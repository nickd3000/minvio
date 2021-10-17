package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;

/*
    Use the BasicUtils.matrix drawer helper method to quickly
    trial equations on the x/y axis.
 */
class MatrixDrawerExample extends MinvioApp {

    double time = 0;

    public static void main(String... args) {
        MinvioApp app = new MatrixDrawerExample();
        app.start(new BasicDisplayAwt(400, 400), "Matrix Drawer Example", 30);
    }


    @Override
    public void draw(BasicDisplay bd, double delta) {
        time += delta;

        BasicUtils.matrixDrawer(bd, 0, 0, 400, 400, 5, time, (x, y, t) -> {
            return ((1.0 + Math.sin(t * 2 + x * 15)) + (1.0 + Math.cos(t * 5 + x * 20))) * 0.25;
        });
    }
}
