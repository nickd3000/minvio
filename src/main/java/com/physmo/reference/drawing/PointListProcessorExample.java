package com.physmo.reference.drawing;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

class PointListProcessorExample extends MinvioApp {

    List<Point> points;

    public static void main(String... args) {
        MinvioApp app = new PointListProcessorExample();
        app.start(200, 200, "Point List Processor Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        points = new ArrayList<>();
        points.add(new Point(100, 50));
        points.add(new Point(100, 100));
    }

    @Override
    public void draw(double delta) {

        BasicUtils.pointListProcessor(this, points, (bd1, p) -> drawCircle(p.x, p.y, 5));

    }
}
