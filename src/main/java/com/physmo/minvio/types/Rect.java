package com.physmo.minvio.types;

import java.util.Objects;

public class Rect {
    public int x, y, w, h;

    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rect(Rect rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.w = rect.w;
        this.h = rect.h;
    }

    public boolean isPointInside(int xx, int yy) {
        if (xx < x || yy < y) return false;
        return xx <= x + w && yy <= y + h;
    }

    public boolean intersects(Rect other) {
        return !(other.x > x + w ||
                other.x + other.w < x ||
                other.y > y + h ||
                other.y + other.h < y);
    }

    public Rect copy() {
        return new Rect(x, y, w, h);
    }

    public int getArea() {
        return w * h;
    }

    public boolean contains(Rect other) {
        return other.x >= x &&
                other.y >= y &&
                other.x + other.w <= x + w &&
                other.y + other.h <= y + h;
    }

    public int getCenterX() {
        return x + w / 2;
    }

    public int getCenterY() {
        return y + h / 2;
    }

    public void set(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void set(Rect other) {
        this.x = other.x;
        this.y = other.y;
        this.w = other.w;
        this.h = other.h;
    }

    @Override
    public String toString() {
        return String.format("Rect{x=%d, y=%d, w=%d, h=%d}", x, y, w, h);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rect rect = (Rect) obj;
        return x == rect.x && y == rect.y && w == rect.w && h == rect.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }
}