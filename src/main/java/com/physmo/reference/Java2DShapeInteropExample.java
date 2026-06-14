package com.physmo.reference;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Demonstrates drawing standard Java2D shapes through Minvio.
 */
public class Java2DShapeInteropExample extends MinvioApp {

    public static void main(String... args) {
        new Java2DShapeInteropExample().start(520, 320, "Java2D Shape Interop", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Palette.GRAY_900);

        pushStyle();
        setDrawColor(Palette.AMBER);
        setStrokeWidth(5);
        drawShape(new RoundRectangle2D.Double(30, 30, 180, 90, 30, 30));
        popStyle();

        Path2D star = createStar(310, 85, 70, 30, 5);
        setDrawColor(Palette.MINT);
        drawFilledShape(star);

        Area ring = new Area(new Ellipse2D.Double(390, 30, 100, 100));
        ring.subtract(new Area(new Ellipse2D.Double(415, 55, 50, 50)));
        setDrawColor(Palette.ORANGE);
        drawFilledShape(ring);

        pushStyle();
        setAlpha(0.55);
        setDrawColor(Palette.BLUE);
        drawFilledShape(new Ellipse2D.Double(80, 170, 150, 100));
        setDrawColor(Palette.RED);
        drawFilledShape(new Ellipse2D.Double(160, 170, 150, 100));
        popStyle();

        setDrawColor(Palette.BLUE);
        setStrokeWidth(3);
        drawShape(createStar(410, 220, 50, 24, 6));

        setDrawColor(Palette.GRAY_300);
        drawText("RoundRectangle2D, Path2D, Area, and Ellipse2D", 30, 300);
    }

    private Path2D createStar(
            double centerX,
            double centerY,
            double outerRadius,
            double innerRadius,
            int points) {
        Path2D path = new Path2D.Double();
        int vertices = points * 2;

        for (int i = 0; i < vertices; i++) {
            double angle = -Math.PI / 2 + i * Math.PI / points;
            double radius = i % 2 == 0 ? outerRadius : innerRadius;
            double x = centerX + Math.cos(angle) * radius;
            double y = centerY + Math.sin(angle) * radius;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.closePath();
        return path;
    }
}
