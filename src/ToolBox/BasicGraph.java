package ToolBox;

import java.awt.Color;

import ToolBox.BasicDisplay;

public class BasicGraph {

	double [] values;
	double maxValue,minValue,floatingMax,floatingMin;
	int headPos = 0;
	int numPoints = 0;
	
	public BasicGraph(int numPoints) {
		this.numPoints = numPoints;
		values = new double[numPoints];
		headPos=0;
		maxValue=0.1;
		minValue=-0.1;
		floatingMax = maxValue;
		floatingMin = minValue;
	}
	
	public void addData(double val) {
		val=-val;
		values[headPos++] = val;
		if (headPos>=numPoints) headPos = 0;
		//if (val>maxValue) maxValue=val;
		//if (val<minValue) minValue=val;
	}
	
	public void draw(BasicDisplay d, int x, int y, int width, int height, Color c) {
		
		floatingMax = floatingMax - ((floatingMax-maxValue)*0.01);
		floatingMin = floatingMin - ((floatingMin-minValue)*0.01);
		//double zoomSpan = Math.max(Math.abs(maxValue), Math.abs(minValue))*2;
		double zoomSpan = Math.max(Math.abs(floatingMax), Math.abs(floatingMin))*2;
		
		d.drawLine(x, y+(height/2), x+width, y+(height/2), Color.black);
		int count = 0;
		int readPos = headPos;
		double rawValue = 0;
		double px,py;
		maxValue=0.01;
		minValue=-0.01;
		while (count<numPoints) {
			rawValue = values[readPos++];
			
			if (rawValue>maxValue) maxValue=rawValue;
			if (rawValue<minValue) minValue=rawValue;
			
			if (readPos>=numPoints) readPos=0;
			count++;
			
			px = count * ((double)width/(double)numPoints);
			px+=x;
			//py = rawValue * height;
			//py = (y + height)-py;
			py = (rawValue/zoomSpan)*height;
			py += y + (height/2);
			if (py<=y || py>=y+height) continue;
			d.fillRect((int)px, (int)py, 2, 2, c);
		}
		
		
		d.drawRect(x,y,x+width,y+height, Color.black);
	}
}
