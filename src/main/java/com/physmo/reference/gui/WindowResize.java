package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

import java.awt.Color;


public class WindowResize extends MinvioApp {

    GuiContext guiContext;
    GuiPanel guiPanel;

    public static void main(String... args) {
        MinvioApp app = new WindowResize();

        app.start(300, 300, "WindowResize Example", 60);
    }


    @Override
    public void init(BasicDisplay bd) {

        bd.addResizeListener((x, y) -> {
            guiPanel.setRect(new Rect(20, 20, x - 40, y - 40));
            return 0;
        });

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
        guiPanel.getDc().drawFilledCircle(getMouseX() - 20, getMouseY() - 20, 50);

        guiContext.drawAll(getDrawingContext());

        setDrawColor(Color.blue);
        drawRect(getMouseX(), getMouseY(), 20, 20);
    }


}