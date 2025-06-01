package com.physmo.minvio.types;

import java.util.Objects;

public class Vec3 {

    public double x, y, z;

    /**
     * Represents a 3-dimensional vector.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     */
    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Represents a 3-dimensional vector.
     */
    public Vec3(Vec3 other) {
        if (other == null) {
            throw new IllegalArgumentException("Input vector cannot be null");
        }
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    /**
     * Sets the x, y, and z coordinates of the Vec3 object.
     *
     * @param x the x coordinate to set
     * @param y the y coordinate to set
     * @param z the z coordinate to set
     */
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("Vec3{x=%.2f, y=%.2f, z=%.2f}", x, y, z);
    }

    /**
     * Adds the coordinates of the given Vec3 object to this Vec3 object.
     *
     * @param other the Vec3 object to add
     * @return a new Vec3 object that is the result of adding the coordinates of the given Vec3 object to this Vec3 object
     */
    public Vec3 add(Vec3 other) {
        Vec3 newVec = new Vec3(this);
        newVec.x += other.x;
        newVec.y += other.y;
        newVec.z += other.z;
        return newVec;
    }

    // 'i' suffix means do the operation "in-place", i.e. update the object.

    /**
     * Adds the coordinates of the given Vec3 object to this Vec3 object.
     *
     * @param other the Vec3 object to add
     */
    public void addi(Vec3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }


    /**
     * Scales the vector by the given value.
     *
     * @param v the value by which to scale the vector
     * @return a new Vec3 object that is the result of scaling the vector by the given value
     */
    public Vec3 scale(double v) {
        return new Vec3(x * v, y * v, z * v);
    }

    /**
     * Scales the vector by the given value.
     *
     * @param v the value by which to scale the vector
     */
    public void scalei(double v) {
        x = x * v;
        y = y * v;
        z = z * v;
    }


    /**
     * Calculates the Euclidean distance between this Vec3 object and another Vec3 object.
     *
     * @param other the Vec3 object to calculate the distance to
     * @return the Euclidean distance between this Vec3 object and the given Vec3 object
     */
    public double distance(Vec3 other) {
        double dx = other.x - x;
        double dy = other.y - y;
        double dz = other.z - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Normalizes the vector to have a magnitude of 1.
     *
     * @return the magnitude of the original vector before normalization
     */
    public double normalise() {
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        x = x / magnitude;
        y = y / magnitude;
        z = z / magnitude;
        return magnitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vec3 vec3 = (Vec3) obj;
        double epsilon = 1e-10; // Precision threshold
        return Math.abs(vec3.x - x) < epsilon &&
                Math.abs(vec3.y - y) < epsilon &&
                Math.abs(vec3.z - z) < epsilon;
    }
}