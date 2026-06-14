package com.physmo.minvio.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Shared mutable color presets and deterministic distinct-color generation.
 *
 * <p>The preset fields are not final for historical compatibility; replacing a
 * field changes the value observed by later callers.</p>
 */
public class Palette {
    /**
     * Rainbow color presets.
     */
    public static Color RED = new Color(186, 36, 36),
            ORANGE = new Color(202, 96, 21),
            YELLOW = new Color(187, 174, 16),
            GREEN = new Color(88, 196, 33),
            BLUE = new Color(40, 135, 210),
            INDIGO = new Color(134, 33, 194),
            VIOLET = new Color(203, 17, 178);

    /**
     * Natural color presets.
     */
    public static Color BROWN = new Color(112, 57, 24),
            MINT = new Color(30, 185, 131);

    /**
     * Grayscale and neutral color presets.
     */
    public static Color BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            GRAY_900 = new Color(25, 25, 25),
            GRAY_700 = new Color(55, 59, 65),
            GRAY_500 = new Color(108, 117, 125),
            GRAY_300 = new Color(200, 200, 200),
            GRAY_100 = new Color(245, 245, 247);

    /**
     * Warm color presets.
     */
    public static Color CRIMSON = new Color(220, 20, 60),
            BRICK = new Color(178, 34, 34),
            SALMON = new Color(250, 128, 114),
            CORAL = new Color(255, 127, 80),
            AMBER = new Color(255, 191, 0);

    /**
     * Cool color presets.
     */
    public static Color TEAL = new Color(0, 150, 136),
            CYAN = new Color(0, 188, 212),
            COBALT = new Color(0, 71, 171),
            NAVY = new Color(10, 28, 58),
            AQUA = new Color(127, 219, 255);

    /**
     * Pastel color presets.
     */
    public static Color PASTEL_PINK = new Color(255, 183, 197),
            PASTEL_PEACH = new Color(255, 205, 178),
            PASTEL_YELLOW = new Color(255, 249, 177),
            PASTEL_GREEN = new Color(186, 255, 201),
            PASTEL_BLUE = new Color(186, 225, 255),
            PASTEL_LAVENDER = new Color(221, 214, 255);

    /**
     * Neon and accent color presets.
     */
    public static Color NEON_PINK = new Color(255, 20, 147),
            NEON_GREEN = new Color(57, 255, 20),
            NEON_BLUE = new Color(0, 255, 255),
            NEON_YELLOW = new Color(255, 255, 0),
            MAGENTA = new Color(255, 0, 255);

    /**
     * Earth-tone color presets.
     */
    public static Color SAND = new Color(237, 201, 175),
            TAN = new Color(210, 180, 140),
            OLIVE = new Color(128, 128, 0),
            FOREST = new Color(34, 139, 34),
            SLATE = new Color(112, 128, 144),
            CLAY = new Color(168, 112, 74);

    /**
     * UI-oriented semantic color presets.
     */
    public static Color PRIMARY = new Color(51, 102, 255),
            SUCCESS = new Color(40, 167, 69),
            WARNING = new Color(255, 193, 7),
            DANGER = new Color(220, 53, 69),
            INFO = new Color(23, 162, 184);

    /**
     * Playful color presets.
     */
    public static Color BUBBLEGUM = new Color(255, 105, 180),
            COTTON_CANDY = new Color(255, 182, 222),
            WATERMELON = new Color(242, 71, 84),
            SLIME = new Color(132, 255, 90),
            LAVA = new Color(255, 77, 0),
            UNICORN = new Color(191, 128, 255),
            FLAMINGO = new Color(252, 142, 172),
            SKY_CANDY = new Color(116, 205, 255),
            GRAPE_SODA = new Color(123, 75, 199),
            BLUEBERRY = new Color(54, 93, 201),
            PINEAPPLE = new Color(255, 223, 72),
            MATCHA = new Color(135, 169, 107),
            MACNCHEESE = new Color(255, 173, 67),
            SHERBET = new Color(255, 201, 120),
            MINT_CREAM = new Color(234, 255, 244),
            PUMPKIN_SPICE = new Color(198, 98, 38),
            TROPICAL_SEA = new Color(0, 216, 204),
            GALAXY = new Color(48, 29, 92),
            SUNSET = new Color(255, 94, 98),
            SUNRISE = new Color(255, 166, 77),
            ICE_POP = new Color(0, 148, 255),
            PEACH_FIZZ = new Color(255, 170, 146),
            LEMON_SORBET = new Color(255, 246, 150),
            BERRY = new Color(171, 37, 107);

    private record DistinctColorKey(int index, int saturationBits) {
    }

    static Map<DistinctColorKey, Color> distinctColorCache = new HashMap<>();

    /**
     * Returns a new distinct colour for each supplied index
     * Colours will be the same for a given index each time it is called.
     *
     * @param index      integer representing the distinct colour
     * @param saturation 0..1 double value
     * @return A distinct color.
     */

    public static Color getDistinctColor(int index, double saturation) {
        float magicNumber = 0.6180339887f;
        float clampedSaturation = (float) Math.max(0.0, Math.min(1.0, saturation));
        DistinctColorKey key = new DistinctColorKey(index, Float.floatToIntBits(clampedSaturation));
        return distinctColorCache.computeIfAbsent(key,
                k -> new Color(Color.HSBtoRGB(((float) index) * magicNumber, clampedSaturation, 1.0f)));

    }

}
