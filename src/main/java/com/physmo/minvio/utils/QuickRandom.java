package com.physmo.minvio.utils;

/**
 * A faster random number generator that is good enough for graphics
 * does not use it for anything important.
 */
public class QuickRandom {
    private long seed;

    private static final long MULTIPLIER = 0x5DEECE66DL;
    private static final long ADDEND = 0xBL;
    private static final long MASK = (1L << 48) - 1;

    /**
     * Creates a generator seeded from the current nanotime.
     */
    public QuickRandom() {
        this((8682522807148012L * 181783497276652981L) ^ System.nanoTime());
    }

    /**
     * Creates a deterministic generator from a seed.
     *
     * @param seed initial seed
     */
    public QuickRandom(long seed) {
        setSeed(seed);
    }

    /**
     * Reinitializes the generator state.
     *
     * @param seed replacement seed
     */
    public void setSeed(long seed) {
        this.seed = (((seed * 7879) ^ MULTIPLIER) + seed * 32_452_867) & MASK;
    }

    /**
     * Returns the next pseudorandom value.
     *
     * @return value greater than or equal to zero and less than one
     */
    public double nextDouble() {
        return (((long) (next(26)) << 27) + next(27)) / (double) (1L << 53);
    }

    private int next(int bits) {
        seed = (seed * MULTIPLIER + ADDEND) & MASK;
        return (int) (seed >>> (48 - bits));
    }
}
