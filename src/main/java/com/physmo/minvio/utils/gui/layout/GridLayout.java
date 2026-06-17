package com.physmo.minvio.utils.gui.layout;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContainer;

import java.util.List;

/**
 * Grid layout with fixed padding and automatic expansion to fit all children.
 *
 * <p>The initial row and column count are minimums. If more children are
 * present than fit, rows or columns are increased before calculating cell
 * sizes.</p>
 */
public class GridLayout implements Layout {

    int rows = 2;
    int cols = 2;
    int hPad = 5;
    int vPad = 5;

    /**
     * Creates a two-by-two grid layout.
     */
    public GridLayout() {
    }

    /**
     * Creates a grid layout.
     *
     * @param rows minimum row count
     * @param cols minimum column count
     * @throws IllegalArgumentException if either value is not positive
     */
    public GridLayout(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive");
        }
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Sizes and positions children inside the parent rectangle.
     *
     * <p>Each cell applies five pixels of horizontal and vertical padding on
     * both sides. Child dimensions are clamped to at least one pixel. Each
     * child then receives {@link GuiContainer#calculateLayout()} so nested
     * layouts can update.</p>
     *
     * @param parent parent container
     * @param children live mutable child list
     */
    @Override
    public void handleLayout(GuiContainer parent, List<GuiContainer> children) {

        int effectiveRows = rows;
        int effectiveCols = cols;

        // Adjust number of columns and rows to fit all children.
        while (children.size() > effectiveRows * effectiveCols) {
            if (effectiveCols > effectiveRows) effectiveCols++;
            else effectiveRows++;
        }

        int cellWidth = parent.getRect().w / effectiveCols;
        int cellHeight = parent.getRect().h / effectiveRows;

        for (int i = 0; i < children.size(); i++) {
            GuiContainer child = children.get(i);
            int cx = i % effectiveCols;
            int cy = i / effectiveCols;

            int x = (cx * cellWidth) + hPad;
            int y = (cy * cellHeight) + vPad;
            int w = Math.max(1, cellWidth - (hPad * 2));
            int h = Math.max(1, cellHeight - (vPad * 2));

            child.setRect(new Rect(x, y, w, h));
            child.calculateLayout();

        }

    }
}
