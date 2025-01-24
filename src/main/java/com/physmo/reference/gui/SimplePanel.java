package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

import java.awt.Color;

public class SimplePanel extends MinvioApp {

    GuiContext guiContext;
    GuiPanel guiPanel;
    int counter = 0;

    public static void main(String... args) {
        MinvioApp app = new SimplePanel();

        app.start(300, 300, "SimplePanel Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());

        guiPanel = new GuiPanel(new Rect(20, 20, 260, 260));

        guiContext.add(guiPanel);

    }

    @Override
    public void draw(double delta) {
        cls();
        guiContext.tick();

        guiPanel.getDc().setDrawColor(Color.ORANGE);
        guiPanel.getDc().cls(Color.darkGray);
        guiPanel.getDc().drawFilledCircle(getMouseX(), getMouseY(), 50);

        guiContext.drawAll(getDrawingContext());
    }


}