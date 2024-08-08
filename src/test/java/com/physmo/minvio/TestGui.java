package com.physmo.minvio;

import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

import java.awt.Color;

public class TestGui extends MinvioApp {

    int counter = 0;
    GuiContext guiContext;


    public static void main(String... args) {
        MinvioApp app = new TestGui();

        app.start(new BasicDisplayAwt(400, 200), "Simple Example", 60);
    }

    GuiPanel guiPanel2;

    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());

        GuiPanel guiPanel = new GuiPanel(new Rect(200, 0, 200, 200));
        GuiButton guiButton1 = new GuiButton(new Rect(10, 10, 50, 50));
        guiButton1.setAction(() -> {
            counter++;
        });
        guiPanel.add(guiButton1);
        GuiButton guiButton2 = new GuiButton(new Rect(60, 10, 30, 20));
        guiPanel.add(guiButton2);
        guiContext.add(guiPanel);

        guiPanel2 = new GuiPanel(new Rect(0, 0, 200, 200));
        guiContext.add(guiPanel2);
    }

    @Override
    public void draw(double delta) {
        guiContext.tick();

        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.WHITE);
        drawFilledRect(75, 75, 50, 50);
        setDrawColor(Color.BLUE);
        drawCircle(100, 100, 70);
        //drawText("X:" + getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
        drawText("Presses:" + counter, 10, 190);
        drawText("Tick :" + getFps(), 10, 160);

        DrawingContext dc1 = guiPanel2.getDc();
        dc1.setDrawColor(Color.ORANGE);
        dc1.drawFilledRect(10, 10, 30, 30);

        guiContext.drawAll(getDrawingContext());
    }
}
