package com.physmo.minvio;

public class Rect {
    public int x, y, w, h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isPointInside(int xx, int yy) {
        if (xx < x || yy < y) return false;
        if (xx > x + w || yy > y + h) return false;
        return true;
    }

}
