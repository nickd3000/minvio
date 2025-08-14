package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiLabel;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.GuiSlider;
import com.physmo.minvio.utils.gui.support.GuiStyle;

import java.awt.Color;

public class CustomGuiStyle extends MinvioApp {

    GuiContext guiContext;
    int counter = 0;

    public static void main(String... args) {
        MinvioApp app = new CustomGuiStyle();

        app.start(300, 200, "CustomGuiStyle", 60);
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

        GuiLabel label = new GuiLabel(new Rect(10, 160, 150, 20), "");
        guiPanel.add(label);

        GuiSlider slider = new GuiSlider(new Rect(10, 120, 150, 20));
        slider.setOnChangedHandler(value -> label.setText(String.format("Slider: %.2f", value)));
        guiPanel.add(slider);


        // Finally add the panel to the GUI context.
        guiContext.add(guiPanel);


        // Define a custom GuiStyle that will change the way Gui elements are colored.
        guiContext.setGuiStyle(new GuiStyle() {

            // Make some vaguely Amiga Workbench 1.3 style colors
            final Color backgroundColor = new Color(43, 107, 255);
            final Color buttonColor = new Color(32, 136, 255);
            final Color bevelLight = new Color(255, 255, 255);
            final Color bevelDark = new Color(0, 0, 0);
            final Color accent = new Color(255, 104, 0);
            final Color textColor = new Color(255, 255, 255);

            @Override
            public Color getBackgroundColor() {
                return backgroundColor;
            }

            @Override
            public Color getButtonColor() {
                return buttonColor;
            }

            @Override
            public Color getBevelLight() {
                return bevelLight;
            }

            @Override
            public Color getBevelDark() {
                return bevelDark;
            }

            @Override
            public Color getAccent() {
                return accent;
            }

            @Override
            public Color getTextColor() {
                return textColor;
            }
        });
    }

    @Override
    public void draw(double delta) {
        guiContext.tick();
        guiContext.drawAll(getDrawingContext());
        setDrawColor(Color.WHITE);
        drawText("val:" + this.counter, 10, 90);
    }
}
