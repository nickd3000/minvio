package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.AnchorManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LSystemExample extends MinvioApp {

    AnchorManager anchorManager;
    Color background = new Color(40, 44, 52);

    // Configuration
    int initialAnchorCount = 5;
    int iterationDepth = 5;

    // L-System variables
    int maxIterations = 8;
    double minSegmentLength = 4.0;
    List<Point> fractalPoints = new ArrayList<>();

    public static void main(String[] args) {
        MinvioApp app = new LSystemExample();
        app.start(800, 600, "L-System with Prototype Shape", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        anchorManager = new AnchorManager(5);

        // Create initial anchor points based on the configuration
        createInitialAnchors(bd.getWidth(), bd.getHeight());

        // Custom anchor drawing
        anchorManager.setAnchorDrawDelegate((dc, point, radius, mouseOver, grabbed) -> {
            if (grabbed) {
                dc.setDrawColor(new Color(100, 255, 100));
                dc.drawFilledCircle(point, radius + 2);
                dc.setDrawColor(new Color(255, 255, 255));
                dc.drawCircle(point, radius + 2);
            } else if (mouseOver) {
                dc.setDrawColor(new Color(255, 255, 100));
                dc.drawFilledCircle(point, radius);
                dc.setDrawColor(new Color(255, 255, 255));
                dc.drawCircle(point, radius);
            } else {
                dc.setDrawColor(new Color(200, 200, 200));
                dc.drawFilledCircle(point, radius);
                dc.setDrawColor(new Color(100, 100, 100));
                dc.drawCircle(point, radius);
            }
        });

        generateFractal();
    }

    private void createInitialAnchors(int width, int height) {
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double segmentLength = 200.0 / (initialAnchorCount - 1);

        // Create a straight line with the last segment slightly higher
        for (int i = 0; i < initialAnchorCount; i++) {
            double x = centerX - 100 + i * segmentLength;
            double y = centerY;

            // Make the last point higher
            if (i == initialAnchorCount - 1) {
                y -= 30;
            }

            anchorManager.add(x, y);
        }
    }

    private void generateFractal() {
        fractalPoints.clear();

        List<Point> anchors = anchorManager.getAnchors();
        if (anchors.size() < 2) return;

        // Start with the prototype shape from anchors
        List<Point> currentShape = new ArrayList<>(anchors);

        // Use the minimum of iterationDepth and maxIterations for actual iteration count
        int actualIterations = Math.min(iterationDepth, maxIterations);

        // Apply L-system iterations with early termination
        for (int iter = 0; iter < actualIterations; iter++) {
            // Check if any segment is large enough to subdivide
            boolean hasLargeSegments = false;
            for (int i = 0; i < currentShape.size() - 1; i++) {
                Point start = currentShape.get(i);
                Point end = currentShape.get(i + 1);
                double segmentLength = Point.distance(start, end);
                if (segmentLength >= minSegmentLength) {
                    hasLargeSegments = true;
                    break;
                }
            }

            // If no segments are large enough, stop iterating
            if (!hasLargeSegments) {
                break;
            }

            currentShape = applyLSystemRule(currentShape, anchors);
        }

        fractalPoints.addAll(currentShape);
    }

    private List<Point> applyLSystemRule(List<Point> shape, List<Point> prototype) {
        List<Point> newShape = new ArrayList<>();

        // For each line segment in the current shape
        for (int i = 0; i < shape.size() - 1; i++) {
            Point start = shape.get(i);
            Point end = shape.get(i + 1);

            double segmentLength = Point.distance(start, end);

            // Only subdivide segments that are large enough
            if (segmentLength >= minSegmentLength) {
                // Replace this line segment with a scaled/rotated version of the prototype
                List<Point> replacementSegment = transformPrototype(prototype, start, end);

                // Add all points except the last one (to avoid duplication)
                for (int j = 0; j < replacementSegment.size() - 1; j++) {
                    newShape.add(replacementSegment.get(j));
                }
            } else {
                // Keep the original segment if it's too small
                newShape.add(start);
            }
        }

        // Add the final point
        if (!shape.isEmpty()) {
            Point lastStart = shape.get(shape.size() - 1);
            Point lastEnd = shape.get(shape.size() - 1);
            if (shape.size() > 1) {
                lastStart = shape.get(shape.size() - 2);
                lastEnd = shape.get(shape.size() - 1);
                double lastSegmentLength = Point.distance(lastStart, lastEnd);

                if (lastSegmentLength >= minSegmentLength) {
                    List<Point> lastSegment = transformPrototype(prototype, lastStart, lastEnd);
                    newShape.add(lastSegment.get(lastSegment.size() - 1));
                } else {
                    newShape.add(lastEnd);
                }
            }
        }

        return newShape;
    }

    private List<Point> transformPrototype(List<Point> prototype, Point segmentStart, Point segmentEnd) {
        if (prototype.size() < 2) return new ArrayList<>();

        List<Point> transformed = new ArrayList<>();

        // Get prototype start and end points
        Point protoStart = prototype.get(0);
        Point protoEnd = prototype.get(prototype.size() - 1);

        // Calculate transformation parameters
        double protoLength = Point.distance(protoStart, protoEnd);
        double segmentLength = Point.distance(segmentStart, segmentEnd);
        double scale = segmentLength / protoLength;

        // Calculate rotation angle
        double protoAngle = Math.atan2(protoEnd.y - protoStart.y, protoEnd.x - protoStart.x);
        double segmentAngle = Math.atan2(segmentEnd.y - segmentStart.y, segmentEnd.x - segmentStart.x);
        double rotation = segmentAngle - protoAngle;

        // Transform each point in the prototype
        for (Point p : prototype) {
            // Translate to origin
            double x = p.x - protoStart.x;
            double y = p.y - protoStart.y;

            // Scale
            x *= scale;
            y *= scale;

            // Rotate
            double rotatedX = x * Math.cos(rotation) - y * Math.sin(rotation);
            double rotatedY = x * Math.sin(rotation) + y * Math.cos(rotation);

            // Translate to segment start
            rotatedX += segmentStart.x;
            rotatedY += segmentStart.y;

            transformed.add(new Point(rotatedX, rotatedY));
        }

        return transformed;
    }

    @Override
    public void draw(double delta) {
        cls(background);

        // Update anchors
        anchorManager.update(getBasicDisplay());
        
        // Regenerate fractal when anchors move
        generateFractal();

        // Draw the prototype shape (anchors connected)
        setDrawColor(new Color(155, 50, 50));
        List<Point> anchors = anchorManager.getAnchors();
        for (int i = 0; i < anchors.size() - 1; i++) {
            drawLine(anchors.get(i).x, anchors.get(i).y,
                    anchors.get(i + 1).x, anchors.get(i + 1).y, 2.0);
        }

        // Draw the fractal
        setDrawColor(new Color(100, 200, 255));
        for (int i = 0; i < fractalPoints.size() - 1; i++) {
            Point p1 = fractalPoints.get(i);
            Point p2 = fractalPoints.get(i + 1);
            drawLine(p1.x, p1.y, p2.x, p2.y, 1.0);
        }

        // Draw anchors
        anchorManager.draw(getDrawingContext());

        // Draw instructions
        setDrawColor(new Color(200, 200, 200));
        setFont(14);
        drawText("L-System Fractal Generator", 10, 25);
        setFont(12);
        drawText("• Red line: Prototype shape defined by anchors", 10, 50);
        drawText("• Blue lines: Fractal result", 10, 70);
        drawText("• Drag anchors to change the prototype shape", 10, 90);

    }
}
