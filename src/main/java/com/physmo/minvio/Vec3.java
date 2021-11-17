package com.physmo.minvio;

public class Vec3 {

    public double x, y, z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(Vec3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("Vec3{x=%.2f, y=%.2f, z=%.2f}", x, y, z);
    }

    public Vec3 add(Vec3 other) {
        Vec3 newVec = new Vec3(this);
        newVec.x += other.x;
        newVec.y += other.y;
        newVec.z += other.z;
        return newVec;
    }

    // 'i' suffix means do the operation "in-place", i.e. update the object.
    public void addi(Vec3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public Vec3 scale(double v) {
        Vec3 newVec = new Vec3(this);
        newVec.x *= v;
        newVec.y *= v;
        newVec.z *= v;
        return newVec;
    }
}
