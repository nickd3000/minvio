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
public class BasicDisplay extends JFrame {

	BufferedImage img;
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
	 * @param height	Height of window
	 */
	public BasicDisplay(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setSize(width, height);
		setVisible(true);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	
	/**
	 * Close display
	 */
	public void close() {
		this.dispose();
	}

	
	/**
	 * Update the display with drawing changes.
	 */
	public void refresh() {
		repaint();
	}

	/**
	 * Clear the display to supplied colour.
	 * @param c		Colour to fill display with.
	 */
	public void cls(Color c) {
		Graphics g = img.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		g.dispose();
	}
	
	public void fillRect(int x, int y, int width, int height, Color c) {
		Graphics g = img.getGraphics();
		g.setColor(c);
		g.fillRect(x, y, width, height);
		g.dispose();
	}
	
	
	public Image getImage() {
		return img;
	}
	
	public void drawImage(BufferedImage sourceImage, int x, int y) {
		Graphics g = img.getGraphics();
		g.drawImage(sourceImage,x,y,null);
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
		Graphics g = img.getGraphics();
		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
		g.dispose();
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
	 * Draw Line
	 * @param x1	Start X
	 * @param y1	Start Y
	 * @param x2	End X
	 * @param y2	End Y
	 * @param c		Colour
	 */
	public void drawLine(float x1, float y1, float x2, float y2, Color c) {
		Graphics g = img.getGraphics();
		g.setColor(c);
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		g.dispose();
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
		Graphics g = img.getGraphics();
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(c);
		g2d.setStroke(new BasicStroke((float) thickness));
		g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		g2d.dispose();

		g.dispose();
	}

	
	/**
	 * Draw a circle to display with supplied colour.
	 * @param x		x position
	 * @param y		y position
	 * @param d		diameter
	 * @param c		colour
	 */
	public void drawCircle(double x, double y, double d, Color c) {
		Graphics g = img.getGraphics();
		g.setColor(c);
		g.fillOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		g.dispose();
	}


	public void drawText(String str, int x, int y, Color c) {
		Graphics g = img.getGraphics();
		g.setColor(c);
		//g.fillOval((int) (x - (d / 2)), (int) (y - (d / 2)), (int) (d), (int) (d));
		g.drawString(str, x, y);
		g.dispose();
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
