package com.physmo.minvio.utils;

import com.physmo.minvio.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manage a positional list of colours and allows fast access to
 * smoothly interpolated colours between them.
 * Black is automatically inserted at the start and end of the list.
 */
public class Gradient {
    final Map<Double, Color> colorList = new HashMap<>();
    final List<Color> preComputedList = new ArrayList<>();

    final int preComputedListSize = 1000;

    public Gradient() {
        colorList.put(0.0, Color.BLACK);
        colorList.put(1.0, Color.BLACK);
    }

    public Gradient(Color startColor, Color endColor) {
        colorList.put(0.0, startColor);
        colorList.put(1.0, endColor);
        recalculateList();
    }

    /**
     * If there is already a colour at the given position it is replace.
     * On inserting a colour the internal lookup table is refreshed.
     *
     * @param position 0..1 Double - the positionof the colour in the list.
     * @param color    the colour to insert.
     */
    public void addColor(double position, Color color) {
        colorList.remove(position);
        colorList.put(position, color);
        recalculateList();
    }

    private void recalculateList() {
        preComputedList.clear();
        for (int i = 0; i < preComputedListSize; i++) {
            double pos = ((double) i) / (double) preComputedListSize;
            preComputedList.add(calculateColor(pos));
        }
    }

    private Color calculateColor(double pos) {
        pos = Utils.clamp(0.0, 1.0, pos);

        List<Double> sortedKeys = colorList.keySet().stream().sorted().collect(Collectors.toList());

        double firstKey = -1;
        double secondKey = -1;

        for (int i = 0; i < sortedKeys.size() - 1; i++) {
            if (pos >= sortedKeys.get(i) && pos < sortedKeys.get(i + 1)) {
                firstKey = sortedKeys.get(i);
                secondKey = sortedKeys.get(i + 1);
            }
        }

        if (secondKey == -1) {
            return colorList.get(firstKey);
        }

        double lerpPos = pos - firstKey;
        double lerpSpan = secondKey - firstKey;
        lerpPos /= lerpSpan;

        Color c1 = colorList.get(firstKey);
        Color c2 = colorList.get(secondKey);

        return Utils.lerp(c1, c2, lerpPos);
    }

    /**
     * Returns an interpolated color given a value in the range 0..1.0
     *
     * @param pos Position in the gradient to extract a pre-computed color value.
     * @return Interpolated colour.
     */
    public Color getColor(double pos) {
        int index = (int) (pos * (double) preComputedListSize);
        if (index >= preComputedListSize) index = preComputedListSize - 1;
        return preComputedList.get(index);
    }

}
