package com.physmo.reference.gallery.ai;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MidnightRainPostcard extends MinvioApp {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 520;
    private static final int DROP_COUNT = 360;
    private static final int SPLASH_COUNT = 80;

    private final Gradient skyGradient = new Gradient();
    private final List<Building> buildings = new ArrayList<>();
    private final List<RainDrop> drops = new ArrayList<>();
    private final List<Splash> splashes = new ArrayList<>();
    private final Random random = new Random(4102026);
    private double time = 0;
    private double lightningTimer = 0;
    private double nextLightning = 4.5;

    public static void main(String... args) {
        MinvioApp app = new MidnightRainPostcard();
        app.start(WIDTH, HEIGHT, "Midnight Rain Postcard", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        skyGradient.addColor(0.0, new Color(8, 12, 25));
        skyGradient.addColor(0.35, new Color(19, 33, 52));
        skyGradient.addColor(0.74, new Color(38, 45, 58));
        skyGradient.addColor(1.0, new Color(14, 18, 27));

        double x = 0.0;
        while (x < 1.05) {
            double width = 0.045 + random.nextDouble() * 0.07;
            double height = 0.18 + random.nextDouble() * 0.34;
            buildings.add(new Building(
                    x,
                    width,
                    height,
                    4 + random.nextInt(7),
                    5 + random.nextInt(9),
                    random.nextDouble(),
                    random.nextDouble() < 0.24));
            x += width * (0.72 + random.nextDouble() * 0.5);
        }

        for (int i = 0; i < DROP_COUNT; i++) {
            drops.add(newDrop(random.nextDouble()));
        }

        for (int i = 0; i < SPLASH_COUNT; i++) {
            splashes.add(new Splash(
                    random.nextDouble(),
                    random.nextDouble(),
                    random.nextDouble() * Math.PI * 2.0,
                    0.4 + random.nextDouble() * 1.4));
        }
    }

    @Override
    public void draw(double delta) {
        time += delta;
        lightningTimer += delta;

        if (lightningTimer > nextLightning) {
            lightningTimer = 0;
            nextLightning = 3.8 + random.nextDouble() * 5.0;
        }

        double flash = Math.max(0.0, 1.0 - lightningTimer * 3.4);
        if (getBasicDisplay().getMouseButtonLeft()) {
            flash = Math.max(flash, 0.65 + Math.sin(time * 24.0) * 0.18);
        }

        double mouseX = getMouseX() > 0 ? getMouseX() : getWidth() / 2.0;
        double wind = ((mouseX / Math.max(1.0, getWidth())) - 0.5) * 2.0;
        double streetY = getHeight() * 0.72;

        drawSky(flash);
        drawMoonAndClouds(wind, flash);
        drawDistantLights(flash);
        drawBuildings(streetY, flash);
        drawStreet(streetY, flash);
        updateAndDrawRain(delta, wind, streetY, flash);
        drawSplashes(streetY, wind);
        drawUmbrella(mouseX, getMouseY() > 0 ? getMouseY() : streetY - 62, flash);
        drawHud();
    }

    private void drawSky(double flash) {
        for (int y = 0; y < getHeight(); y += 3) {
            double pos = y / (double) Math.max(1, getHeight() - 1);
            Color base = skyGradient.getColor(pos);
            int lift = (int) (flash * (80 - pos * 44));
            setDrawColor(new Color(
                    clampInt(base.getRed() + lift, 0, 255),
                    clampInt(base.getGreen() + lift, 0, 255),
                    clampInt(base.getBlue() + lift, 0, 255)));
            drawFilledRect(0, y, getWidth(), 3);
        }
    }

    private void drawMoonAndClouds(double wind, double flash) {
        double moonX = getWidth() * 0.78;
        double moonY = getHeight() * 0.17;

        setDrawColor(new Color(249, 243, 205, 145 + (int) (flash * 80)));
        drawFilledCircle(moonX, moonY, 34);
        setDrawColor(new Color(11, 17, 28, 125));
        drawFilledCircle(moonX - 12, moonY - 7, 33);

        for (int layer = 0; layer < 3; layer++) {
            double drift = ((time * (14 + layer * 5) * (layer % 2 == 0 ? 1 : -1)) + wind * 28) % (getWidth() + 180);
            double x = drift - 120;
            double y = 64 + layer * 42 + Math.sin(time * 0.35 + layer) * 8;
            int alpha = 70 - layer * 12 + (int) (flash * 36);
            setDrawColor(new Color(18 + layer * 8, 27 + layer * 8, 39 + layer * 8, alpha));

            drawFilledEllipse(x, y, 160, 34);
            drawFilledEllipse(x + 48, y - 14, 124, 42);
            drawFilledEllipse(x + 120, y + 3, 115, 32);
            drawFilledEllipse(x - getWidth() - 180, y, 160, 34);
            drawFilledEllipse(x + getWidth() + 180, y - 14, 124, 42);
        }
    }

    private void drawDistantLights(double flash) {
        for (int i = 0; i < 36; i++) {
            double x = ((i * 97) % 719) / 719.0 * getWidth();
            double y = (0.18 + ((i * 53) % 240) / 1000.0) * getHeight();
            double twinkle = 0.5 + Math.sin(time * (0.6 + (i % 5) * 0.17) + i) * 0.5;
            int alpha = clampInt((int) (35 + twinkle * 80 + flash * 60), 0, 180);

            setDrawColor(new Color(219, 238, 255, alpha));
            drawFilledCircle(x, y, 0.8 + twinkle * 1.2);
        }
    }

    private void drawBuildings(double streetY, double flash) {
        for (int layer = 2; layer >= 0; layer--) {
            double scale = 1.0 - layer * 0.14;
            double baseY = streetY + layer * 20;

            for (Building building : buildings) {
                double x = (building.xRatio * getWidth()) - layer * 18;
                double w = building.widthRatio * getWidth() * scale;
                double h = building.heightRatio * getHeight() * scale;
                double y = baseY - h;
                int shade = 17 + layer * 13;

                setDrawColor(new Color(shade, shade + 7, shade + 14));
                drawFilledRect(x, y, w, h);

                if (building.antenna) {
                    setDrawColor(new Color(99, 125, 143, 100 + (int) (flash * 50)));
                    drawLine(x + w * 0.72, y, x + w * 0.72, y - h * 0.16, 1.0);
                    drawFilledCircle(x + w * 0.72, y - h * 0.16, 2.0);
                }

                drawWindows(building, x, y, w, h, layer, flash);
            }
        }
    }

    private void drawWindows(Building building, double x, double y, double w, double h, int layer, double flash) {
        double padX = w * 0.16;
        double padY = h * 0.12;
        double cellW = (w - padX * 2.0) / building.columns;
        double cellH = (h - padY * 2.0) / building.rows;

        for (int row = 0; row < building.rows; row++) {
            for (int col = 0; col < building.columns; col++) {
                double pattern = Math.sin(building.seed * 17.0 + row * 2.1 + col * 3.7);
                double flicker = Math.sin(time * (0.7 + building.seed) + row + col * 0.3);
                if (pattern + flicker * 0.45 < -0.15) continue;

                double wx = x + padX + col * cellW + cellW * 0.22;
                double wy = y + padY + row * cellH + cellH * 0.2;
                double ww = Math.max(2, cellW * 0.42);
                double wh = Math.max(2, cellH * 0.38);
                int warm = clampInt(122 + (int) (pattern * 55) + (int) (flash * 60), 55, 235);
                int alpha = clampInt(115 - layer * 18 + (int) (flash * 60), 42, 220);

                setDrawColor(new Color(255, warm, 92, alpha));
                drawFilledRect(wx, wy, ww, wh);
            }
        }
    }

    private void drawStreet(double streetY, double flash) {
        setDrawColor(new Color(8 + (int) (flash * 26), 13 + (int) (flash * 28), 19 + (int) (flash * 32)));
        drawFilledRect(0, streetY, getWidth(), getHeight() - (int) streetY);

        for (int y = (int) streetY; y < getHeight(); y += 12) {
            int alpha = clampInt(28 + (int) ((y - streetY) * 0.25) + (int) (flash * 35), 0, 120);
            setDrawColor(new Color(126, 166, 188, alpha));
            drawLine(0, y, getWidth(), y + Math.sin(time + y * 0.03) * 3, 1.0);
        }

        setDrawColor(new Color(255, 196, 92, 58 + (int) (flash * 50)));
        for (int x = 18; x < getWidth(); x += 68) {
            drawLine(x, streetY + 54, x + 34, streetY + 51, 4.0);
        }
    }

    private void updateAndDrawRain(double delta, double wind, double streetY, double flash) {
        for (RainDrop drop : drops) {
            drop.yRatio += delta * drop.speed;
            drop.xRatio += delta * wind * drop.drift;
            if (drop.yRatio > 1.12 || drop.xRatio < -0.12 || drop.xRatio > 1.12) {
                resetDrop(drop);
            }

            double x = drop.xRatio * getWidth();
            double y = drop.yRatio * getHeight();
            double length = drop.length * (0.8 + Math.abs(wind) * 0.45);
            int alpha = clampInt((int) (70 + drop.depth * 120 + flash * 55), 0, 220);

            setDrawColor(new Color(157, 205, 235, alpha));
            drawLine(x, y, x + wind * length * 0.9, y + length, 0.8 + drop.depth * 1.7);

            if (y > streetY && random.nextDouble() < 0.03) {
                splashes.set(random.nextInt(splashes.size()), new Splash(
                        clamp(x / Math.max(1.0, getWidth()), 0.0, 1.0),
                        clamp((y - streetY) / Math.max(1.0, getHeight() - streetY), 0.0, 1.0),
                        time,
                        0.5 + random.nextDouble() * 1.2));
            }
        }
    }

    private void drawSplashes(double streetY, double wind) {
        for (Splash splash : splashes) {
            double age = (time - splash.birthTime + 20.0) % 2.0;
            double fade = Math.max(0.0, 1.0 - age / 2.0);
            if (fade <= 0.01) continue;

            double x = splash.xRatio * getWidth() + wind * age * 18;
            double y = streetY + splash.yRatio * (getHeight() - streetY);
            double radius = splash.size * (4.0 + age * 9.0);
            int alpha = clampInt((int) (fade * 90), 0, 95);

            setDrawColor(new Color(178, 219, 242, alpha));
            drawArc(x - radius, y - radius * 0.32, radius * 2.0, radius * 0.64, 0.0, Math.PI);
        }
    }

    private void drawUmbrella(double mouseX, double mouseY, double flash) {
        double x = clamp(mouseX, 70, getWidth() - 70);
        double y = clamp(mouseY, getHeight() * 0.44, getHeight() * 0.78);
        double sway = Math.sin(time * 2.4) * 3.0;

        setDrawColor(new Color(3, 8, 13, 130));
        drawFilledEllipse(x - 48, y + 76, 96, 13);

        setDrawColor(new Color(214, 139, 79, 34 + (int) (flash * 28)));
        drawFilledEllipse(x - 72, y + 71, 144, 18);

        drawPedestrian(x + sway, y, flash);

        setDrawColor(new Color(30 + (int) (flash * 50), 42, 62));
        drawLine(x + sway, y + 8, x + sway, y + 86, 3.0);
        drawArc(x + sway - 17, y + 65, 34, 38, 0.05, Math.PI * 0.82);

        setDrawColor(new Color(199, 54, 86));
        drawFilledTriangle(x - 78 + sway, y + 10, x + sway, y - 42, x + sway, y + 20);
        setDrawColor(new Color(238, 170, 84));
        drawFilledTriangle(x + sway, y - 42, x + 78 + sway, y + 10, x + sway, y + 20);
        setDrawColor(new Color(245, 222, 148, 210));
        drawArc(x - 78 + sway, y - 42, 156, 74, 0.0, Math.PI);
        drawLine(x - 78 + sway, y + 10, x + 78 + sway, y + 10, 1.2);

        for (int i = -2; i <= 2; i++) {
            double ribX = x + sway + i * 26;
            setDrawColor(new Color(255, 237, 190, 80));
            drawLine(x + sway, y - 37, ribX, y + 10, 1.0);
        }

        setDrawColor(new Color(232, 238, 221, 190));
        drawFilledCircle(x + sway, y - 41, 4);
    }

    private void drawPedestrian(double x, double y, double flash) {
        setDrawColor(new Color(4, 7, 11, 125));
        drawFilledEllipse(x - 25, y + 76, 50, 9);

        setDrawColor(new Color(9 + (int) (flash * 18), 13 + (int) (flash * 16), 18 + (int) (flash * 18)));
        drawFilledCircle(x, y + 20, 10);
        drawFilledRect(x - 11, y + 28, 22, 35);

        setDrawColor(new Color(14 + (int) (flash * 20), 19 + (int) (flash * 18), 27 + (int) (flash * 18)));
        drawFilledTriangle(x - 12, y + 33, x + 12, y + 33, x, y + 73);

        setDrawColor(new Color(5, 8, 12, 210));
        drawLine(x - 5, y + 64, x - 18, y + 82, 3.0);
        drawLine(x + 5, y + 64, x + 17, y + 82, 3.0);

        setDrawColor(new Color(225, 207, 151, 55 + (int) (flash * 40)));
        drawLine(x - 9, y + 33, x + 9, y + 33, 1.0);
    }

    private RainDrop newDrop(double yRatio) {
        return new RainDrop(
                random.nextDouble(),
                yRatio,
                0.72 + random.nextDouble() * 1.45,
                9 + random.nextDouble() * 18,
                0.08 + random.nextDouble() * 0.32,
                random.nextDouble());
    }

    private void resetDrop(RainDrop drop) {
        drop.xRatio = random.nextDouble();
        drop.yRatio = -random.nextDouble() * 0.18;
        drop.speed = 0.72 + random.nextDouble() * 1.45;
        drop.length = 9 + random.nextDouble() * 18;
        drop.drift = 0.08 + random.nextDouble() * 0.32;
        drop.depth = random.nextDouble();
    }

    private void drawHud() {
        String message = "Move mouse for wind. Hold left button for lightning.";
        setFont(12);
        int[] textSize = getTextSize(message);
        int x = (getWidth() - textSize[BasicDisplay.TEXT_SIZE_WIDTH]) / 2;
        int y = getHeight() - 20;

        setDrawColor(new Color(4, 8, 14, 160));
        drawFilledRect(x - 10, y - 18, textSize[BasicDisplay.TEXT_SIZE_WIDTH] + 20, 24);
        setDrawColor(new Color(230, 243, 255, 205));
        drawText(message, x, y);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private record Building(
            double xRatio,
            double widthRatio,
            double heightRatio,
            int columns,
            int rows,
            double seed,
            boolean antenna) {
    }

    private static class RainDrop {
        double xRatio;
        double yRatio;
        double speed;
        double length;
        double drift;
        double depth;

        RainDrop(double xRatio, double yRatio, double speed, double length, double drift, double depth) {
            this.xRatio = xRatio;
            this.yRatio = yRatio;
            this.speed = speed;
            this.length = length;
            this.drift = drift;
            this.depth = depth;
        }
    }

    private record Splash(double xRatio, double yRatio, double birthTime, double size) {
    }
}
