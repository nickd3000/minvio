package com.physmo.toolbox;

// Import the basic graphics classes.
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

/**
 * A basic display module with simplified drawing operations.
 * 
 * @author nickd3000
 */
// TODO: add filled and outline versions of circle
// TODO: change all drawing operations to take int position values.
// TODO: should we use Graphics2D more?
// TODO: add mouse functions to BPanel
// TODO: move bpanel to it's own file as it's going to do more.
// TODO: how to send mouse data to basicdisplay without lots of duplicate functions.
// This might help add some key/mouse input.
public class BasicDisplay {

	class BPanel extends JPanel implements MouseMotionListener, KeyListener {
		private static final long serialVersionUID = 3096588689174149256L;
		public BufferedImage drawBuffer;
		public Graphics g = null;
		public Graphics2D g2d = null;
		public int mouseX=0, mouseY=0;
		int numKeys = 1000;
		public int keyDown[] = new int[numKeys];
		public int keyDownPrevious[] = new int[numKeys];
		
		public BPanel(int width, int height) {
			setSize(width, height);
			setVisible(true);
			drawBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			setPreferredSize(new Dimension(width,height));
			g = drawBuffer.getGraphics();
			g2d = (Graphics2D) g;
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			Font fnt = new Font("window", Font.BOLD, 20);
			g.setFont(fnt);
			
			for (int i=0;i<numKeys;i++) keyDown[i]=0;
			
			this.addKeyListener(this);
			this.addMouseMotionListener(this);
			this.setFocusable(true);
            this.requestFocusInWindow();
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
			//System.out.println("Key Pressed:"+e.getKeyCode());
			keyDown[e.getKeyCode()]=1;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("keyReleased "+e.getKeyCode());
			keyDown[e.getKeyCode()]=0;
		}
		

		
	}
	
	JFrame mainFrame = null;
	BPanel panel= null;
	Color drawColor;
	int width, height;
	static long timerStart = 0;
	
	/**
	 * Default constructor - creates display with default size
	 */
	public BasicDisplay() {
		this(400,400);
	}
		
	/**
	 * Constructor with user defined window size.
	 * @param width		Width of window
	 * @param height		Height of window
	 */
	public BasicDisplay(int width, int height) {
		this.width = width;
		this.height = height;
		panel = new BPanel(width,height);
		
		mainFrame = new JFrame("...");
		mainFrame.getContentPane().add(panel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

		drawColor = Color.BLACK;
	}
	
	/**
	 * Close display
	 */
	public void close() {
		//panel.dispose();
	}

	
	/**
	 * Update the display with drawing changes.
	 */
	public void refresh() {
		panel.repaint();
	}

	// Refresh variant that delays to keep refresh rate at fps frames per second.
	public void refresh(int fps) {
		panel.repaint();
		while (getEllapsedTime()<1000/fps) {};
		startTimer();
	}

	public void setTitle(String str) {
		mainFrame.setTitle(str);
	}
	
	//while (bd.getEllapsedTime()<1000/10) {};
	
	/**
	 * Clear the display to supplied color.
	 * @param c		Color to fill display with.
	 */
	public void cls(Color c) {
		Color colOld = this.setDrawColor(c);
		panel.g.fillRect(0, 0, width, height);
		this.setDrawColor(colOld);
	}
	
	public Color setDrawColor(Color newCol) {
		Color oldCol = drawColor;
		drawColor = newCol;
		panel.g.setColor(newCol);
		return oldCol;
	}

	public int[] getKeyState() {
		return panel.keyDown;
	}
	public int[] getKeyStatePrevious() {
		return panel.keyDownPrevious;
	}

	// Update previous keys with current keys so we can tell what changed next time.
	public void tickInput() {
		for (int i=0;i<panel.keyDown.length;i++) {
			panel.keyDownPrevious[i] = panel.keyDown[i];
		}
	}
	
	
	public Image getDrawBuffer() {
		return panel.drawBuffer;
	}
	
	public void drawImage(BufferedImage sourceImage, int x, int y) {
		panel.g.drawImage(sourceImage,x,y,null);
	}
	
	/**
	 * Draw Line
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		panel.g.drawLine(x1, y1, x2, y2);
	}

	public void drawLine(float x1, float y1, float x2, float y2) {
		panel.g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public void drawLine(double x1, double y1, double x2, double y2, double thickness) {
		//Graphics2D g2d = (Graphics2D) panel.g;
	
		panel.g2d.setStroke(new BasicStroke((float) thickness));
		panel.g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public void drawFilledRect(int x, int y, int width, int height) {
		panel.g.fillRect(x, y, width, height);
	}

	/**
	 * Draw rectangle outline
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 */
	public void drawRect(int x1, int y1, int x2, int y2) {
		drawLine(x1,y1,x2,y1);
		drawLine(x2,y1,x2,y2);
		drawLine(x2,y2,x1,y2);
		drawLine(x1,y1,x1,y2);
	}

	/**
	 * Draw a centered circle.
	 * @param x		x position
	 * @param y		y position
	 * @param d		diameter
	 */
	public void drawFilledCircle(double x, double y, double r) {
		//panel.g2d.fillOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		double r_2 = r/2.0;
		// This new method does correct sub-pixel float coords.
		panel.g2d.fill(new Ellipse2D.Double(x-r_2, y-r_2, r, r));
	}
	

	public void drawCircle(double x, double y, double r) {
		//panel.g.drawOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		double r_2 = r/2.0;
		panel.g2d.draw(new Ellipse2D.Double(x-r_2, y-r_2, r, r));
	}

	public void drawText(String str, int x, int y) {
		panel.g.drawString(str, x, y);
	}

	/* TIMING ---------------------------------------------------------------*/ 
	
	public void startTimer() {
		timerStart = System.nanoTime();
	}
	
	// Returns milliseconds since startTimr() was called.
	public long getEllapsedTime() {
		long t = (System.nanoTime() - timerStart) / 1_000_000;
		return t;
	}

	/* COLOR ----------------------------------------------------------------*/
	
	// Returns a new distinct colour for each supplied index.
	public Color getDistinctColor(int index, float saturation) {
		
		Color newCol = new Color(Color.HSBtoRGB(((float)index)*0.6180339887f, saturation, 1.0f));
		
		return newCol;
	}
	
	public int mouseX() { return panel.mouseX; }
	public int mouseY() { return panel.mouseY; }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
