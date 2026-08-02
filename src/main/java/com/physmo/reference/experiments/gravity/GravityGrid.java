package com.physmo.reference.experiments.gravity;

import java.util.Arrays;

public class GravityGrid {

    private final int cellSize;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int width;
    private final int height;
    private final int[] particleIndices;
    private final int[] cellCounts;
    private final int[] cellOffsets;
    private final int[] cellWritePositions;
    private final double[] cellPositionSumsX;
    private final double[] cellPositionSumsY;
    private boolean preparedForPopulation;

    public GravityGrid(int fieldWidth, int fieldHeight, int cellSize, int maxParticles) {
        if (fieldWidth <= 0 || fieldHeight <= 0) {
            throw new IllegalArgumentException("Field dimensions must be positive");
        }
        if (cellSize <= 0) {
            throw new IllegalArgumentException("Cell size must be positive");
        }
        if (maxParticles < 0) {
            throw new IllegalArgumentException("Maximum particle count cannot be negative");
        }

        this.cellSize = cellSize;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        width = 1 + (fieldWidth - 1) / cellSize;
        height = 1 + (fieldHeight - 1) / cellSize;
        int cellCount = width * height;
        particleIndices = new int[maxParticles];
        cellCounts = new int[cellCount];
        cellOffsets = new int[cellCount];
        cellWritePositions = new int[cellCount];
        cellPositionSumsX = new double[cellCount];
        cellPositionSumsY = new double[cellCount];
    }

    public int getCellId(int x, int y) {
        int gx = Math.min(Math.max(x, 0), fieldWidth - 1) / cellSize;
        int gy = Math.min(Math.max(y, 0), fieldHeight - 1) / cellSize;
        return gx + gy * width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellCount(int gx, int gy) {
        return cellCounts[gx + gy * width];
    }

    public int getCellOffset(int gx, int gy) {
        return cellOffsets[gx + gy * width];
    }

    public int getParticle(int index) {
        return particleIndices[index];
    }

    public double getCellCenterX(int gx, int gy) {
        int cellId = gx + gy * width;
        int count = cellCounts[cellId];
        if (count == 0) {
            throw new IllegalStateException("Cannot calculate the center of an empty cell");
        }
        return cellPositionSumsX[cellId] / count;
    }

    public double getCellCenterY(int gx, int gy) {
        int cellId = gx + gy * width;
        int count = cellCounts[cellId];
        if (count == 0) {
            throw new IllegalStateException("Cannot calculate the center of an empty cell");
        }
        return cellPositionSumsY[cellId] / count;
    }

    public void clear() {
        Arrays.fill(cellCounts, 0);
        Arrays.fill(cellPositionSumsX, 0);
        Arrays.fill(cellPositionSumsY, 0);
        preparedForPopulation = false;
    }

    public void countParticle(double x, double y) {
        if (preparedForPopulation) {
            throw new IllegalStateException("Cannot count particles after preparing cell offsets");
        }

        int cellId = getCellId((int) x, (int) y);
        cellCounts[cellId]++;
        cellPositionSumsX[cellId] += x;
        cellPositionSumsY[cellId] += y;
    }

    public void prepareForPopulation() {
        int offset = 0;
        for (int cellId = 0; cellId < cellCounts.length; cellId++) {
            cellOffsets[cellId] = offset;
            cellWritePositions[cellId] = offset;
            offset += cellCounts[cellId];
        }
        if (offset > particleIndices.length) {
            throw new IllegalStateException("Particle count exceeds grid capacity");
        }
        preparedForPopulation = true;
    }

    public void addParticle(double x, double y, int particleId) {
        if (!preparedForPopulation) {
            throw new IllegalStateException("Cell offsets must be prepared before adding particles");
        }

        int cellId = getCellId((int) x, (int) y);
        int writePosition = cellWritePositions[cellId];
        int cellEnd = cellOffsets[cellId] + cellCounts[cellId];
        if (writePosition >= cellEnd) {
            throw new IllegalStateException("More particles added to a cell than counted");
        }
        particleIndices[writePosition] = particleId;
        cellWritePositions[cellId] = writePosition + 1;
    }

}
