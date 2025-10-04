package com.physmo.reference.experiments.fractaltile;

import java.util.List;

public class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private final int priority;
    private final Runnable runnable;
    int zoom;
    int column;
    int row;
    List<Integer[]> activeTiles;

    public PrioritizedTask(int priority, List<Integer[]> activeTiles, int zoom, int column, int row, Runnable runnable) {
        this.priority = priority;
        this.runnable = runnable;
        this.row = row;
        this.column = column;
        this.zoom = zoom;
        this.activeTiles = activeTiles;
    }

    public int getAdjustedPriority() {
        int adjustedPriority = this.priority;
        boolean found = false;
        for (Integer[] activeTile : activeTiles) {
            if (activeTile[0] == zoom && activeTile[1] == column && activeTile[2] == row) {
                found = true;
                break;
            }
        }
        if (!found) adjustedPriority *= 30;
        return adjustedPriority;
    }


    @Override
    public int compareTo(PrioritizedTask o) {
        // Lower values mean higher priority
        return Integer.compare(getAdjustedPriority(), o.getAdjustedPriority());
    }

    @Override
    public void run() {
        runnable.run();
    }

}
