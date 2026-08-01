package com.physmo.reference.gallery.ai;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TransitPulseMap extends MinvioApp {

    private static final int WIDTH = 760;
    private static final int HEIGHT = 540;

    private final List<Station> stations = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();
    private double time = 0;

    public static void main(String... args) {
        MinvioApp app = new TransitPulseMap();
        app.start(WIDTH, HEIGHT, "Transit Pulse Map", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        stations.add(new Station("North Pier", 105, 94, true));
        stations.add(new Station("Glassworks", 214, 112, false));
        stations.add(new Station("Library", 327, 122, false));
        stations.add(new Station("Observatory", 464, 96, true));
        stations.add(new Station("Market", 594, 128, false));
        stations.add(new Station("Old Gate", 668, 216, true));
        stations.add(new Station("Museum", 526, 224, false));
        stations.add(new Station("Central", 384, 266, true));
        stations.add(new Station("Arcade", 246, 252, false));
        stations.add(new Station("Harbor", 102, 258, true));
        stations.add(new Station("Canal", 174, 374, false));
        stations.add(new Station("Foundry", 308, 406, true));
        stations.add(new Station("Garden", 452, 392, false));
        stations.add(new Station("Depot", 610, 414, true));

        routes.add(new Route("Azure", new Color(55, 181, 232), 0, 1, 2, 3, 6, 7, 8, 9));
        routes.add(new Route("Amber", new Color(247, 176, 64), 9, 10, 11, 12, 13, 5));
        routes.add(new Route("Rose", new Color(237, 83, 122), 3, 7, 11));
        routes.add(new Route("Mint", new Color(88, 215, 167), 1, 8, 10));
        routes.add(new Route("Violet", new Color(146, 113, 238), 4, 6, 12));
    }

    @Override
    public void draw(double delta) {
        time += delta;

        double mouseX = getMouseX() > 0 ? getMouseX() : scaleX(384);
        double mouseY = getMouseY() > 0 ? getMouseY() : scaleY(266);
        Station selected = findNearestStation(mouseX, mouseY);
        double energy = getBasicDisplay().getMouseButtonLeft() ? 1.0 : 0.0;

        drawBackground(mouseX, mouseY);
        drawMapTexture();
        drawRoutes(energy);
        drawTrains(energy);
        drawStations(selected, energy);
        drawInfoPanel(selected, energy);
        drawHud();
    }

    private void drawBackground(double mouseX, double mouseY) {
        for (int y = 0; y < getHeight(); y += 3) {
            double pos = y / (double) Math.max(1, getHeight() - 1);
            int red = (int) (14 + pos * 16);
            int green = (int) (20 + pos * 20);
            int blue = (int) (29 + pos * 24);
            setDrawColor(new Color(red, green, blue));
            drawFilledRect(0, y, getWidth(), 3);
        }

        double glowX = mouseX;
        double glowY = mouseY;
        for (int r = 180; r > 0; r -= 18) {
            int alpha = (int) ((1.0 - r / 180.0) * 10);
            setDrawColor(new Color(106, 164, 205, alpha));
            drawFilledCircle(glowX, glowY, r);
        }
    }

    private void drawMapTexture() {
        setDrawColor(new Color(255, 255, 255, 13));
        for (int x = 38; x < getWidth(); x += 46) {
            drawLine(x, 0, x, getHeight(), 1.0);
        }
        for (int y = 34; y < getHeight(); y += 42) {
            drawLine(0, y, getWidth(), y, 1.0);
        }

        setDrawColor(new Color(11, 15, 21, 110));
        drawFilledRect(scaleX(56), scaleY(58), scaleX(88), scaleY(74));
        drawFilledRect(scaleX(532), scaleY(56), scaleX(142), scaleY(76));
        drawFilledRect(scaleX(67), scaleY(334), scaleX(138), scaleY(96));
        drawFilledRect(scaleX(526), scaleY(350), scaleX(150), scaleY(86));

        setDrawColor(new Color(86, 123, 142, 70));
        drawLine(scaleX(55), scaleY(188), scaleX(706), scaleY(318), 15.0);
        setDrawColor(new Color(32, 48, 62, 120));
        drawLine(scaleX(55), scaleY(188), scaleX(706), scaleY(318), 10.0);
        setDrawColor(new Color(126, 177, 199, 50));
        drawLine(scaleX(55), scaleY(188), scaleX(706), scaleY(318), 2.0);
    }

    private void drawRoutes(double energy) {
        for (Route route : routes) {
            for (int i = 0; i < route.stationIndexes.length - 1; i++) {
                Station a = stations.get(route.stationIndexes[i]);
                Station b = stations.get(route.stationIndexes[i + 1]);

                drawRouteSegment(a, b, new Color(2, 5, 8, 130), 12.0, scaleX(5), scaleY(6));
                drawRouteSegment(a, b, route.color, 6.5 + energy * 1.8, 0, 0);
                drawRouteSegment(a, b, new Color(255, 255, 255, 70), 1.2, 0, -scaleY(2));
            }
        }
    }

    private void drawRouteSegment(Station a, Station b, Color color, double width, double offsetX, double offsetY) {
        setDrawColor(color);
        double x1 = scaleX(a.x) + offsetX;
        double y1 = scaleY(a.y) + offsetY;
        double x2 = scaleX(b.x) + offsetX;
        double y2 = scaleY(b.y) + offsetY;
        double bend = Math.sin((a.x + b.y) * 0.03) * scaleY(26);
        double cx = (x1 + x2) / 2.0;
        double cy = (y1 + y2) / 2.0 + bend;
        drawCurve(x1, y1, cx, cy, x2, y2, width);
    }

    private void drawTrains(double energy) {
        for (int routeIndex = 0; routeIndex < routes.size(); routeIndex++) {
            Route route = routes.get(routeIndex);
            int trainCount = route.stationIndexes.length > 3 ? 3 : 2;

            for (int train = 0; train < trainCount; train++) {
                double progress = (time * (0.06 + routeIndex * 0.011 + energy * 0.04) + train / (double) trainCount) % 1.0;
                Position position = sampleRoute(route, progress);
                double pulse = 0.5 + Math.sin(time * 4.0 + routeIndex + train) * 0.5;

                setDrawColor(new Color(2, 5, 8, 145));
                drawFilledCircle(position.x + scaleX(4), position.y + scaleY(5), scaleX(8.5));
                setDrawColor(route.color.brighter());
                drawFilledCircle(position.x, position.y, scaleX(6.0 + pulse * 1.8 + energy * 2.0));
                setDrawColor(new Color(255, 255, 255, 165));
                drawFilledCircle(position.x - scaleX(2), position.y - scaleY(2), scaleX(2.0));
            }
        }
    }

    private void drawStations(Station selected, double energy) {
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            double x = scaleX(station.x);
            double y = scaleY(station.y);
            boolean isSelected = station == selected;
            double pulse = 0.5 + Math.sin(time * 2.2 + i * 0.7) * 0.5;
            double baseRadius = station.hub ? 8.0 : 5.8;

            if (isSelected) {
                for (int r = 42; r > 10; r -= 8) {
                    setDrawColor(new Color(255, 246, 191, clampInt(60 - r, 8, 50)));
                    drawCircle(x, y, scaleX(r + pulse * 5 + energy * 12));
                }
            }

            setDrawColor(new Color(3, 5, 8, 160));
            drawFilledCircle(x + scaleX(3), y + scaleY(4), scaleX(baseRadius + 2.4));
            setDrawColor(station.hub ? new Color(247, 238, 203) : new Color(210, 226, 233));
            drawFilledCircle(x, y, scaleX(baseRadius + (isSelected ? 2.2 : 0.0)));
            setDrawColor(isSelected ? new Color(255, 183, 86) : new Color(45, 61, 71));
            drawCircle(x, y, scaleX(baseRadius + 3.6 + energy * pulse * 3.0));
        }
    }

    private void drawInfoPanel(Station selected, double energy) {
        int panelX = scaleX(36);
        int panelY = scaleY(38);
        int panelW = scaleX(218);
        int panelH = scaleY(104);

        setDrawColor(new Color(7, 10, 15, 178));
        drawFilledRect(panelX, panelY, panelW, panelH);
        setDrawColor(new Color(220, 235, 238, 62));
        drawRect(panelX, panelY, panelW, panelH);

        setFont(18);
        setDrawColor(new Color(242, 248, 240, 220));
        drawText(selected.name, panelX + 14, panelY + 29);

        setFont(12);
        setDrawColor(new Color(179, 204, 211, 190));
        drawText(selected.hub ? "interchange station" : "local station", panelX + 14, panelY + 51);
        drawText("service load " + serviceLoad(selected, energy) + "%", panelX + 14, panelY + 72);

        int barX = panelX + 14;
        int barY = panelY + 84;
        int barW = panelW - 28;
        int fillW = (int) (barW * serviceLoad(selected, energy) / 100.0);
        setDrawColor(new Color(28, 38, 46));
        drawFilledRect(barX, barY, barW, 8);
        setDrawColor(new Color(82, 213, 177));
        drawFilledRect(barX, barY, fillW, 8);
    }

    private void drawHud() {
        String message = "Move mouse to inspect stations. Hold left button for rush hour.";
        setFont(12);
        int[] textSize = getTextSize(message);
        int x = (getWidth() - textSize[BasicDisplay.TEXT_SIZE_WIDTH]) / 2;
        int y = getHeight() - 20;

        setDrawColor(new Color(4, 7, 10, 165));
        drawFilledRect(x - 10, y - 18, textSize[BasicDisplay.TEXT_SIZE_WIDTH] + 20, 24);
        setDrawColor(new Color(229, 241, 246, 210));
        drawText(message, x, y);
    }

    private int serviceLoad(Station station, double energy) {
        double wave = 0.5 + Math.sin(time * 1.4 + station.x * 0.03 + station.y * 0.01) * 0.5;
        double hubBoost = station.hub ? 18.0 : 0.0;
        return clampInt((int) (30 + wave * 34 + hubBoost + energy * 16), 0, 100);
    }

    private Station findNearestStation(double x, double y) {
        Station nearest = stations.get(0);
        double nearestDistance = Double.MAX_VALUE;

        for (Station station : stations) {
            double dx = scaleX(station.x) - x;
            double dy = scaleY(station.y) - y;
            double distance = dx * dx + dy * dy;
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = station;
            }
        }

        return nearest;
    }

    private Position sampleRoute(Route route, double progress) {
        int segmentCount = route.stationIndexes.length - 1;
        double scaled = progress * segmentCount;
        int index = Math.min(segmentCount - 1, (int) scaled);
        double local = scaled - index;
        Station a = stations.get(route.stationIndexes[index]);
        Station b = stations.get(route.stationIndexes[index + 1]);
        return sampleSegment(a, b, local);
    }

    private Position sampleSegment(Station a, Station b, double t) {
        double x1 = scaleX(a.x);
        double y1 = scaleY(a.y);
        double x2 = scaleX(b.x);
        double y2 = scaleY(b.y);
        double bend = Math.sin((a.x + b.y) * 0.03) * scaleY(26);
        double cx = (x1 + x2) / 2.0;
        double cy = (y1 + y2) / 2.0 + bend;
        double oneMinusT = 1.0 - t;

        return new Position(
                oneMinusT * oneMinusT * x1 + 2.0 * oneMinusT * t * cx + t * t * x2,
                oneMinusT * oneMinusT * y1 + 2.0 * oneMinusT * t * cy + t * t * y2);
    }

    private void drawCurve(double x1, double y1, double cx, double cy, double x2, double y2, double width) {
        double previousX = x1;
        double previousY = y1;

        for (int i = 1; i <= 18; i++) {
            double t = i / 18.0;
            double oneMinusT = 1.0 - t;
            double x = oneMinusT * oneMinusT * x1 + 2.0 * oneMinusT * t * cx + t * t * x2;
            double y = oneMinusT * oneMinusT * y1 + 2.0 * oneMinusT * t * cy + t * t * y2;
            drawLine(previousX, previousY, x, y, width);
            previousX = x;
            previousY = y;
        }
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

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private record Station(String name, int x, int y, boolean hub) {
    }

    private record Route(String name, Color color, int... stationIndexes) {
    }

    private record Position(double x, double y) {
    }
}
