package com.physmo.minvio;

// Import the basic graphics classes.

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.Palette;
import com.physmo.minvio.utils.gui.support.MouseConnector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a basic display window implemented using AWT and Swing.
 * This class provides functionality such as mouse and keyboard input handling,
 * drawing capabilities, and window management.
 * It extends the {@code BasicDisplay} class.
 */
public class BasicDisplayAwt extends BasicDisplay {

    private static final int MAX_BUTTONS = 4;
    private int width;
    private int height;
    private JFrame mainFrame;
    private BPanel panel;
    private BufferedImage drawBuffer;
    private DrawingContext drawingContext;
    private boolean headless = false;
    private volatile boolean closed = false;

    /**
     * Default constructor - creates display with default size
     */
    public BasicDisplayAwt() {
        this(400, 400);
    }

    /**
     * Constructor with user-defined window size.
     *
     * @param width  Width of window
     * @param height Height of window
     */
    public BasicDisplayAwt(int width, int height) {
        this.width = width;
        this.height = height;


        setDisplaySize(width, height);
        drawingContext.setDrawColor(Color.WHITE);
        drawingContext.cls();

        if (GraphicsEnvironment.isHeadless()) {
            headless = true;
        }

        createAndShowGui();

    }


    /**
     * Replaces the drawing buffer with a buffer of the requested size, copying
     * the previous pixels at the top-left origin.
     *
     * <p>This low-level method updates the drawing context and panel buffer but
     * does not update the display's reported width and height fields or lay out
     * the native window. It is used as one step of deferred resize processing.
     * Calls are not synchronized with painting or AWT input delivery.</p>
     *
     * @param w new buffer width; must be positive
     * @param h new buffer height; must be positive
     * @throws IllegalArgumentException if either dimension is not positive
     */
    public void setDisplaySize(int w, int h) {

        BufferedImage newBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D resizeGraphics = newBuffer.createGraphics();
        try {
            resizeGraphics.drawImage(drawBuffer, 0, 0, null);
        } finally {
            resizeGraphics.dispose();
        }
        drawBuffer = newBuffer;

        if (drawingContext == null) {
            drawingContext = new DrawingContextAwt(drawBuffer);
        } else {
            drawingContext.setImageBuffer(drawBuffer);
        }

        if (panel != null) panel.setDrawBuffer(drawBuffer);
    }


    Rect resizeRequest = null;

    public void resizeIfRequested() {

        if (resizeRequest == null) return;

        int newWidth = resizeRequest.w;
        int newHeight = resizeRequest.h;
        resizeRequest = null; // Clear resize object once we have the size.

        setDisplaySize(newWidth, newHeight);
        width = newWidth;
        height = newHeight;
        if (resizeListener != null) {
            resizeListener.applyAsInt(newWidth, newHeight);
        }
        panel.setSize(newWidth, newHeight);
        panel.doLayout();
        mainFrame.doLayout();
    }

