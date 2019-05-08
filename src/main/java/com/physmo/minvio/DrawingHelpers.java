package com.physmo.minvio;

public class DrawingHelpers {

    public static void drawGrid(BasicDisplay bd, int x, int y, int width, int height, int numSegments) {
        int horizontalSpace = width/numSegments;
        int verticalSpace = height/numSegments;
        for (int i=0;i<=numSegments;i++) {
            bd.drawLine(x+(i*horizontalSpace),y,x+(i*horizontalSpace),y+height);
            bd.drawLine(x,y+(i*verticalSpace),x+width,y+(i*verticalSpace));
        }
    }

}
