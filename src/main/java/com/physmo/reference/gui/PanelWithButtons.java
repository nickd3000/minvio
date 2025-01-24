package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

public class PanelWithButtons extends MinvioApp {

    GuiContext guiContext;
    int counter = 0;

    public static void main(String... args) {
        MinvioApp app = new PanelWithButtons();

        app.start(300, 200, "PanelWithButtons", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        // Create the GUI context that will manage everything GUI related.
        guiContext = new GuiContext(getBasicDisplay());

        // Create a GUI Panel
        GuiPanel guiPanel = new GuiPanel(new Rect(0, 0, getWidth(), getHeight()));

        // Create the first button, add an action then add it as a child of the panel.
        GuiButton minusButton = new GuiButton(new Rect(10, 10, 30, 30), "-");
        minusButton.setAction(() -> counter--);
        guiPanel.add(minusButton);

        // Create the second button, add an action then add it as a child of the panel.
        GuiButton plusButton = new GuiButton(new Rect(45, 10, 30, 30), "+");
        plusButton.setAction(() -> counter++);
        guiPanel.add(plusButton);

        GuiButton resetButton = new GuiButton(new Rect(95, 10, 70, 30), "Reset");
        resetButton.setAction(() -> counter = 0);
        guiPanel.add(resetButton);

        // Finally add the panel to the GUI context.
        guiContext.add(guiPanel);

    }

    @Override
    public void draw(double delta) {
        guiContext.tick();
        guiContext.drawAll(getDrawingContext());
        drawText("val:" + this.counter, 10, 90);
    }


}
