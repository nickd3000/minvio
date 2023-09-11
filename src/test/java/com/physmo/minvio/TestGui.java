package com.physmo.minvio;

import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

import java.awt.Color;

public class TestGui extends MinvioApp {

    GuiContext guiContext;
    int counter = 0;

    public static void main(String... args) {
        MinvioApp app = new TestGui();

        app.start(new BasicDisplayAwt(200, 200), "Simple Example", 60);
    }


    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());
        GuiPanel guiPanel = new GuiPanel(new Rect(40, 0, 200, 200));
        GuiButton guiButton1 = new GuiButton(new Rect(10, 10, 50, 50));
        guiButton1.setAction(() -> {
            counter++;
        });
        guiPanel.add(guiButton1);
        GuiButton guiButton2 = new GuiButton(new Rect(60, 10, 30, 20));
        guiPanel.add(guiButton2);
        guiContext.add(guiPanel);
    }

    @Override
    public void draw(DrawingContext dc, double delta) {
        guiContext.tick();

        dc.cls(Color.LIGHT_GRAY);
        dc.setDrawColor(Color.WHITE);
        dc.drawFilledRect(75, 75, 50, 50);
        dc.setDrawColor(Color.BLUE);
        dc.drawCircle(100, 100, 70);
        //dc.drawText("X:" + getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
        dc.drawText("Presses:" + counter, 10, 190);
        dc.drawText("Tick :" + getFps(), 10, 160);

        guiContext.drawAll(dc);
    }
}
