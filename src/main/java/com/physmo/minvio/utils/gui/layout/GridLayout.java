package com.physmo.minvio.utils.gui.layout;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContainer;

import java.util.List;

public class GridLayout implements Layout {

    int rows = 2;
    int cols = 2;
    int hPad = 5;
    int vPad = 5;

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

            int x = parent.getRect().x + (cx * cellWidth) + hPad;
            int y = parent.getRect().y + (cy * cellHeight) + vPad;
            int w = cellWidth - (hPad * 2);
            int h = cellHeight - (vPad * 2);

            child.setRect(new Rect(x, y, w, h));
            child.calculateLayout();

        }

    }
}
