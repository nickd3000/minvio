package com.physmo.minvio.utils.gui.layout;

import com.physmo.minvio.utils.gui.GuiContainer;

import java.util.List;

/**
 * Layout callback used to reposition and resize a container's direct children.
 */
@FunctionalInterface
public interface Layout {
    /**
     * Applies layout to a parent's live child list.
     *
     * <p>Implementations may mutate child rectangles. The supplied list is the
     * parent's internal child list, so structural mutations affect the GUI tree.</p>
     *
     * @param parent parent container
     * @param children live mutable list of direct children
     */
    void handleLayout(GuiContainer parent, List<GuiContainer> children);
}
