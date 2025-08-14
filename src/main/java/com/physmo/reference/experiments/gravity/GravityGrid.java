package com.physmo.reference.experiments.gravity;

import java.util.Arrays;

public class GravityGrid {

    public int cellSize;
    public int fieldWidth;
    public int fieldHeight;
    public int width;
    public int height;


    public int[][] cells;
    public int[] cellCounts;

    public GravityGrid(int fieldWidth, int fieldHeight, int cellSize, int maxParticles) {
        this.cellSize = cellSize;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        width = fieldWidth / cellSize;
        height = fieldHeight / cellSize;
        cells = new int[width * height][maxParticles];
        cellCounts = new int[width * height];
    }

    public int getCellId(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return 0;
        return (x / cellSize) + ((y / cellSize) * width);
    }

    public int[] getCell(int gx, int gy) {
        return cells[gx + gy * width];
    }

    public int getCellCount(int gx, int gy) {
        return cellCounts[gx + gy * width];
    }

    public void clear() {
        for (int[] cell : cells) {
            Arrays.fill(cell, -1);
        }
        Arrays.fill(cellCounts, 0);
    }

    public void addParticle(int x, int y, int particleId) {
        int cellId = getCellId(x, y);
        int index = cellCounts[cellId];
        cells[cellId][index] = particleId;
        cellCounts[cellId] = index + 1;
    }

}
