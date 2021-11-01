package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Point;
import com.physmo.minvio.PointDynamic;
import com.physmo.minvio.Position;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.BucketList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BucketListExample extends MinvioApp {

    public static final double minDist = 3.0;
    BucketList bucketList;
    List<Position> points;
    int numPoints = 3000;
    double forceScale = 3.5;
    int threshold = 1;
    double friction = 1;
    double distancePad = 20;

    public static void main(String... args) {
        MinvioApp app = new BucketListExample();
        app.start(new BasicDisplayAwt(800, 800), "", 20);
    }


    @Override
    public void init(BasicDisplay bd) {
        bucketList = new BucketList(64, bd.getWidth(), bd.getHeight());
        points = new ArrayList<>();
        double startingSpeed = 10;
        for (int i = 0; i < numPoints; i++) {
            PointDynamic p = new PointDynamic();
            Point pos = BasicUtils.createRandomPointInCircle(
                    bd.getWidth() / 2, bd.getHeight() / 2, bd.getWidth() / 6);
            p.x = pos.x;
            p.y = pos.y;
            p.dx = (Math.random() - 0.5) * startingSpeed;
            p.dy = (Math.random() - 0.5) * startingSpeed;
            points.add(0, p);
        }

        List<List<Integer>> bucketsAroundPoint = bucketList.getBucketsAroundPoint(200, 200, 1);
        List<Integer[]> outsideBucketSummary = bucketList.getOutsideBucketSummary(200, 200, 1);
        System.out.println("total:" + bucketList.getBucketCount()
                + " inside:" + bucketsAroundPoint.size()
                + " outside:" + outsideBucketSummary.size()
                + " sum:" + (bucketsAroundPoint.size() + outsideBucketSummary.size()));
        System.out.println();
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.BLACK);
        bd.setDrawColor(Color.lightGray);
        tick(bd);

        // Draw summary grid
        List<Integer[]> bucketSummary = bucketList.getBucketSummary();
        int cellSize = bucketList.getCellSize();
        bd.setDrawColor(new Color(64, 31, 31));
        for (Integer[] integers : bucketSummary) {
            if (integers[2] == 0) continue;
            bd.drawRect(integers[0] - (cellSize / 2), integers[1] - (cellSize / 2), cellSize, cellSize);
        }

        // Draw particles
        bd.setDrawColor(new Color(230, 215, 186));
        for (Position point : points) {
            bd.drawPoint((int) point.x, (int) point.y);
        }
    }

    public void tick(BasicDisplay bd) {
        bucketList.clearAll();
        bucketList.addPointList(points);

        for (Position point : points) {
            PointDynamic p = (PointDynamic) point;
            List<List<Integer>> bucketsAroundPoint = bucketList.getBucketsAroundPoint(p.x, p.y, threshold);
            for (List<Integer> integers : bucketsAroundPoint) {
                if (integers == null) continue;
                for (Integer integer : integers) {
                    PointDynamic p2 = (PointDynamic) points.get(integer);
                    if (p == p2) continue;

                    double distance = p.distance(p2) + distancePad;
                    if (distance < minDist) distance = minDist;
                    double dx = (p2.x - p.x) / distance;
                    double dy = (p2.y - p.y) / distance;
                    double force = (2.0 / (distance * distance)) * forceScale;
                    p.dx += dx * force;
                    p.dy += dy * force;
                }
            }

            List<Integer[]> outsideBucketSummary = bucketList.getOutsideBucketSummary(p.x, p.y, threshold);
            for (Integer[] integers : outsideBucketSummary) {
                Point p2 = new Point(integers[0], integers[1]);
                double distance = p.distance(p2) + distancePad;
                //if (distance < 0.01) distance = 0.01;
                double dx = (p2.x - p.x) / distance;
                double dy = (p2.y - p.y) / distance;
                double force = ((1 + integers[2]) / (distance * distance)) * forceScale;
                p.dx += dx * force;
                p.dy += dy * force;
            }

        }


        double w = bd.getWidth();
        double h = bd.getHeight();


        // Move, constrain and add friction to points.
        for (Position p : points) {
            p.x += ((PointDynamic) p).dx;
            p.y += ((PointDynamic) p).dy;

            if (p.x < 0) {
                p.x = 0;
                ((PointDynamic) p).dx *= -1;
            }
            if (p.x > w) {
                p.x = w;
                ((PointDynamic) p).dx *= -1;
            }
            if (p.y < 0) {
                p.y = 0;
                ((PointDynamic) p).dy *= -1;
            }
            if (p.y > h) {
                p.y = h;
                ((PointDynamic) p).dy *= -1;
            }
            ((PointDynamic) p).dx *= friction;
            ((PointDynamic) p).dy *= friction;
        }


    }


}
