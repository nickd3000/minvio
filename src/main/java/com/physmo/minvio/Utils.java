package com.physmo.minvio;

import com.physmo.minvio.types.Point;

import java.awt.Color;

public class Utils {

    /**
     * Returns a new distinct colour for each supplied index
     * Colours will be the same for a given index each time it is called.
     *
     * @param index      integer representing the distinct colour
     * @param saturation 0..1 double value
     * @return A distinct color.
     */
    // TODO: Cache some of these
//    public static Color getDistinctColor(int index, double saturation) {
//        float magicNumber = 0.6180339887f;
//        return new Color(Color.HSBtoRGB(((float) index) * magicNumber, (float) saturation, 1.0f));
//    }

    /**
     * Return a blended color between c1 and c2 at position pos.
     *
     * @param c1  Color 1
     * @param c2  Color 2
     * @param pos Position
     * @return Blended color
     */
    public static Color lerp(Color c1, Color c2, double pos) {
        pos = clamp(0.0, 1.0, pos);
        int r = lerp(c1.getRed(), c2.getRed(), pos);
        int g = lerp(c1.getGreen(), c2.getGreen(), pos);
        int b = lerp(c1.getBlue(), c2.getBlue(), pos);
        return new Color(r, g, b);
    }

    /**
     * Return blended value, mix of v1 and v2, proportion specified by pos.
     *
     * @param v1  First value
     * @param v2  Second Value
     * @param pos control
     * @return the interpolated value
     */
    public static int lerp(int v1, int v2, double pos) {
        int span = v2 - v1;
        return (int) (v1 + (int) (double) span * pos);
    }

    /**
     * Return blended value, mix of v1 and v2, proportion specified by pos.
     *
     * @param p1  First value
     * @param p2  Second Value
     * @param pos control
     * @return the interpolated value
     */
    public static Point lerp(Point p1, Point p2, double pos) {
        return new Point(lerp(p1.x, p2.x, pos), lerp(p1.y, p2.y, pos));
    }

    /**
     * Return blended value, mix of v1 and v2, proportion specified by pos.
     *
     * @param v1  First value
     * @param v2  Second Value
     * @param pos control
     * @return the interpolated value
     */
    public static double lerp(double v1, double v2, double pos) {
        double span = v2 - v1;
        return (v1 + span * pos);
    }

    /**
     * Return blended value, mix of v1 and v2, proportion specified by pos.
     *
     * @param min   First value
     * @param max   Second Value
     * @param value control
     * @return the clamped value
     */
    public static double clamp(double min, double max, double value) {
        return value < min ? min : Math.min(value, max);
    }

    /**
     * Calculates the inverted distance between the given distance and the maximum distance.
     * If the distance is greater than the maximum, it is set to the maximum before the calculation is performed.
     * The resulting value is between 0 and 1, where 1 is the closest distance and 0 is the farthest.
     *
     * @param distance the distance value
     * @param max      the maximum distance value
     * @return the inverted distance value
     */
    public static double invertDistance(double distance, double max) {
        if (distance > max) {
            distance = max;
        }
        return (max - distance) / max;
    }
}
