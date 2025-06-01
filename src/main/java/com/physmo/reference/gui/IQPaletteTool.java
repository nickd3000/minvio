package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiButton;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.GuiSlider;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The IQPaletteTool class is a graphical interface for experimenting
 * with an IQPalette, enabling users to manipulate palette controls
 * using sliders and observe the resulting color transitions.
 * It extends MinvioApp, providing a GUI-based application.
 * <p>
 * This application includes:
 * <p>
 * - A graphical panel where generated colors and RGB component visualizations are displayed.
 * - A series of sliders for controlling the parameters of the IQPalette.
 * - A randomization button to set sliders to random values.
 * <p>
 * Features:
 * - Real-time updates to the color palette based on user slider input.
 * - Randomization of slider values to explore various settings.
 * - Default slider settings option for a predefined starting point.
 */
public class IQPaletteTool extends MinvioApp {

    GuiContext guiContext;
    GuiPanel graphPanel;

    Map<Integer, GuiSlider> sliders = new HashMap<>();
    double[] sliderValues = new double[12];
    Color[] componentColors;
    IQPalette iqPalette = new IQPalette();

    public static void main(String... args) {
        MinvioApp app = new IQPaletteTool();

        app.start(500, 400, "IQ Palette", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());

        graphPanel = new GuiPanel(new Rect(10, 10, bd.getWidth() - 20, 240));


        guiContext.add(graphPanel);

        int sliderHeight = 20;
        int sliderWidth = 150;

        // Create the sliders.
        for (int i = 0; i < 12; i++) {
            int column = i / 4;
            int row = i % 4;
            GuiSlider slider = new GuiSlider(new Rect(10 + (column * (sliderWidth + 10)), 260 + (row * (sliderHeight + 5)), sliderWidth, sliderHeight));
            int finalI = i;
            slider.setOnChangedHandler(val -> {
                sliderValues[finalI] = val;
                updateIQPaletteControls();
            });
            sliders.put(i, slider);
            guiContext.add(slider);
        }

        retrieveAllSliderValues();

        GuiButton randomizeButton = new GuiButton(new Rect(10, 370, 110, 25), "Randomize");
        randomizeButton.setAction(this::randomizeSliders);
        guiContext.add(randomizeButton);

        GuiButton exportButton = new GuiButton(new Rect(10 + 150, 370, 110, 25), "Export");
        exportButton.setAction(this::export);
        guiContext.add(exportButton);

        componentColors = new Color[3];
        componentColors[0] = new Color(142, 38, 38, 0xff);
        componentColors[1] = new Color(51, 172, 51, 0xff);
        componentColors[2] = new Color(56, 106, 200, 0xff);

        setDefaults();
    }

    public void setDefaults() {
        double[] initialValues = new double[]{
                0.25, 0.95, 1.0, 0.0,
                0.25, 0.95, 1.0, 0.33,
                0.25, 0.95, 1.0, 0.66,
        };

        for (int i = 0; i < initialValues.length; i++) {
            sliders.get(i).setValue(initialValues[i]);
        }
        retrieveAllSliderValues();
        iqPalette.setControls(sliderValues);
    }

    public void updateIQPaletteControls() {
        iqPalette.setControls(sliderValues);
    }

    public void randomizeSliders() {
        for (int i = 0; i < 12; i++) {
            sliders.get(i).setValue(Math.random());
        }
        retrieveAllSliderValues();
        updateIQPaletteControls();
    }

    /**
     * Exports the current state of slider values by formatting them to two decimal places and
     * printing the resulting values as a comma-separated string.
     */
    public void export() {
        String result = Arrays.stream(sliderValues)
                .mapToObj(value -> String.format("%.2f", value)) // Format each value to 2 decimal places
                .collect(Collectors.joining(","));
        System.out.println(result);
    }

    public void retrieveAllSliderValues() {
        for (int i = 0; i < 12; i++) {
            sliderValues[i] = sliders.get(i).getValue();
        }
    }

    @Override
    public void draw(double delta) {
        cls();
        guiContext.tick();
        DrawingContext panelDc = graphPanel.getDc();
        panelDc.setDrawColor(Color.ORANGE);
        panelDc.cls(Color.darkGray);
        int numPoints = panelDc.getWidth();


        for (int x = 0; x < numPoints; x++) {
            double xx = (double) x / (double) numPoints;
            int rgb = iqPalette.getRgb(xx);

            int red = rgb >> 16 & 0xff;
            panelDc.setDrawColor(componentColors[0]);
            panelDc.drawFilledRect(x, panelDc.getHeight() - (red / 2), 2, 2);

            int green = rgb >> 8 & 0xff;
            panelDc.setDrawColor(componentColors[1]);
            panelDc.drawFilledRect(x, panelDc.getHeight() - (green / 2), 2, 2);

            int blue = rgb & 0xff;
            panelDc.setDrawColor(componentColors[2]);
            panelDc.drawFilledRect(x, panelDc.getHeight() - (blue / 2), 2, 2);


            panelDc.setDrawColor(new Color(rgb));
            panelDc.drawFilledRect(x, 20, 1, 60);
        }

        guiContext.drawAll(getDrawingContext());
    }


}