    /**
     * Creates and shows the Swing window unless the environment is headless,
     * then resets drawing state to display defaults.
     *
     * <p>The method performs creation synchronously on the calling thread and
     * does not marshal work to the Swing event dispatch thread. It is invoked
     * by the constructor and is not intended to be called repeatedly.</p>
     */
    public void createAndShowGui() {

        if (!headless) {
            panel = new BPanel(width, height, drawBuffer);

            panel.addMouseConnectors(this.mouseConnectors);

            mainFrame = new JFrame("...");
            mainFrame.getContentPane().add(panel);
            mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setResizable(true);
            mainFrame.setVisible(true);

            mainFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    resizeRequest = new Rect(0, 0, panel.getWidth(), panel.getHeight());
                }
            });
        }

        reset();

    }

    @Override
    public void reset() {
        drawingContext.setDrawColor(Palette.GRAY_900);
        drawingContext.setBackgroundColor(Palette.GRAY_300);
        drawingContext.setFont(16);
        drawingContext.setStrokeWidth(1);
        drawingContext.setAlpha(1);
        drawingContext.clearClip();
    }

    @Override
    public DrawingContext getDrawingContext() {
        return drawingContext;
    }

    /**
     * Close display
     */
    @Override
    public void close() {
        closed = true;
        if (mainFrame == null) return;

        if (SwingUtilities.isEventDispatchThread()) {
            mainFrame.dispose();
        } else {
            SwingUtilities.invokeLater(mainFrame::dispose);
        }
    }

    @Override
    public boolean isVisible() {
        if (closed) return false;
        if (headless) return true;
        return mainFrame != null && mainFrame.isVisible();
    }

    @Override
    public void repaint() {
        if (!headless) {
            panel.paintImmediately(0, 0, width, height);
        }
    }

    @Override
    public String getTitle() {
        if (!headless) {
            return mainFrame.getTitle();
        } else return "Headless";
    }

    /**
     * Set window title.
     *
     * @param str Text representing the new window title.
     */
    public void setTitle(String str) {
        if (!headless) {
            mainFrame.setTitle(str);
        }
    }

    @Override
    public int[] getKeyState() {
        if (panel == null) return new int[1000];
        return panel.keyDown;
    }

    @Override
    public int[] getKeyStatePrevious() {
        if (panel == null) return new int[1000];
        return panel.keyDownPrevious;
    }

    // Update previous keys with current keys so we can tell what changed next time.
    @Override
    public void tickInput() {
        if (panel == null) return;
        System.arraycopy(panel.keyDown, 0, panel.keyDownPrevious, 0, panel.keyDown.length);
    }

    @Override
    public Image getDrawBuffer() {
        return drawBuffer;
    }

    @Override
    public int getMouseX() {
        if (panel == null) return 0;
        return panel.mouseX;
    }

    @Override
    public int getMouseY() {
        if (panel == null) return 0;
        return panel.mouseY;
    }

    @Override
    public boolean getMouseButtonLeft() {
        if (panel == null) return false;
        int MOUSE_BUTTON_ID_LEFT = 1;
        return panel.mouseButtonStates[MOUSE_BUTTON_ID_LEFT];
    }

    @Override
    public boolean getMouseButtonMiddle() {
        if (panel == null) return false;
        int MOUSE_BUTTON_ID_MIDDLE = 2;
        return panel.mouseButtonStates[MOUSE_BUTTON_ID_MIDDLE];
    }

    @Override
    public boolean getMouseButtonRight() {
        if (panel == null) return false;
        int MOUSE_BUTTON_ID_RIGHT = 3;
        return panel.mouseButtonStates[MOUSE_BUTTON_ID_RIGHT];
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    static class BPanel extends JPanel implements MouseMotionListener, KeyListener, MouseListener {
        private static final long serialVersionUID = 3096588689174149256L;
        final int numKeys = 1000;
        final int[] keyDown = new int[numKeys];
        final int[] keyDownPrevious = new int[numKeys];
        final boolean[] mouseButtonStates = new boolean[MAX_BUTTONS];
        BufferedImage drawBuffer;
        int mouseX = 0;
        int mouseY = 0;
        List<MouseConnector> mouseConnectors;

        BPanel(int width, int height, BufferedImage drawBuffer) {
            setSize(width, height);
            setVisible(true);
            this.drawBuffer = drawBuffer;
            setPreferredSize(new Dimension(width, height));

            Arrays.fill(keyDown, 0);

            this.addKeyListener(this);
            this.addMouseMotionListener(this);
            this.addMouseListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.setDoubleBuffered(true);
        }

        public void setDrawBuffer(BufferedImage drawBuffer) {
            this.drawBuffer = drawBuffer;
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(drawBuffer, 0, 0, null);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            for (MouseConnector mouseConnector : mouseConnectors) {
                mouseConnector.onMouseMoved(mouseX, mouseY);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            for (MouseConnector mouseConnector : mouseConnectors) {
                mouseConnector.onMouseMoved(mouseX, mouseY);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // System.out.println("[DEBUG_LOG] keyPressed: " + keyCode);
            if (keyCode >= 0 && keyCode < numKeys) {
                keyDown[keyCode] = 1;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode >= 0 && keyCode < numKeys) {
                keyDown[keyCode] = 0;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
//            int bid = e.getButton();
//            if (bid<MAX_BUTTONS) {
//                mouseButtonStates[bid]=true;
//            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int bid = e.getButton();
            if (bid < MAX_BUTTONS) {
                mouseButtonStates[bid] = true;
            }

            for (MouseConnector mouseConnector : mouseConnectors) {
                mouseConnector.onButtonDown(e.getX(), e.getY(), bid);
            }


        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int bid = e.getButton();
            if (bid < MAX_BUTTONS) {
                mouseButtonStates[bid] = false;
            }
            for (MouseConnector mouseConnector : mouseConnectors) {
                mouseConnector.onButtonUp(e.getX(), e.getY(), bid);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        public void addMouseConnectors(List<MouseConnector> mouseConnectors) {
            this.mouseConnectors = mouseConnectors;
        }
    }
}
