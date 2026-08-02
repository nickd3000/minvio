package com.physmo.reference.gallery.ai;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrismaticBloom extends MinvioApp {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 540;
    private static final int SHARD_COUNT = 110;
    private static final int SPARK_COUNT = 150;

    private final Gradient skyGradient = new Gradient();
    private final Gradient bloomGradient = new Gradient();
    private final List<Shard> shards = new ArrayList<>();
    private final List<Spark> sparks = new ArrayList<>();
    private double time = 0;

    public static void main(String... args) {
        MinvioApp app = new PrismaticBloom();
        app.start(WIDTH, HEIGHT, "Prismatic Bloom", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        skyGradient.addColor(0.0, new Color(7, 12, 28));
        skyGradient.addColor(0.35, new Color(19, 40, 69));
        skyGradient.addColor(0.7, new Color(42, 34, 63));
        skyGradient.addColor(1.0, new Color(10, 17, 30));

        bloomGradient.addColor(0.0, new Color(255, 244, 194));
        bloomGradient.addColor(0.28, new Color(84, 224, 205));
        bloomGradient.addColor(0.56, new Color(255, 126, 137));
        bloomGradient.addColor(0.78, new Color(128, 171, 255));
        bloomGradient.addColor(1.0, new Color(253, 247, 221));

        Random random = new Random(20260801);
        for (int i = 0; i < SHARD_COUNT; i++) {
            shards.add(new Shard(
                    random.nextDouble() * Math.PI * 2.0,
                    0.22 + random.nextDouble() * 0.84,
                    14 + random.nextDouble() * 48,
                    3 + random.nextDouble() * 10,
                    0.15 + random.nextDouble() * 0.55,
                    random.nextDouble() * Math.PI * 2.0,
                    random.nextDouble()));
        }

        for (int i = 0; i < SPARK_COUNT; i++) {
            sparks.add(new Spark(
                    random.nextDouble(),
                    random.nextDouble(),
                    0.5 + random.nextDouble() * 1.4,
                    0.3 + random.nextDouble() * 1.7,
                    random.nextDouble() * Math.PI * 2.0));
        }
    }

    @Override
    public void draw(double delta) {
        time += delta;

        double cx = getWidth() / 2.0;
        double cy = getHeight() * 0.52;
        double mouseDistance = Math.hypot(getMouseX(), getMouseY());
        double focusX = mouseDistance > 1 ? getMouseX() : cx + Math.cos(time * 0.35) * getWidth() * 0.24;
        double focusY = mouseDistance > 1 ? getMouseY() : cy + Math.sin(time * 0.28) * getHeight() * 0.18;
        boolean pressed = getBasicDisplay().getMouseButtonLeft();
        double bloom = pressed ? 1.0 : 0.0;

        drawSky();
        drawAurora(focusX, focusY, bloom);
        drawSparks(focusX, focusY);
        drawShardField(cx, cy, focusX, focusY, bloom);
        drawCentralBloom(cx, cy, focusX, focusY, bloom);
        drawReflection(cx, cy, focusX, bloom);
        drawHud();
    }

    private void drawSky() {
        for (int y = 0; y < getHeight(); y += 3) {
            double pos = y / (double) Math.max(1, getHeight() - 1);
            setDrawColor(skyGradient.getColor(pos));
            drawFilledRect(0, y, getWidth(), 3);
        }
    }

    private void drawAurora(double focusX, double focusY, double bloom) {
        int strands = 8;
        for (int strand = 0; strand < strands; strand++) {
            double strandPos = strand / (double) (strands - 1);
            Color base = bloomGradient.getColor(strandPos);
            double yBase = 65 + strand * 22 + Math.sin(time * 0.45 + strand) * 24;
            double previousX = -20;
            double previousY = yBase;

            for (int x = 0; x <= getWidth() + 20; x += 12) {
                double focusPull = (focusX - x) / Math.max(1.0, getWidth());
                double wave = Math.sin(x * 0.018 + time * (0.8 + strandPos) + strand * 1.7) * 34;
                double shimmer = Math.sin(x * 0.046 - time * 1.6 + strand) * 9;
                double y = yBase + wave + shimmer + focusPull * 35 - (focusY / getHeight()) * 22;
                int alpha = clampInt(22 + strand * 10 + (int) (bloom * 42), 0, 125);

                setDrawColor(withAlpha(base, alpha));
                drawLine(previousX, previousY, x, y, 3.0 + strandPos * 4.5);
                previousX = x;
                previousY = y;
            }
        }
    }

    private void drawSparks(double focusX, double focusY) {
        for (Spark spark : sparks) {
            double x = spark.xRatio * getWidth();
            double y = spark.yRatio * getHeight() * 0.74;
            double distance = Math.hypot(focusX - x, focusY - y);
            double focusGlow = Math.max(0.0, 1.0 - distance / 180.0);
            double twinkle = 0.5 + Math.sin(time * spark.speed + spark.phase) * 0.5;
            double radius = 0.8 + spark.size * (0.3 + twinkle * 0.7) + focusGlow * 2.8;
            int alpha = clampInt((int) (55 + twinkle * 80 + focusGlow * 95), 0, 230);

            setDrawColor(new Color(234, 252, 255, alpha));
            drawFilledCircle(x, y, radius);
        }
    }

    private void drawShardField(double cx, double cy, double focusX, double focusY, double bloom) {
        double fieldRadius = Math.min(getWidth(), getHeight()) * 0.48;

        for (int i = 0; i < shards.size(); i++) {
            Shard shard = shards.get(i);
            double orbit = shard.angle + time * shard.speed * 0.12;
            double radius = fieldRadius * shard.radius + Math.sin(time * shard.speed + shard.phase) * 18;
            double x = cx + Math.cos(orbit) * radius;
            double y = cy + Math.sin(orbit) * radius * 0.72;
            double distance = Math.hypot(focusX - x, focusY - y);
            double pull = Math.max(0.0, 1.0 - distance / 210.0);

            x += (focusX - x) * pull * (0.08 + bloom * 0.08);
            y += (focusY - y) * pull * (0.08 + bloom * 0.08);

            double angle = orbit + Math.sin(time + shard.phase) * 0.35 + pull * 0.7;
            double length = shard.length * (0.8 + pull * 0.8 + bloom * 0.25);
            double width = shard.width * (0.9 + pull * 1.3);
            Color color = bloomGradient.getColor((shard.colorPosition + time * 0.035 + pull * 0.2) % 1.0);
            int alpha = clampInt((int) (55 + pull * 125 + bloom * 45), 0, 215);

            drawShard(x, y, angle, length, width, withAlpha(color, alpha));
        }
    }

    private void drawShard(double x, double y, double angle, double length, double width, Color color) {
        pushMatrix();
        translate(x, y);
        rotate(angle);

        int[] xPoints = new int[]{0, (int) width, 0, (int) -width};
        int[] yPoints = new int[]{(int) -length, 0, (int) length, 0};

        setDrawColor(color);
        drawFilledPolygon(xPoints, yPoints, 4);

        setDrawColor(withAlpha(Color.WHITE, Math.min(180, color.getAlpha() + 20)));
        drawLine(0, -length, width, 0, 1.0);
        drawLine(0, -length, -width, 0, 1.0);

        popMatrix();
    }

    private void drawCentralBloom(double cx, double cy, double focusX, double focusY, double bloom) {
        double gaze = Math.atan2(focusY - cy, focusX - cx);

        for (int ring = 5; ring >= 0; ring--) {
            int petals = 9 + ring * 5;
            double ringRadius = 26 + ring * 24;
            double petalLength = 54 - ring * 3 + bloom * 20;
            double petalWidth = 12 + ring * 2.5;
            double ringTurn = time * (0.18 + ring * 0.035) * (ring % 2 == 0 ? 1 : -1);

            for (int petal = 0; petal < petals; petal++) {
                double petalPos = petal / (double) petals;
                double angle = petalPos * Math.PI * 2.0 + ringTurn + gaze * 0.05;
                double pulse = 0.85 + Math.sin(time * 1.7 + ring + petalPos * Math.PI * 2.0) * 0.15;
                Color color = bloomGradient.getColor((petalPos + ring * 0.12 + time * 0.025) % 1.0);

                pushMatrix();
                translate(cx, cy);
                rotate(angle);
                translate(ringRadius * pulse, 0);
                rotate(Math.sin(time + petalPos * Math.PI * 2.0) * 0.16);

                setDrawColor(withAlpha(color, 76 + ring * 20));
                drawFilledEllipse(-petalLength * 0.15, -petalWidth, petalLength, petalWidth * 2.0);

                setDrawColor(withAlpha(Color.WHITE, 32 + ring * 8));
                drawArc(-petalLength * 0.12, -petalWidth, petalLength * 0.94, petalWidth * 2.0, -0.8, 1.6);
                popMatrix();
            }
        }

        for (int r = 34; r > 0; r -= 5) {
            double pos = r / 34.0;
            setDrawColor(withAlpha(bloomGradient.getColor(1.0 - pos * 0.7), (int) (95 + (1.0 - pos) * 120)));
            drawFilledCircle(cx, cy, r + bloom * 7);
        }
        setDrawColor(new Color(255, 255, 255, 220));
        drawFilledCircle(cx, cy, 5 + Math.sin(time * 3.0) * 1.5 + bloom * 3);
    }

    private void drawReflection(double cx, double cy, double focusX, double bloom) {
        double waterY = getHeight() * 0.78;

        for (int band = 0; band < 24; band++) {
            double y = waterY + band * 7;
            double span = 170 - band * 4 + bloom * 30;
            double wobble = Math.sin(time * 1.7 + band * 0.44) * (10 + band * 0.7);
            double drift = (focusX - cx) * 0.05;
            Color color = bloomGradient.getColor((band / 24.0 + time * 0.02) % 1.0);

            setDrawColor(withAlpha(color, clampInt(76 - band * 2 + (int) (bloom * 30), 8, 110)));
            drawLine(cx - span + wobble + drift, y, cx + span + wobble * 0.4 + drift, y + 1, 1.0 + band * 0.08);
        }

        setDrawColor(new Color(3, 8, 15, 90));
        drawFilledRect(0, waterY + 35, getWidth(), getHeight() - (int) waterY);
    }

    private void drawHud() {
        String message = "Move mouse. Hold left button.";
        setFont(12);
        int[] textSize = getTextSize(message);
        int x = (getWidth() - textSize[BasicDisplay.TEXT_SIZE_WIDTH]) / 2;
        int y = getHeight() - 20;

        setDrawColor(new Color(3, 8, 15, 140));
        drawFilledRect(x - 10, y - 18, textSize[BasicDisplay.TEXT_SIZE_WIDTH] + 20, 24);
        setDrawColor(new Color(232, 246, 255, 190));
        drawText(message, x, y);
    }

    private static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), clampInt(alpha, 0, 255));
    }

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private record Shard(
            double angle,
            double radius,
            double length,
            double width,
            double speed,
            double phase,
            double colorPosition) {
    }

    private record Spark(double xRatio, double yRatio, double size, double speed, double phase) {
    }
}
