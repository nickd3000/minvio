package com.physmo.minvio.types;

import java.util.Objects;

/**
 * Mutable three-dimensional vector with exact value equality.
 *
 * <p>Use {@link #approximatelyEquals(Vec3, double)} for tolerance-based
 * comparisons. Do not mutate an instance while it is used as a hash key.</p>
 */
public class Vec3 {

    /**
     * Mutable x, y, and z components.
     */
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
     * Creates a coordinate copy of another vector.
     *
     * @param other vector to copy
     * @throws IllegalArgumentException if {@code other} is null
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
        if (magnitude == 0.0) {
            return 0.0;
        }
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
        return Double.compare(x, vec3.x) == 0 &&
                Double.compare(y, vec3.y) == 0 &&
                Double.compare(z, vec3.z) == 0;
    }

    /**
     * Checks whether each component of another vector is within the supplied
     * absolute tolerance of this vector.
     *
     * @param other   the vector to compare
     * @param epsilon the maximum absolute difference for each component
     * @return true when all component differences are within the tolerance
     */
    public boolean approximatelyEquals(Vec3 other, double epsilon) {
        Objects.requireNonNull(other, "Other vector cannot be null");
        if (epsilon < 0.0 || Double.isNaN(epsilon)) {
            throw new IllegalArgumentException("Epsilon must be non-negative");
        }
        return Math.abs(other.x - x) <= epsilon &&
                Math.abs(other.y - y) <= epsilon &&
                Math.abs(other.z - z) <= epsilon;
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param other the other vector
     * @return the dot product
     */
    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param other the other vector
     * @return a new Vec3 object that is the cross product of this vector and the other vector
     */
    public Vec3 cross(Vec3 other) {
        return new Vec3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

    /**
     * Linearly interpolates between this vector and another vector.
     *
     * @param other the other vector
     * @param t     the interpolation factor (typically between 0 and 1)
     * @return a new Vec3 object that is the result of the linear interpolation
     */
    public Vec3 lerp(Vec3 other, double t) {
        return new Vec3(
                x + (other.x - x) * t,
                y + (other.y - y) * t,
                z + (other.z - z) * t
        );
    }

    /**
     * Calculates the angle between this vector and another vector in radians.
     *
     * @param other the other vector
     * @return the angle between the two vectors in radians
     */
    public double angleBetween(Vec3 other) {
        double dotProduct = dot(other);
        double magnitude1 = Math.sqrt(x * x + y * y + z * z);
        double magnitude2 = Math.sqrt(other.x * other.x + other.y * other.y + other.z * other.z);
        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            throw new IllegalArgumentException("Cannot calculate an angle involving a zero-length vector");
        }
        double cosine = dotProduct / (magnitude1 * magnitude2);
        return Math.acos(Math.max(-1.0, Math.min(1.0, cosine)));
    }
}
