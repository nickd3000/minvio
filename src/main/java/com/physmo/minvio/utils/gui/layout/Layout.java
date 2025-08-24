package com.physmo.minvio.utils.gui.layout;

import com.physmo.minvio.utils.gui.GuiContainer;

import java.util.List;

public interface Layout {
    void handleLayout(GuiContainer parent, List<GuiContainer> children);
}
