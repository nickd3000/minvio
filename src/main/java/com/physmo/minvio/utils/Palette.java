package com.physmo.minvio.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Palette {
    // Rainbow
    public static Color RED = new Color(186, 36, 36);
    public static Color ORANGE = new Color(202, 96, 21);
    public static Color YELLOW = new Color(187, 174, 16);
    public static Color GREEN = new Color(88, 196, 33);
    public static Color BLUE = new Color(40, 135, 210);
    public static Color INDIGO = new Color(134, 33, 194);
    public static Color VIOLET = new Color(203, 17, 178);

    // Natural
    public static Color BROWN = new Color(112, 57, 24);
    public static Color MINT = new Color(30, 185, 131);

    // Grayscale / Neutrals
    public static Color BLACK = new Color(0, 0, 0);
    public static Color WHITE = new Color(255, 255, 255);
    public static Color GRAY_900 = new Color(25, 25, 25);
    public static Color GRAY_700 = new Color(55, 59, 65);
    public static Color GRAY_500 = new Color(108, 117, 125);
    public static Color GRAY_300 = new Color(200, 200, 200);
    public static Color GRAY_100 = new Color(245, 245, 247);

    // Warm tones
    public static Color CRIMSON = new Color(220, 20, 60);
    public static Color BRICK = new Color(178, 34, 34);
    public static Color SALMON = new Color(250, 128, 114);
    public static Color CORAL = new Color(255, 127, 80);
    public static Color AMBER = new Color(255, 191, 0);

    // Cool tones
    public static Color TEAL = new Color(0, 150, 136);
    public static Color CYAN = new Color(0, 188, 212);
    public static Color COBALT = new Color(0, 71, 171);
    public static Color NAVY = new Color(10, 28, 58);
    public static Color AQUA = new Color(127, 219, 255);

    // Pastels
    public static Color PASTEL_PINK = new Color(255, 183, 197);
    public static Color PASTEL_PEACH = new Color(255, 205, 178);
    public static Color PASTEL_YELLOW = new Color(255, 249, 177);
    public static Color PASTEL_GREEN = new Color(186, 255, 201);
    public static Color PASTEL_BLUE = new Color(186, 225, 255);
    public static Color PASTEL_LAVENDER = new Color(221, 214, 255);

    // Neon / Accents
    public static Color NEON_PINK = new Color(255, 20, 147);
    public static Color NEON_GREEN = new Color(57, 255, 20);
    public static Color NEON_BLUE = new Color(0, 255, 255);
    public static Color NEON_YELLOW = new Color(255, 255, 0);
    public static Color MAGENTA = new Color(255, 0, 255);

    // Earth tones
    public static Color SAND = new Color(237, 201, 175);
    public static Color TAN = new Color(210, 180, 140);
    public static Color OLIVE = new Color(128, 128, 0);
    public static Color FOREST = new Color(34, 139, 34);
    public static Color SLATE = new Color(112, 128, 144);
    public static Color CLAY = new Color(168, 112, 74);

    // UI-friendly accents
    public static Color PRIMARY = new Color(51, 102, 255);
    public static Color SUCCESS = new Color(40, 167, 69);
    public static Color WARNING = new Color(255, 193, 7);
    public static Color DANGER = new Color(220, 53, 69);
    public static Color INFO = new Color(23, 162, 184);

    // Fun / Playful
    public static Color BUBBLEGUM = new Color(255, 105, 180);
    public static Color COTTON_CANDY = new Color(255, 182, 222);
    public static Color WATERMELON = new Color(242, 71, 84);
    public static Color SLIME = new Color(132, 255, 90);
    public static Color LAVA = new Color(255, 77, 0);
    public static Color UNICORN = new Color(191, 128, 255);
    public static Color FLAMINGO = new Color(252, 142, 172);
    public static Color SKY_CANDY = new Color(116, 205, 255);
    public static Color GRAPE_SODA = new Color(123, 75, 199);
    public static Color BLUEBERRY = new Color(54, 93, 201);
    public static Color PINEAPPLE = new Color(255, 223, 72);
    public static Color MATCHA = new Color(135, 169, 107);
    public static Color MACNCHEESE = new Color(255, 173, 67);
    public static Color SHERBET = new Color(255, 201, 120);
    public static Color MINT_CREAM = new Color(234, 255, 244);
    public static Color PUMPKIN_SPICE = new Color(198, 98, 38);
    public static Color TROPICAL_SEA = new Color(0, 216, 204);
    public static Color GALAXY = new Color(48, 29, 92);
    public static Color SUNSET = new Color(255, 94, 98);
    public static Color SUNRISE = new Color(255, 166, 77);
    public static Color ICE_POP = new Color(0, 148, 255);
    public static Color PEACH_FIZZ = new Color(255, 170, 146);
    public static Color LEMON_SORBET = new Color(255, 246, 150);
    public static Color BERRY = new Color(171, 37, 107);

    static Map<Integer, Color> distinctColorCache = new HashMap<>();

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
        return distinctColorCache.computeIfAbsent(index, k -> new Color(Color.HSBtoRGB(((float) index) * magicNumber, (float) saturation, 1.0f)));

    }

}
