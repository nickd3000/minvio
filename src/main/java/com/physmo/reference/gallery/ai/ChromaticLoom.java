package com.physmo.reference.gallery.ai;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChromaticLoom extends MinvioApp {

    private static final int WIDTH = 760;
    private static final int HEIGHT = 560;
    private static final int THREAD_COUNT = 84;
    private static final int PIN_COUNT = 42;
    private static final int GLINT_COUNT = 170;

    private final Gradient backgroundGradient = new Gradient();
    private final Gradient threadGradient = new Gradient();
    private final List<Glint> glints = new ArrayList<>();
    private double time = 0;

    public static void main(String... args) {
        MinvioApp app = new ChromaticLoom();
        app.start(WIDTH, HEIGHT, "Chromatic Loom", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        backgroundGradient.addColor(0.0, new Color(13, 16, 24));
        backgroundGradient.addColor(0.32, new Color(24, 38, 52));
        backgroundGradient.addColor(0.68, new Color(47, 35, 42));
        backgroundGradient.addColor(1.0, new Color(11, 15, 24));

        threadGradient.addColor(0.0, new Color(255, 238, 150));
        threadGradient.addColor(0.18, new Color(76, 211, 190));
        threadGradient.addColor(0.38, new Color(91, 137, 255));
        threadGradient.addColor(0.58, new Color(245, 105, 152));
        threadGradient.addColor(0.78, new Color(255, 178, 85));
        threadGradient.addColor(1.0, new Color(238, 250, 255));

        Random random = new Random(908172635);
        for (int i = 0; i < GLINT_COUNT; i++) {
            glints.add(new Glint(
                    random.nextDouble(),
                    random.nextDouble(),
                    0.45 + random.nextDouble() * 2.4,
                    0.4 + random.nextDouble() * 1.9,
                    random.nextDouble() * Math.PI * 2.0));
        }
    }

    @Override
    public void draw(double delta) {
        time += delta;

        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;
        double mx = getMouseX() > 0 ? getMouseX() : cx;
        double my = getMouseY() > 0 ? getMouseY() : cy;
        double mouseXNorm = (mx - cx) / Math.max(1.0, getWidth() / 2.0);
        double mouseYNorm = (my - cy) / Math.max(1.0, getHeight() / 2.0);
        double tension = getBasicDisplay().getMouseButtonLeft() ? 1.0 : 0.0;

        drawBackground(mouseXNorm, mouseYNorm);
        drawBackThreads(cx, cy, mouseXNorm, mouseYNorm, tension);
        drawFrame(cx, cy, tension);
        drawMainThreads(cx, cy, mouseXNorm, mouseYNorm, tension);
        drawGlints(mx, my, tension);
        drawCenterpiece(cx, cy, mouseXNorm, mouseYNorm, tension);
        drawHud();
    }

    private void drawBackground(double mouseXNorm, double mouseYNorm) {
        for (int y = 0; y < getHeight(); y += 4) {
            double pos = y / (double) Math.max(1, getHeight() - 1);
            Color color = backgroundGradient.getColor(pos);
            int lift = (int) ((Math.sin(time * 0.7 + pos * 7.0 + mouseXNorm) + 1.0) * 5);
            setDrawColor(new Color(
                    clampInt(color.getRed() + lift, 0, 255),
                    clampInt(color.getGreen() + lift, 0, 255),
                    clampInt(color.getBlue() + lift + (int) (mouseYNorm * 8), 0, 255)));
            drawFilledRect(0, y, getWidth(), 4);
        }

        setDrawColor(new Color(255, 255, 255, 12));
        for (int x = -80; x < getWidth() + 80; x += 42) {
            double bend = Math.sin(time * 0.35 + x * 0.04) * 18;
            drawLine(x + bend, 0, x - 90 - bend, getHeight(), 1.0);
        }
    }

    private void drawBackThreads(double cx, double cy, double mouseXNorm, double mouseYNorm, double tension) {
        double radiusX = getWidth() * (0.32 + tension * 0.03);
        double radiusY = getHeight() * (0.27 + tension * 0.02);

        for (int i = 0; i < THREAD_COUNT; i++) {
            double pos = i / (double) THREAD_COUNT;
            double a = pos * Math.PI * 2.0 + time * 0.12;
            double b = a + Math.PI * (0.74 + Math.sin(time * 0.17 + pos * 9.0) * 0.18);
            double x1 = cx + Math.cos(a) * radiusX;
            double y1 = cy + Math.sin(a) * radiusY;
            double x2 = cx + Math.cos(b) * radiusX;
            double y2 = cy + Math.sin(b) * radiusY;

            x1 += mouseXNorm * Math.sin(pos * 17.0 + time) * 18;
            y1 += mouseYNorm * Math.cos(pos * 13.0 - time) * 14;
            x2 -= mouseXNorm * Math.cos(pos * 11.0 - time) * 18;
            y2 -= mouseYNorm * Math.sin(pos * 19.0 + time) * 14;

            Color color = threadGradient.getColor((pos + time * 0.025) % 1.0);
            int alpha = 22 + (int) (Math.sin(time + pos * Math.PI * 12.0) * 12) + (int) (tension * 36);
            setDrawColor(withAlpha(color, clampInt(alpha, 12, 85)));
            drawLine(x1, y1, x2, y2, 1.0 + tension * 0.7);
        }
    }

    private void drawFrame(double cx, double cy, double tension) {
        double radiusX = getWidth() * 0.37;
        double radiusY = getHeight() * 0.31;

        for (int i = 0; i < PIN_COUNT; i++) {
            double pos = i / (double) PIN_COUNT;
            double angle = pos * Math.PI * 2.0;
            double pulse = Math.sin(time * 1.5 + pos * Math.PI * 10.0) * 2.0;
            double x = cx + Math.cos(angle) * radiusX;
            double y = cy + Math.sin(angle) * radiusY;

            setDrawColor(withAlpha(new Color(3, 8, 14), 140));
            drawFilledCircle(x + 2, y + 3, 6.5 + tension * 1.5);
            setDrawColor(withAlpha(threadGradient.getColor((pos + 0.2) % 1.0), 175));
            drawFilledCircle(x, y, 4.0 + pulse * 0.25 + tension * 1.2);
            setDrawColor(new Color(248, 255, 245, 160));
            drawCircle(x, y, 7 + tension * 2);
        }
    }

    private void drawMainThreads(double cx, double cy, double mouseXNorm, double mouseYNorm, double tension) {
        double radiusX = getWidth() * 0.37;
        double radiusY = getHeight() * 0.31;
        double twist = time * (0.18 + tension * 0.16) + mouseXNorm * 0.5;

        for (int i = 0; i < PIN_COUNT; i++) {
            int mate = (i * 17 + 11) % PIN_COUNT;
            double pos1 = i / (double) PIN_COUNT;
            double pos2 = mate / (double) PIN_COUNT;
            double a = pos1 * Math.PI * 2.0 + twist;
            double b = pos2 * Math.PI * 2.0 - twist * 0.6;

            double x1 = cx + Math.cos(a) * radiusX;
            double y1 = cy + Math.sin(a) * radiusY;
            double x2 = cx + Math.cos(b) * radiusX;
            double y2 = cy + Math.sin(b) * radiusY;
            double middleLift = Math.sin(time * 0.9 + i * 0.7) * 18 + mouseYNorm * 32;

            Color color = threadGradient.getColor((pos1 + time * 0.045) % 1.0);
            setDrawColor(withAlpha(color, 88 + (int) (tension * 70)));
            drawLine(x1, y1, cx + middleLift, cy - middleLift * 0.35, x2, y2, 1.8 + tension * 1.4);
        }
    }

    private void drawLine(double x1, double y1, double cx, double cy, double x2, double y2, double width) {
        int steps = 18;
        double previousX = x1;
        double previousY = y1;

        for (int i = 1; i <= steps; i++) {
            double t = i / (double) steps;
            double oneMinusT = 1.0 - t;
            double x = oneMinusT * oneMinusT * x1 + 2.0 * oneMinusT * t * cx + t * t * x2;
            double y = oneMinusT * oneMinusT * y1 + 2.0 * oneMinusT * t * cy + t * t * y2;
            drawLine(previousX, previousY, x, y, width);
            previousX = x;
            previousY = y;
        }
    }

    private void drawGlints(double mx, double my, double tension) {
        for (Glint glint : glints) {
            double x = glint.xRatio * getWidth();
            double y = glint.yRatio * getHeight();
            double shimmer = 0.5 + Math.sin(time * glint.speed + glint.phase) * 0.5;
            double proximity = Math.max(0.0, 1.0 - Math.hypot(mx - x, my - y) / 170.0);
            double size = glint.size * (0.45 + shimmer * 0.8) + proximity * 2.4 + tension * 0.4;
            int alpha = clampInt((int) (35 + shimmer * 80 + proximity * 95), 0, 215);

            setDrawColor(new Color(240, 252, 255, alpha));
            drawFilledCircle(x, y, size);
        }
    }

    private void drawCenterpiece(double cx, double cy, double mouseXNorm, double mouseYNorm, double tension) {
        double rotation = time * 0.45 + mouseXNorm * 0.4;

        for (int ring = 0; ring < 5; ring++) {
            double radius = 22 + ring * 18 + tension * 4;
            int sides = 6 + ring * 2;
            int[] xPoints = new int[sides];
            int[] yPoints = new int[sides];

            for (int i = 0; i < sides; i++) {
                double angle = rotation * (ring % 2 == 0 ? 1 : -1) + i * Math.PI * 2.0 / sides;
                double wobble = 1.0 + Math.sin(time * 1.6 + i + ring) * 0.08;
                xPoints[i] = (int) (cx + Math.cos(angle) * radius * wobble);
                yPoints[i] = (int) (cy + Math.sin(angle) * radius * wobble + mouseYNorm * ring * 2.5);
            }

            setDrawColor(withAlpha(threadGradient.getColor((ring * 0.17 + time * 0.04) % 1.0), 42 + ring * 23));
            drawFilledPolygon(xPoints, yPoints, sides);
            setDrawColor(new Color(247, 255, 240, 52 + ring * 18));
            drawPolygon(xPoints, yPoints, sides);
        }

        setDrawColor(new Color(255, 246, 187, 190));
        drawFilledCircle(cx, cy, 9 + tension * 5);
        setDrawColor(new Color(255, 255, 255, 160));
        drawCircle(cx, cy, 17 + Math.sin(time * 2.4) * 3 + tension * 8);
    }

    private void drawHud() {
        String message = "Move mouse. Hold left button.";
        setFont(12);
        int[] textSize = getTextSize(message);
        int x = (getWidth() - textSize[BasicDisplay.TEXT_SIZE_WIDTH]) / 2;
        int y = getHeight() - 20;

        setDrawColor(new Color(4, 8, 14, 145));
        drawFilledRect(x - 10, y - 18, textSize[BasicDisplay.TEXT_SIZE_WIDTH] + 20, 24);
        setDrawColor(new Color(235, 248, 255, 200));
        drawText(message, x, y);
    }

    private static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), clampInt(alpha, 0, 255));
    }

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private record Glint(double xRatio, double yRatio, double size, double speed, double phase) {
    }
}
