package com.physmo.reference.experiments.fractaltile;

public class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private final int priority;
    private final Runnable runnable;
    int zoom;
    int column;
    int row;
    TileManager tileManager;

    public PrioritizedTask(int priority, TileManager tileManager, int zoom, int column, int row, Runnable runnable) {
        this.priority = priority;
        this.runnable = runnable;
        this.row = row;
        this.column = column;
        this.zoom = zoom;
        this.tileManager = tileManager;
    }

    public int getAdjustedPriority() {

        boolean found = false;

        ActiveWindow activeWindow = tileManager.getActiveWindow();

        if (column >= activeWindow.x() && column <= activeWindow.x() + activeWindow.width() && row >= activeWindow.y() && row <= activeWindow.y() + activeWindow.height()) {
            found = true;
        }

        if (found) return priority;
        return priority + 30;
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
