package com.physmo.reference.gui;

/**
 * The IQPalette class represents a mathematical model to generate RGB color values
 * based on input parameters. It is specifically designed for interpolating or
 * transitioning color components using a series of control variables.
 * <p>
 * Features:
 * - Supports up to three separate color components (red, green, and blue).
 * - Utilizes a set of control parameters to determine the generated colors.
 * - Allows dynamic updating of control parameters.
 * <p>
 * The generation of color components is based on a mathematical cosine function
 * applied with parameters that control amplitude, frequency, and phase shifts.
 * <p>
 * Based on the method described by Inigo Quilez https://www.shadertoy.com/user/iq
 */
public class IQPalette {
    private static final int CONTROL_LENGTH = 20;
    private static final int COMPONENTS = 3;
    private static final int COLOR_MAX = 255;
    double[] controls = new double[CONTROL_LENGTH];

    public void setControls(double[] newValues) {
        if (newValues.length > CONTROL_LENGTH) {
            throw new IllegalArgumentException("New values exceed control length.");
        }
        System.arraycopy(newValues, 0, controls, 0, newValues.length);
    }

    public int getRgb(double x) {
        int rgb = 0;
        for (int component = 0; component < COMPONENTS; component++) {
            int index = component * 4;
            double value = getColorComponent(x, controls[index], controls[index + 1], controls[index + 2], controls[index + 3]);

            // Clamping the value to [0, 255]
            int c = (int) (value * COLOR_MAX);
            c = Math.max(0, Math.min(c, COLOR_MAX));

            rgb |= c << ((2 - component) * 8);
        }
        return rgb;
    }

    private double getColorComponent(double x, double A, double B, double C, double D) {
        return A + B * (Math.cos((2.0 * Math.PI) * (C * x + D) * 2) + 1.0) * 0.5;
    }

}
