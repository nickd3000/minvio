package com.physmo.reference.experiments.fractaltile;

import java.util.HashMap;
import java.util.Map;

public class TileManager {
    Map<Integer, Tile> tiles = new HashMap<>();

    public Tile getTile(int zoom, int x, int y) {
        Integer encodedKey = encode(zoom, x, y);
        return tiles.computeIfAbsent(encodedKey, integer -> initTile(zoom, x, y));
    }

    public Tile initTile(int zoom, int x, int y) {
        return new Tile(zoom, x, y);
    }

    // Encode 3 input values into one integer.
    public Integer encode(int zoom, int x, int y) {
        // Use the layout: [zoom: 8 bits][x: 12 bits][y: 12 bits]
        return ((zoom & 0xFF) << 24) | ((x & 0xFFF) << 12) | (y & 0xFFF);
    }

    public int[] decode(Integer encoded) {
        int zoom = (encoded >> 24) & 0xFF;
        int x = (encoded >> 12) & 0xFFF;
        int y = encoded & 0xFFF;
        return new int[]{zoom, x, y};
    }

}
