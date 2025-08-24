package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiLabel;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.GuiSlider;
import com.physmo.minvio.utils.gui.layout.GridLayout;


public class LayoutTestGridLayout extends MinvioApp {

    GuiContext guiContext;

    public static void main(String... args) {
        MinvioApp app = new LayoutTestGridLayout();

        app.start(300, 300, "LayoutTestGridLayout", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        // Create the GUI context that will manage everything GUI related.
        guiContext = new GuiContext(getBasicDisplay());

        // Create a GUI Panel
        GuiPanel guiPanel = new GuiPanel(new Rect(0, 0, 300, 300));
        guiPanel.setLayout(new GridLayout());

        guiPanel.add(new GuiLabel(new Rect(10, 10, 100, 20), "Label 1"));
        guiPanel.add(new GuiButton(new Rect(10, 10, 50, 50)));
        guiPanel.add(new GuiSlider(new Rect(10, 10, 150, 20)));

        // Create 4 buttons and add them as children of the panel.
        for (int i = 0; i < 8; i++) {
            GuiButton guiButton = new GuiButton(new Rect(10, 10, 50, 50));
            guiPanel.add(guiButton);
        }

        // Finally, add the panel to the GUI context.
        guiContext.add(guiPanel);

        guiPanel.calculateLayout();

        bd.addResizeListener((width, height) -> {
            guiPanel.setRect(new Rect(0, 0, width, height));
            guiPanel.calculateLayout();
            guiPanel.setDirtyRecursive(true);
            return 0;
        });
    }

    @Override
    public void draw(double delta) {
        guiContext.tick();
        guiContext.drawAll(getDrawingContext());
    }


}