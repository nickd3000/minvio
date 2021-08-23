package com.physmo.minvio;

// Import the basic graphics classes.

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic display module implemented using AWT.
 *
 * @author nickd3000
 */

public class BasicDisplayAwt extends BasicDisplay {

    private static final int MAX_BUTTONS = 4;

    private final JFrame mainFrame;
    private final BPanel panel;
    private final int width;
    private final int height;
    final Map<Integer, Font> builtInFonts = new HashMap<>();
    private Color drawColor;
    private Color backgroundColor;

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
        panel = new BPanel(width, height);

        mainFrame = new JFrame("...");
        mainFrame.getContentPane().add(panel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        drawColor = Color.white;

        cls(Color.BLACK);
    }

    /**
     * Close display
     */
    @Override
    public void close() {
        //panel.dispose();
    }

    @Override
    public void repaint() {
        panel.paintImmediately(0, 0, width, height);
    }


    /**
     * Set window title.
     *
     * @param str Text representing the new window title.
     */
    public void setTitle(String str) {
        mainFrame.setTitle(str);
    }

    @Override
    public String getTitle() {
        return mainFrame.getTitle();
    }

    /**
     * Clear the display to supplied color.
     *
     * @param c Color to fill display with.
     */
    @Override
    public void cls(Color c) {
        Color colOld = this.setDrawColor(c);

        panel.g.fillRect(0, 0, width, height);

        this.setDrawColor(colOld);
    }

    /**
     * Clear the display to background color.
     */
    @Override
    public void cls() {
        Color colOld = this.setDrawColor(backgroundColor);

        panel.g.fillRect(0, 0, width, height);

        this.setDrawColor(colOld);
    }

    @Override
    public Color setDrawColor(Color newCol) {
        Color oldCol = drawColor;
        drawColor = newCol;
        panel.g.setColor(newCol);
        return oldCol;
    }

    @Override
    public Color setBackgroundColor(Color newCol) {
        Color oldCol = backgroundColor;
        backgroundColor = newCol;
        return oldCol;
    }

    @Override
    public Color getDrawColor() {
        return panel.g.getColor();
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int[] getKeyState() {
        return panel.keyDown;
    }

    @Override
    public int[] getKeyStatePrevious() {
        return panel.keyDownPrevious;
    }

    // Update previous keys with current keys so we can tell what changed next time.
    @Override
    public void tickInput() {
        System.arraycopy(panel.keyDown, 0, panel.keyDownPrevious, 0, panel.keyDown.length);
    }

    @Override
    public Image getDrawBuffer() {
        return panel.drawBuffer;
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y) {
        panel.g.drawImage(sourceImage, x, y, null);
    }

    @Override
    public void drawImage(BufferedImage sourceImage, int x, int y, int w, int h) {
        panel.g.drawImage(sourceImage, x, y, w, h, null);
    }

    @Override
    public int getRGBAtPoint(int x, int y) {
        return panel.drawBuffer.getRGB(x, y);
    }

    @Override
    public void drawPoint(int x, int y) {
        panel.g.drawLine(x, y, x, y);
        //panel.g.drawLine(x1, y1, x2, y2);

    }

    /**
     * Draw Line
     *
     * @param x1 Start X
     * @param y1 Start Y
     * @param x2 End X
     * @param y2 End Y
     */
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        panel.g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2, double thickness) {
        panel.g2d.setStroke(new BasicStroke((float) thickness));
        panel.g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void drawFilledRect(int x, int y, int width, int height) {
        panel.g.fillRect(x, y, width, height);
    }

    /**
     * Draw rectangle outline
     *
     * @param x Start X
     * @param y Start Y
     * @param width Width
     * @param height Height
     */
    @Override
    public void drawRect(int x, int y, int width, int height) {
        panel.g.drawRect(x, y, width, height);
    }

    /**
     * Draw a centered circle.
     *
     * @param x x position
     * @param y y position
     * @param r diameter
     */
    @Override
    public void drawFilledCircle(double x, double y, double r) {
        // This new method does correct sub-pixel float coords.
        panel.g2d.fill(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        panel.g2d.draw(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
    }

    @Override
    public void drawFilledPolygon(int[] xPoints, int[] yPoints, int numPoints) {
        panel.g2d.fillPolygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void drawText(String str, int x, int y) {

        panel.g.drawString(str, x, y);

    }

    /* TEXT ---------------------------------------------------------------*/

    @Override
    public void setFont(int size) {
        String builtInFontName = "Verdana";
        if (!builtInFonts.containsKey(size)) {
            Font newFont = new Font(builtInFontName, Font.PLAIN, size);
            builtInFonts.put(size, newFont);
        }

        if (builtInFonts.containsKey(size)) {
            setFont(builtInFonts.get(size));
        }
    }

    @Override
    public void setFont(Font font) {
        panel.g.setFont(font);
    }

    @Override
    public Font getFont() {
        return panel.g.getFont();
    }

    @Override
    public int[] getTextSize(String str) {
        FontMetrics metrics = panel.g.getFontMetrics(panel.g.getFont());

        return new int[]{metrics.stringWidth(str), metrics.getAscent(), metrics.getDescent()};

    }


    @Override
    public int getMouseX() {
        return panel.mouseX;
    }

    @Override
    public int getMouseY() {
        return panel.mouseY;
    }

    @Override
    public boolean getMouseButtonLeft() {
        int MOUSE_BUTTON_ID_LEFT = 1;
        return panel.mouseButtonStates[MOUSE_BUTTON_ID_LEFT];
    }

    @Override
    public boolean getMouseButtonMiddle() {
        int MOUSE_BUTTON_ID_MIDDLE = 2;
        return panel.mouseButtonStates[MOUSE_BUTTON_ID_MIDDLE];
    }

    @Override
    public boolean getMouseButtonRight() {
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
        final BufferedImage drawBuffer;
        final Graphics g;
        final Graphics2D g2d;
        int mouseX = 0;
        int mouseY = 0;

        BPanel(int width, int height) {
            setSize(width, height);
            setVisible(true);
            drawBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            setPreferredSize(new Dimension(width, height));

            g = drawBuffer.getGraphics();
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Font fnt = new Font("window", Font.BOLD, 20);
            g.setFont(fnt);

            for (int i = 0; i < numKeys; i++) keyDown[i] = 0;

            this.addKeyListener(this);
            this.addMouseMotionListener(this);
            this.addMouseListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.setDoubleBuffered(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(drawBuffer, 0, 0, null);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("Key Pressed:" + e.getKeyCode());
            keyDown[e.getKeyCode()] = 1;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("keyReleased "+e.getKeyCode());
            keyDown[e.getKeyCode()] = 0;
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
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int bid = e.getButton();
            if (bid < MAX_BUTTONS) {
                mouseButtonStates[bid] = false;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
