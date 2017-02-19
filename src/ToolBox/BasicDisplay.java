package ToolBox;

// Import the basic graphics classes.
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * A basic display module with simplified drawing operations.
 * 
 * @author nickd3000
 */
public class BasicDisplay {

	class BFrame extends JFrame {
		private static final long serialVersionUID = 3096588689174149256L;
		public BufferedImage drawBuffer;
		public Graphics g = null;
		
		public BFrame(int width, int height) {
			setSize(width, height);
			setVisible(true);
			drawBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			g = drawBuffer.getGraphics();
		}
		
		
		
		@Override
		public void paint(Graphics g) {
			g.drawImage(drawBuffer, 0, 0, null);
		}
	}
	
	BFrame frame = null;
	
	Color currentColor;
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
		frame = new BFrame(width,height);
		
	}
	
	/**
	 * Close display
	 */
	public void close() {
		frame.dispose();
	}

	
	/**
	 * Update the display with drawing changes.
	 */
	public void refresh() {
		frame.repaint();
	}

	/**
	 * Clear the display to supplied color.
	 * @param c		Color to fill display with.
	 */
	public void cls(Color c) {
		//Graphics g = frame.drawBuffer.getGraphics();
		frame.g.setColor(c);
		frame.g.fillRect(0, 0, width, height);
		//frame.g.dispose();
	}
	
	public Image getDrawBuffer() {
		return frame.drawBuffer;
	}
	
	public void drawImage(BufferedImage sourceImage, int x, int y) {
		frame.g.drawImage(sourceImage,x,y,null);
	}
	
	/**
	 * Draw Line
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 * @param c		Colour
	 */
	public void drawLine(int x1, int y1, int x2, int y2, Color c) {
		frame.g.setColor(c);
		frame.g.drawLine(x1, y1, x2, y2);
	}
	
	/**
	 * Draw Line
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 * @param c		Colour
	 */
	public void drawLine(float x1, float y1, float x2, float y2, Color c) {
		frame.g.setColor(c);
		frame.g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	/**
	 * Draw Line
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 * @param c		Colour
	 * @param thickness	Line thickness
	 */
	public void drawLine(double x1, double y1, double x2, double y2, Color c, double thickness) {
		//Graphics g = frame.drawBuffer.getGraphics();
		Graphics2D g2d = (Graphics2D) frame.g;
	
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke((float) thickness));
		g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		//g2d.dispose();
	
		//g.dispose();
	}

	public void fillRect(int x, int y, int width, int height, Color c) {
		//Graphics g = frame.drawBuffer.getGraphics();
		frame.g.setColor(c);
		frame.g.fillRect(x, y, width, height);
		//frame.g.dispose();
	}

	/**
	 * Draw rectangle
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 * @param c		Colour
	 */
	public void drawRect(int x1, int y1, int x2, int y2, Color c) {
		drawLine(x1,y1,x2,y1,c);
		drawLine(x2,y1,x2,y2,c);
		drawLine(x2,y2,x1,y2,c);
		drawLine(x1,y1,x1,y2,c);
	}

	/**
	 * Draw a centered circle to display with supplied color.
	 * @param x		x position
	 * @param y		y position
	 * @param d		diameter
	 * @param c		color
	 */
	public void drawCircle(double x, double y, double d, Color c) {
		//Graphics g = frame.drawBuffer.getGraphics();
		frame.g.setColor(c);
		frame.g.fillOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		//g.dispose();
	}


	public void drawText(String str, int x, int y, Color c) {
		//Graphics g = frame.drawBuffer.getGraphics();
		frame.g.setColor(c);
		//g.fillOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		frame.g.drawString(str, x, y);
		//g.dispose();
	}

	
	public static void startTimer() {
		timerStart = System.nanoTime();
	}
	
	// Returns milliseconds since startTimr() was called.
	public static long getEllapsedTime() {
		long t = (System.nanoTime() - timerStart) / 1_000_000;
		return t;
	}

	
	
}
