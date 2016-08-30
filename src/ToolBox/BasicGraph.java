package ToolBox;

import java.awt.Color;

import ToolBox.BasicDisplay;

public class BasicGraph {

	double [] values;
	int headPos = 0;
	int numPoints = 0;
	
	public BasicGraph(int numPoints) {
		this.numPoints = numPoints;
		values = new double[numPoints];
		headPos=0;
	}
	
	public void addData(double val) {
		values[headPos++] = val;
		if (headPos>=numPoints) headPos = 0;
	}
	
	public void draw(BasicDisplay d, int x, int y, int width, int height, Color c) {
		
		int count = 0;
		int readPos = headPos;
		double rawValue = 0;
		double px,py;
		while (count<numPoints) {
			rawValue = values[readPos++];
			if (readPos>=numPoints) readPos=0;
			count++;
			
			px = count * ((double)width/(double)numPoints);
			px+=x;
			py = rawValue * height;
			py = (y + height)-py;
			d.fillRect((int)px, (int)py, 2, 2, c);
		}
	}
}
