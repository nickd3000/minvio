package com.physmo.minvio.utils;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shared color presets and deterministic distinct-color generation.
 */
public class Palette {
    /**
     * Rainbow color presets.
     */
    public static final Color RED = new Color(186, 36, 36),
            ORANGE = new Color(202, 96, 21),
            YELLOW = new Color(187, 174, 16),
            GREEN = new Color(88, 196, 33),
            BLUE = new Color(40, 135, 210),
            INDIGO = new Color(134, 33, 194),
            VIOLET = new Color(203, 17, 178);

    /**
     * Natural color presets.
     */
    public static final Color BROWN = new Color(112, 57, 24),
            MINT = new Color(30, 185, 131);

    /**
     * Grayscale and neutral color presets.
     */
    public static final Color BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            GRAY_900 = new Color(25, 25, 25),
            GRAY_700 = new Color(55, 59, 65),
            GRAY_500 = new Color(108, 117, 125),
            GRAY_300 = new Color(200, 200, 200),
            GRAY_100 = new Color(245, 245, 247);

    /**
     * Warm color presets.
     */
    public static final Color CRIMSON = new Color(220, 20, 60),
            BRICK = new Color(178, 34, 34),
            SALMON = new Color(250, 128, 114),
            CORAL = new Color(255, 127, 80),
            AMBER = new Color(255, 191, 0);

    /**
     * Cool color presets.
     */
    public static final Color TEAL = new Color(0, 150, 136),
            CYAN = new Color(0, 188, 212),
            COBALT = new Color(0, 71, 171),
            NAVY = new Color(10, 28, 58),
            AQUA = new Color(127, 219, 255);

    /**
     * Pastel color presets.
     */
    public static final Color PASTEL_PINK = new Color(255, 183, 197),
            PASTEL_PEACH = new Color(255, 205, 178),
            PASTEL_YELLOW = new Color(255, 249, 177),
            PASTEL_GREEN = new Color(186, 255, 201),
            PASTEL_BLUE = new Color(186, 225, 255),
            PASTEL_LAVENDER = new Color(221, 214, 255);

    /**
     * Neon and accent color presets.
     */
    public static final Color NEON_PINK = new Color(255, 20, 147),
            NEON_GREEN = new Color(57, 255, 20),
            NEON_BLUE = new Color(0, 255, 255),
            NEON_YELLOW = new Color(255, 255, 0),
            MAGENTA = new Color(255, 0, 255);

    /**
     * Earth-tone color presets.
     */
    public static final Color SAND = new Color(237, 201, 175),
            TAN = new Color(210, 180, 140),
            OLIVE = new Color(128, 128, 0),
            FOREST = new Color(34, 139, 34),
            SLATE = new Color(112, 128, 144),
            CLAY = new Color(168, 112, 74);

    /**
     * UI-oriented semantic color presets.
     */
    public static final Color PRIMARY = new Color(51, 102, 255),
            SUCCESS = new Color(40, 167, 69),
            WARNING = new Color(255, 193, 7),
            DANGER = new Color(220, 53, 69),
            INFO = new Color(23, 162, 184);

    /**
     * Playful color presets.
     */
    public static final Color BUBBLEGUM = new Color(255, 105, 180),
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

    /**
     * Commodore 64-inspired 16-color palette.
     */
    public static final Color C64_BLACK = new Color(0, 0, 0),
            C64_WHITE = new Color(255, 255, 255),
            C64_RED = new Color(129, 51, 56),
            C64_CYAN = new Color(117, 206, 200),
            C64_PURPLE = new Color(142, 60, 151),
            C64_GREEN = new Color(86, 172, 77),
            C64_BLUE = new Color(46, 44, 155),
            C64_YELLOW = new Color(237, 241, 113),
            C64_ORANGE = new Color(142, 80, 41),
            C64_BROWN = new Color(85, 56, 0),
            C64_LIGHT_RED = new Color(196, 108, 113),
            C64_DARK_GRAY = new Color(74, 74, 74),
            C64_GRAY = new Color(123, 123, 123),
            C64_LIGHT_GREEN = new Color(169, 255, 159),
            C64_LIGHT_BLUE = new Color(112, 109, 235),
            C64_LIGHT_GRAY = new Color(178, 178, 178);

