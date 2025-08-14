package com.physmo.reference.experiments;

import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

// NOTE: This class was created to visually test rectangle collision
// overlap reporting for the garnet toolkit - October 18, 2023.
class OverlapTest extends MinvioApp {

    RectD rect1 = new RectD(100, 100, 40, 40);
    RectD rect2 = new RectD(100, 100, 50, 50);
    double gdx = 0;
    double gdy = 0;

    public static void main(String... args) {
        MinvioApp app = new OverlapTest();
        app.start(new BasicDisplayAwt(400, 400), "Overlap Test", 30);
    }

    @Override
    public void draw(double delta) {
        cls(Color.darkGray);

        rect1.x = getMouseX();
        rect1.y = getMouseY();
        setDrawColor(Color.YELLOW);
        drawRect((int) rect1.x, (int) rect1.y, (int) rect1.w, (int) rect1.h);
        drawRect((int) rect2.x, (int) rect2.y, (int) rect2.w, (int) rect2.h);

        double[] overlap = new double[4];
        overlap(rect1, rect2, overlap);
        drawText("" + overlap[0], 200, 200);
        drawText("" + overlap[1], 200 + 50, 200 + 50);
        drawText("" + overlap[2], 200, 200 + 100);
        drawText("" + overlap[3], 200 - 50, 200 + 50);
        drawText("gdx:" + gdx + " gdy:" + gdy, 50, 320 + 50);
    }

    public void overlap(RectD one, RectD other, double[] overlap) {
        double hh = one.h / 2 + other.h / 2;
        double ww = one.w / 2 + other.w / 2;

        double dy = (other.y + (other.h / 2)) - (one.y + (one.h / 2));
        double dx = (other.x + (other.w / 2)) - (one.x + (one.w / 2));

        if (Math.abs(dx) >= ww) return;
        if (Math.abs(dy) >= hh) return;

        gdx = dx;
        gdy = dy;

        double up = 0, down = 0, left = 0, right = 0;

        if (one.y + (one.h / 2) > other.y + (other.h / 2)) {
            up = hh + dy;
        } else {
            down = hh - dy;
        }

        if (one.x + (one.w / 2) < other.x + (other.w / 2)) {
            right = ww - dx;
        } else {
            left = ww + dx;
        }

        overlap[0] = up;
        overlap[1] = right;
        overlap[2] = down;
        overlap[3] = left;

    }
}

class RectD {
    public double x, y, w, h;

    public RectD(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
