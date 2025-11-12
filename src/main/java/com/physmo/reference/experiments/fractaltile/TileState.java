package com.physmo.reference.experiments.fractaltile;

public enum TileState {
    UNINITIALIZED,
    RENDERING_PREVIEW,
    AWAITING_FULL_RENDER,
    RENDERING_FULL,
    AWAITING_AA_RENDER,
    RENDERING_AA,
    COMPLETED
}
