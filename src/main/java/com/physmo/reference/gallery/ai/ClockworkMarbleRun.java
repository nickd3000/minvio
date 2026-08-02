package com.physmo.reference.gallery.ai;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ClockworkMarbleRun extends MinvioApp {

    private static final int WIDTH = 760;
    private static final int HEIGHT = 540;
    private static final int MARBLE_COUNT = 14;

    private final Gradient wallGradient = new Gradient();
    private final Gradient marbleGradient = new Gradient();
    private final List<Segment> track = new ArrayList<>();
    private double time = 0;

    public static void main(String... args) {
        MinvioApp app = new ClockworkMarbleRun();
        app.start(WIDTH, HEIGHT, "Clockwork Marble Run", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        wallGradient.addColor(0.0, new Color(31, 33, 38));
        wallGradient.addColor(0.42, new Color(58, 64, 68));
        wallGradient.addColor(1.0, new Color(25, 28, 32));

        marbleGradient.addColor(0.0, new Color(255, 216, 97));
        marbleGradient.addColor(0.22, new Color(78, 211, 184));
        marbleGradient.addColor(0.46, new Color(252, 104, 112));
        marbleGradient.addColor(0.7, new Color(111, 148, 255));
        marbleGradient.addColor(1.0, new Color(244, 247, 231));

        track.add(new Segment(82, 115, 210, 28, 296, 38, 402, 121));
        track.add(new Segment(402, 121, 512, 210, 665, 92, 676, 212));
        track.add(new Segment(676, 212, 680, 330, 514, 336, 438, 258));
        track.add(new Segment(438, 258, 350, 166, 220, 239, 266, 342));
        track.add(new Segment(266, 342, 319, 460, 527, 407, 602, 452));
        track.add(new Segment(602, 452, 466, 500, 218, 494, 102, 407));
        track.add(new Segment(102, 407, 11, 338, 58, 197, 82, 115));
    }

    @Override
    public void draw(double delta) {
        double mouseRatio = getMouseX() > 0 ? getMouseX() / (double) Math.max(1, getWidth()) : 0.5;
        double speed = 0.25 + mouseRatio * 1.55;
        if (getBasicDisplay().getMouseButtonLeft()) speed *= 2.15;
        time += delta * speed;

        drawWorkshopBackground();
        drawLampGlow();
        drawMachineFrame();
        drawTrackShadows();
        drawGears(speed);
        drawLiftChain(speed);
        drawTrack();
        drawMarbles();
        drawCollectors();
        drawHud(speed);
    }

    private void drawWorkshopBackground() {
        for (int y = 0; y < getHeight(); y += 4) {
            double pos = y / (double) Math.max(1, getHeight() - 1);
            setDrawColor(wallGradient.getColor(pos));
            drawFilledRect(0, y, getWidth(), 4);
        }

        setDrawColor(new Color(12, 14, 16, 80));
        for (int x = 0; x < getWidth(); x += scaleX(76)) {
            drawLine(x, 0, x - scaleX(22), getHeight(), 1.0);
        }

        setDrawColor(new Color(235, 221, 180, 38));
        for (int y = scaleY(34); y < getHeight(); y += scaleY(58)) {
            drawLine(0, y, getWidth(), y + Math.sin(time + y * 0.01) * 2.0, 1.0);
        }
    }

    private void drawLampGlow() {
        double lampX = scaleX(392);
        double lampY = scaleY(18);

        for (int radius = 260; radius > 60; radius -= 38) {
            int alpha = Math.max(5, 32 - radius / 12);
            setDrawColor(new Color(255, 211, 126, alpha));
            drawFilledCircle(lampX, lampY + scaleY(94), scaleX(radius));
        }

        setDrawColor(new Color(19, 20, 21, 150));
        drawLine(lampX, 0, lampX, scaleY(32), 2.0);

        int[] shadeX = new int[]{
                (int) (lampX - scaleX(35)),
                (int) (lampX + scaleX(35)),
                (int) (lampX + scaleX(22)),
                (int) (lampX - scaleX(22))
        };
        int[] shadeY = new int[]{
                (int) scaleY(32),
                (int) scaleY(32),
                (int) scaleY(58),
                (int) scaleY(58)
        };
        setDrawColor(new Color(115, 83, 48));
        drawFilledPolygon(shadeX, shadeY, 4);
        setDrawColor(new Color(244, 206, 128, 145));
        drawLine(lampX - scaleX(24), scaleY(58), lampX + scaleX(24), scaleY(58), 3.0);
    }

    private void drawMachineFrame() {
        double floorY = scaleY(482);

        setDrawColor(new Color(13, 15, 18, 150));
        drawFilledRect(0, floorY, getWidth(), getHeight() - floorY);

        drawPost(66, 96, 398);
        drawPost(688, 156, 326);
        drawPost(388, 82, 402);
        drawPost(248, 280, 205);
        drawPost(608, 314, 173);

        setDrawColor(new Color(160, 138, 105, 160));
        drawLine(scaleX(44), scaleY(482), scaleX(714), scaleY(482), 5.0);
        drawLine(scaleX(64), scaleY(106), scaleX(698), scaleY(214), 3.0);
        drawLine(scaleX(98), scaleY(410), scaleX(608), scaleY(452), 3.0);

        for (int x = 58; x <= 700; x += 92) {
            setDrawColor(new Color(5, 7, 9, 90));
            drawFilledCircle(scaleX(x + 2), scaleY(484), scaleX(5));
            setDrawColor(new Color(225, 198, 137, 130));
            drawFilledCircle(scaleX(x), scaleY(482), scaleX(4));
        }
    }

    private void drawPost(double x, double topY, double height) {
        double px = scaleX(x);
        double py = scaleY(topY);
        double ph = scaleY(height);
        double pw = scaleX(14);

        setDrawColor(new Color(8, 10, 13, 135));
        drawFilledRect(px + scaleX(5), py + scaleY(6), pw, ph);
        setDrawColor(new Color(84, 72, 58));
        drawFilledRect(px, py, pw, ph);
        setDrawColor(new Color(176, 151, 107, 115));
        drawLine(px + pw * 0.25, py + 4, px + pw * 0.25, py + ph - 4, 1.0);
    }

    private void drawTrackShadows() {
        for (Segment segment : track) {
            drawBezier(segment, new Color(3, 5, 8, 120), 13.0, scaleX(5), scaleY(7));
        }
    }

    private void drawTrack() {
        for (Segment segment : track) {
            drawBezier(segment, new Color(157, 139, 111), 9.0, 0, 0);
            drawBezier(segment, new Color(229, 201, 139), 3.0, 0, -scaleY(2));
            drawBezier(segment, new Color(47, 43, 39), 1.2, 0, scaleY(4));
        }

        for (int i = 0; i < 34; i++) {
            PointOnTrack point = sampleTrack((i / 34.0 + 0.012) % 1.0);
            setDrawColor(new Color(22, 20, 18, 135));
            drawFilledCircle(point.x, point.y + scaleY(5), scaleX(3.4));
            setDrawColor(new Color(238, 211, 151, 155));
            drawFilledCircle(point.x, point.y, scaleX(2.6));
        }
    }

    private void drawGears(double speed) {
        drawGear(scaleX(162), scaleY(258), scaleX(48), 15, time * 1.4 * speed, new Color(137, 157, 153));
        drawGear(scaleX(564), scaleY(188), scaleX(58), 18, -time * 1.1 * speed, new Color(174, 140, 98));
        drawGear(scaleX(444), scaleY(388), scaleX(40), 13, time * 1.7 * speed, new Color(128, 152, 188));
    }

    private void drawGear(double x, double y, double radius, int teeth, double angle, Color color) {
        pushMatrix();
        translate(x, y);
        rotate(angle);

        for (int i = 0; i < teeth; i++) {
            double toothAngle = i * Math.PI * 2.0 / teeth;
            pushMatrix();
            rotate(toothAngle);
            setDrawColor(new Color(5, 7, 9, 95));
            drawFilledRect(radius - scaleX(2), -scaleY(5), scaleX(18), scaleY(10));
            setDrawColor(color.darker());
            drawFilledRect(radius - scaleX(4), -scaleY(5), scaleX(18), scaleY(10));
            popMatrix();
        }

        setDrawColor(new Color(5, 7, 9, 105));
        drawFilledCircle(scaleX(3), scaleY(4), radius + scaleX(5));
        setDrawColor(color);
        drawFilledCircle(0, 0, radius);
        setDrawColor(new Color(31, 35, 37, 175));
        drawFilledCircle(0, 0, radius * 0.57);
        setDrawColor(new Color(235, 225, 190, 165));
        drawCircle(0, 0, radius);
        drawCircle(0, 0, radius * 0.34);

        for (int i = 0; i < 6; i++) {
            double spokeAngle = i * Math.PI / 3.0;
            drawLine(
                    Math.cos(spokeAngle) * radius * 0.28,
                    Math.sin(spokeAngle) * radius * 0.28,
                    Math.cos(spokeAngle) * radius * 0.88,
                    Math.sin(spokeAngle) * radius * 0.88,
                    3.0);
        }

        popMatrix();
    }

    private void drawLiftChain(double speed) {
        double x = scaleX(694);
        double top = scaleY(138);
        double bottom = scaleY(438);
        double phase = (time * 68 * speed) % scaleY(34);

        setDrawColor(new Color(14, 17, 19, 135));
        drawLine(x + scaleX(7), top, x + scaleX(7), bottom, 8.0);
        setDrawColor(new Color(131, 139, 128));
        drawLine(x, top, x, bottom, 4.0);

        for (double y = top + phase - scaleY(34); y < bottom; y += scaleY(34)) {
            setDrawColor(new Color(199, 174, 112));
            drawFilledRect(x - scaleX(18), y, scaleX(35), scaleY(9));
            setDrawColor(new Color(63, 55, 41));
            drawRect(x - scaleX(18), y, scaleX(35), scaleY(9));
        }
    }

    private void drawMarbles() {
        for (int i = 0; i < MARBLE_COUNT; i++) {
            double progress = (time * 0.08 + i / (double) MARBLE_COUNT) % 1.0;
            PointOnTrack point = sampleTrack(progress);
            Color color = marbleGradient.getColor((i / (double) MARBLE_COUNT + time * 0.04) % 1.0);
            double wobble = Math.sin(time * 7.0 + i) * scaleX(1.4);
            double radius = scaleX(7.5 + Math.sin(time * 4.0 + i * 0.6) * 0.9);

            setDrawColor(new Color(2, 4, 7, 115));
            drawFilledCircle(point.x + scaleX(4), point.y + scaleY(7), radius * 1.05);
            setDrawColor(color);
            drawFilledCircle(point.x + wobble, point.y, radius);
            setDrawColor(new Color(255, 255, 255, 170));
            drawFilledCircle(point.x - radius * 0.34 + wobble, point.y - radius * 0.38, radius * 0.24);
        }
    }

    private void drawCollectors() {
        drawCup(112, 414, 76, new Color(168, 100, 82));
        drawCup(620, 454, 86, new Color(90, 133, 151));

        for (int i = 0; i < 8; i++) {
            double x = scaleX(104 + i * 11);
            double y = scaleY(423 + Math.sin(time * 3.0 + i) * 4);
            setDrawColor(marbleGradient.getColor((i * 0.13 + time * 0.03) % 1.0));
            drawFilledCircle(x, y, scaleX(5.5));
        }

        for (int i = 0; i < 9; i++) {
            double x = scaleX(638 + (i % 5) * 11 + (i / 5) * 6);
            double y = scaleY(470 + (i / 5) * 11 + Math.sin(time * 2.2 + i) * 2);
            setDrawColor(new Color(3, 5, 8, 80));
            drawFilledCircle(x + scaleX(3), y + scaleY(4), scaleX(5.8));
            setDrawColor(marbleGradient.getColor((0.47 + i * 0.09 + time * 0.025) % 1.0));
            drawFilledCircle(x, y, scaleX(5.4));
            setDrawColor(new Color(255, 255, 255, 135));
            drawFilledCircle(x - scaleX(1.8), y - scaleY(1.8), scaleX(1.5));
        }
    }

    private void drawCup(double x, double y, double width, Color color) {
        int[] xPoints = new int[]{
                (int) scaleX(x),
                (int) scaleX(x + width),
                (int) scaleX(x + width - 15),
                (int) scaleX(x + 15)
        };
        int[] yPoints = new int[]{
                (int) scaleY(y),
                (int) scaleY(y),
                (int) scaleY(y + 58),
                (int) scaleY(y + 58)
        };

        setDrawColor(new Color(4, 6, 9, 110));
        drawFilledRect(scaleX(x + 8), scaleY(y + 54), scaleX(width), scaleY(11));
        setDrawColor(color);
        drawFilledPolygon(xPoints, yPoints, 4);
        setDrawColor(new Color(238, 221, 178, 135));
        drawLine(scaleX(x), scaleY(y), scaleX(x + width), scaleY(y), 3.0);
        setDrawColor(new Color(23, 21, 20, 130));
        drawLine(scaleX(x + 15), scaleY(y + 58), scaleX(x + width - 15), scaleY(y + 58), 2.0);
    }

    private void drawHud(double speed) {
        String message = String.format("Move mouse to tune speed. Hold left button for overdrive. x%.1f", speed);
        setFont(12);
        int[] textSize = getTextSize(message);
        int x = (getWidth() - textSize[BasicDisplay.TEXT_SIZE_WIDTH]) / 2;
        int y = getHeight() - 20;

        setDrawColor(new Color(6, 8, 10, 155));
        drawFilledRect(x - 10, y - 18, textSize[BasicDisplay.TEXT_SIZE_WIDTH] + 20, 24);
        setDrawColor(new Color(241, 229, 193, 210));
        drawText(message, x, y);
    }

    private void drawBezier(Segment segment, Color color, double width, double offsetX, double offsetY) {
        setDrawColor(color);

        PointOnTrack previous = segment.sample(0.0, this::scaleX, this::scaleY);
        previous = new PointOnTrack(previous.x + offsetX, previous.y + offsetY);

        for (int i = 1; i <= 28; i++) {
            double t = i / 28.0;
            PointOnTrack point = segment.sample(t, this::scaleX, this::scaleY);
            point = new PointOnTrack(point.x + offsetX, point.y + offsetY);
            drawLine(previous.x, previous.y, point.x, point.y, width);
            previous = point;
        }
    }

    private PointOnTrack sampleTrack(double progress) {
        double scaled = progress * track.size();
        int index = Math.min(track.size() - 1, (int) scaled);
        double local = scaled - index;
        return track.get(index).sample(local, this::scaleX, this::scaleY);
    }

    private int scaleX(int value) {
        return (int) scaleX((double) value);
    }

    private int scaleY(int value) {
        return (int) scaleY((double) value);
    }

    private double scaleX(double value) {
        return value * getWidth() / WIDTH;
    }

    private double scaleY(double value) {
        return value * getHeight() / HEIGHT;
    }

    private record PointOnTrack(double x, double y) {
    }

    @FunctionalInterface
    private interface ScaleFunction {
        double apply(double value);
    }

    private record Segment(double x1, double y1, double cx1, double cy1, double cx2, double cy2, double x2, double y2) {
        PointOnTrack sample(double t, ScaleFunction scaleX, ScaleFunction scaleY) {
            double a = 1.0 - t;
            double x = a * a * a * x1 + 3.0 * a * a * t * cx1 + 3.0 * a * t * t * cx2 + t * t * t * x2;
            double y = a * a * a * y1 + 3.0 * a * a * t * cy1 + 3.0 * a * t * t * cy2 + t * t * t * y2;
            return new PointOnTrack(scaleX.apply(x), scaleY.apply(y));
        }
    }
}
