package com.physmo.minvio.utils;

import com.physmo.minvio.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The BucketList class represents a 2-dimensional grid of buckets,
 * which are used to store items based on their position.
 * Each bucket can contain multiple items.
 */
public class BucketList {
    int cellSize = 64;
    int cellsWide = 0;
    int cellsHigh = 0;
    final List<List<Integer>> buckets;

    public BucketList(int cellSize, int width, int height) {
        this.cellSize = cellSize;
        cellsWide = (width / cellSize) + 1;
        cellsHigh = (height / cellSize) + 1;
        buckets = new ArrayList<>();
        for (int i = 0; i < cellsWide * cellsHigh; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    public int getBucketCount() {
        return buckets.size();
    }

    public void clearAll() {
        for (List<Integer> bucket : buckets) {
            bucket.clear();
        }
    }

    public void addPointList(List<Position> points) {
        List<Integer> workList;
        for (int i = 0; i < points.size(); i++) {
            Position p = points.get(i);
            workList = getBucketForPoint(p.x, p.y);
            workList.add(i);
        }
    }

    public List<Integer> getBucketForPoint(double x, double y) {
        int cx = (int) x / cellSize;
        int cy = (int) y / cellSize;
        return buckets.get(cx + (cy * cellsWide));
    }

    public void addIndividual(double x, double y, int index) {
        List<Integer> workList;
        workList = getBucketForPoint(x, y);
        workList.add(index);
    }

    public List<List<Integer>> getBucketsAroundPoint(double x, double y, int r) {
        List<List<Integer>> list = new ArrayList<>();
        int cx = (int) x / cellSize;
        int cy = (int) y / cellSize;

        for (int yy = cy - r; yy <= cy + r; yy++) {
            for (int xx = cx - r; xx <= cx + r; xx++) {
                list.add(getBucket(xx, yy));
            }
        }

        return list;
    }

    public List<Integer> getBucket(int cx, int cy) {
        if (cx < 0) return null;
        if (cy < 0) return null;
        if (cx >= cellsWide) return null;
        if (cy >= cellsHigh) return null;
        return buckets.get(cx + (cy * cellsWide));
    }

    // Get summary of buckets outside given area.
    public List<Integer[]> getOutsideBucketSummary(double x, double y, int r) {
        int cx = (int) x / cellSize;
        int cy = (int) y / cellSize;

        // position of bucket center
        // number of items

        List<Integer[]> outsideList = new ArrayList<>();
        int skipped = 0;
        for (int cv = 0; cv < cellsHigh; cv++) {
            for (int ch = 0; ch < cellsWide; ch++) {
                if (Math.abs(cv - cy) <= r && Math.abs(ch - cx) <= r) continue;

                Integer[] bucketOverview = new Integer[3]; // x,y,count
                bucketOverview[0] = ch * cellSize + (cellSize / 2);
                bucketOverview[1] = cv * cellSize + (cellSize / 2);
                bucketOverview[2] = buckets.get(ch + (cv * cellsWide)).size();
                outsideList.add(bucketOverview);
            }
        }

        return outsideList;
    }


    public List<Integer[]> getBucketSummary() {

        List<Integer[]> outsideList = new ArrayList<>();

        for (int cv = 0; cv < cellsHigh; cv++) {
            for (int ch = 0; ch < cellsWide; ch++) {
                Integer[] bucketOverview = new Integer[3]; // x,y,count
                bucketOverview[0] = ch * cellSize + (cellSize / 2);             // Cell column
                bucketOverview[1] = cv * cellSize + (cellSize / 2);             // Cell row
                bucketOverview[2] = buckets.get(ch + (cv * cellsWide)).size();  // Cell member count
                outsideList.add(bucketOverview);
            }
        }

        return outsideList;
    }

    public int getCellSize() {
        return cellSize;
    }
}
