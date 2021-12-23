package com.physmo.minvio.utils;

// A faster random number generator that is good enough for graphics,
// do not use for anything important.
public class QuickRandom {
    private long seed;

    private static final long MULTIPLIER = 0x5DEECE66DL;
    private static final long ADDEND = 0xBL;
    private static final long MASK = (1L << 48) - 1;

    public QuickRandom() {
        this((8682522807148012L * 181783497276652981L) ^ System.nanoTime());
    }

    public QuickRandom(long seed) {
        setSeed(seed);
    }

    public void setSeed(long seed) {
        this.seed = (((seed * 7879) ^ MULTIPLIER) + seed * 32_452_867) & MASK;
    }

    public double nextDouble() {
        return (((long) (next(26)) << 27) + next(27)) / (double) (1L << 53);
    }

    private int next(int bits) {
        seed = (seed * MULTIPLIER + ADDEND) & MASK;
        return (int) (seed >>> (48 - bits));
    }
}
