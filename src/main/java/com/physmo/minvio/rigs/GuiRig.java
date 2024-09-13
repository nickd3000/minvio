package com.physmo.minvio.rigs;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Rect;
import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiLabel;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.GuiSlider;


public class GuiRig extends MinvioApp {

    GuiContext guiContext;
    int counter = 0;

    public static void main(String... args) {
        MinvioApp app = new GuiRig();

        app.start(300, 300, "PanelWithButtons", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        // Create the GUI context that will manage everything GUI related.
        guiContext = new GuiContext(getBasicDisplay());

        // Create a GUI Panel
        GuiPanel guiPanel = new GuiPanel(new Rect(0, 0, 300, 300));

        // Create the first button, add an action then add it as a child of the panel.
        GuiButton guiButton1 = new GuiButton(new Rect(10, 10, 50, 50));
        guiButton1.setAction(() -> counter--);
        guiPanel.add(guiButton1);

        // Create the second button, add an action then add it as a child of the panel.
        GuiButton guiButton2 = new GuiButton(new Rect(70, 10, 50, 50));
        guiButton2.setAction(() -> counter++);
        guiPanel.add(guiButton2);

        // Create the third button, add an action then add it as a child of the panel.
        GuiButton guiButton3 = new GuiButton(new Rect(130, 10, 70, 25), "reset");
        guiButton3.setAction(() -> counter = 0);
        guiPanel.add(guiButton3);

        GuiLabel guiLabel1 = new GuiLabel(new Rect(10, 140, 150, 30), "Label 1");
        guiPanel.add(guiLabel1);
        GuiLabel guiLabel2 = new GuiLabel(new Rect(170, 140, 80, 30), "no");
        guiPanel.add(guiLabel2);

        GuiSlider guiSlider1 = new GuiSlider(new Rect(10, 100, 150, 20));
        guiSlider1.setOnChangedHandler(value -> {
            guiLabel2.setText(String.format("%.2f", value));
        });
        guiPanel.add(guiSlider1);


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