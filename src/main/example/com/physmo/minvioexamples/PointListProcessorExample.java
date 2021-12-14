package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Point;
import com.physmo.minvio.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

class PointListProcessorExample extends MinvioApp {

    List<Point> points;

    public static void main(String... args) {
        MinvioApp app = new PointListProcessorExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "PointListProcessorExample", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        points = new ArrayList<>();
        points.add(new Point(100, 50));
        points.add(new Point(100, 100));
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {

        BasicUtils.pointListProcessor(bd, points, (bd1, p) -> {
            bd.drawCircle(p.x, p.y, 5);
        });

    }
}