    /**
     * ZX Spectrum 15-color palette, including bright variants.
     */
    public static final Color ZX_BLACK = new Color(0, 0, 0),
            ZX_BLUE = new Color(0, 0, 205),
            ZX_RED = new Color(205, 0, 0),
            ZX_MAGENTA = new Color(205, 0, 205),
            ZX_GREEN = new Color(0, 205, 0),
            ZX_CYAN = new Color(0, 205, 205),
            ZX_YELLOW = new Color(205, 205, 0),
            ZX_WHITE = new Color(205, 205, 205),
            ZX_BRIGHT_BLUE = new Color(0, 0, 255),
            ZX_BRIGHT_RED = new Color(255, 0, 0),
            ZX_BRIGHT_MAGENTA = new Color(255, 0, 255),
            ZX_BRIGHT_GREEN = new Color(0, 255, 0),
            ZX_BRIGHT_CYAN = new Color(0, 255, 255),
            ZX_BRIGHT_YELLOW = new Color(255, 255, 0),
            ZX_BRIGHT_WHITE = new Color(255, 255, 255);

    /**
     * BBC Micro 8-color logical palette.
     */
    public static final Color BBC_BLACK = new Color(0, 0, 0),
            BBC_RED = new Color(255, 0, 0),
            BBC_GREEN = new Color(0, 255, 0),
            BBC_YELLOW = new Color(255, 255, 0),
            BBC_BLUE = new Color(0, 0, 255),
            BBC_MAGENTA = new Color(255, 0, 255),
            BBC_CYAN = new Color(0, 255, 255),
            BBC_WHITE = new Color(255, 255, 255);

    /**
     * IBM CGA 16-color RGBI palette.
     */
    public static final Color CGA_BLACK = new Color(0, 0, 0),
            CGA_BLUE = new Color(0, 0, 170),
            CGA_GREEN = new Color(0, 170, 0),
            CGA_CYAN = new Color(0, 170, 170),
            CGA_RED = new Color(170, 0, 0),
            CGA_MAGENTA = new Color(170, 0, 170),
            CGA_BROWN = new Color(170, 85, 0),
            CGA_LIGHT_GRAY = new Color(170, 170, 170),
            CGA_DARK_GRAY = new Color(85, 85, 85),
            CGA_BRIGHT_BLUE = new Color(85, 85, 255),
            CGA_BRIGHT_GREEN = new Color(85, 255, 85),
            CGA_BRIGHT_CYAN = new Color(85, 255, 255),
            CGA_BRIGHT_RED = new Color(255, 85, 85),
            CGA_BRIGHT_MAGENTA = new Color(255, 85, 255),
            CGA_YELLOW = new Color(255, 255, 85),
            CGA_WHITE = new Color(255, 255, 255);

    /**
     * Nintendo Game Boy DMG 4-shade green palette.
     */
    public static final Color GB_DARK_GREEN = new Color(15, 56, 15),
            GB_MEDIUM_DARK_GREEN = new Color(48, 98, 48),
            GB_MEDIUM_LIGHT_GREEN = new Color(139, 172, 15),
            GB_LIGHT_GREEN = new Color(155, 188, 15);

    /**
     * MSX/TMS9918-style 15-color palette.
     */
    public static final Color MSX_BLACK = new Color(0, 0, 0),
            MSX_MEDIUM_GREEN = new Color(33, 200, 66),
            MSX_LIGHT_GREEN = new Color(94, 220, 120),
            MSX_DARK_BLUE = new Color(84, 85, 237),
            MSX_LIGHT_BLUE = new Color(125, 118, 252),
            MSX_DARK_RED = new Color(212, 82, 77),
            MSX_CYAN = new Color(66, 235, 245),
            MSX_MEDIUM_RED = new Color(252, 85, 84),
            MSX_LIGHT_RED = new Color(255, 121, 120),
            MSX_DARK_YELLOW = new Color(212, 193, 84),
            MSX_LIGHT_YELLOW = new Color(230, 206, 128),
            MSX_DARK_GREEN = new Color(33, 176, 59),
            MSX_MAGENTA = new Color(201, 91, 186),
            MSX_GRAY = new Color(204, 204, 204),
            MSX_WHITE = new Color(255, 255, 255);

    private record DistinctColorKey(int index, int saturationBits) {
    }

    private static final Map<DistinctColorKey, Color> DISTINCT_COLOR_CACHE = new ConcurrentHashMap<>();

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
        return DISTINCT_COLOR_CACHE.computeIfAbsent(key,
                k -> new Color(Color.HSBtoRGB(((float) index) * magicNumber, clampedSaturation, 1.0f)));

    }

}
