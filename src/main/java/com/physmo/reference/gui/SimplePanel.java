package com.physmo.reference.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;

import java.awt.Color;

/**
 * The SimplePanel class extends the MinvioApp framework to create
 * a graphical application with a simple panel where graphical
 * elements can be drawn and interacted with.
 * <p>
 * This class demonstrates the use of GuiContext and GuiPanel from
 * the Minvio framework and handles basic initialization and drawing
 * logic. It uses mouse interaction to dynamically update the drawing
 * within the panel.
 * <p>
 * Functionality includes:
 * - Setting up a GUI context and adding a panel to it.
 * - Handling initialization using the init method.
 * - Drawing elements within the panel, such as a filled circle
 *   that follows the mouse position, using the draw method.
 */
public class SimplePanel extends MinvioApp {

    GuiContext guiContext;
    GuiPanel guiPanel;

    /**
     * The main method serves as the entry point of the SimplePanel application.
     * It initializes a graphical application by creating an instance of the SimplePanel class,
     * which extends the MinvioApp framework and starts the application with the specified
     * window size, title, and frame rate.
     *
     * @param args command-line arguments passed to the program, not used in this application.
     */
    public static void main(String... args) {
        MinvioApp app = new SimplePanel();

        app.start(300, 300, "SimplePanel Example", 60);
    }

    /**
     * Initializes the graphical user interface context and panel for the application.
     * This method sets up the `GuiContext` and adds a `GuiPanel` to it,
     * which serves as the primary container for graphical elements within the application.
     *
     * @param bd the `BasicDisplay` instance used to provide the graphical display,
     *           from which the `GuiContext` is initialized.
     */
    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());

        guiPanel = new GuiPanel(new Rect(20, 20, 260, 260));

        guiContext.add(guiPanel);

    }

    /**
     * The draw method is responsible for rendering the graphics in the application window.
     * It clears the screen, updates the GUI context, and draws graphical elements
     * such as a filled circle that tracks the mouse's position. The method leverages
     * the GUI context and the drawing context of the GuiPanel to perform the rendering.
     *
     * @param delta The time elapsed between the previous and current frame render calls,
     *              allowing for time-dependent updates to animations or other graphical elements.
     */
    @Override
    public void draw(double delta) {
        cls();
        guiContext.tick();

        guiPanel.getDc().setDrawColor(Color.ORANGE);
        guiPanel.getDc().cls(Color.darkGray);
        guiPanel.getDc().drawFilledCircle(getMouseX() - 20, getMouseY() - 20, 50);

        guiContext.drawAll(getDrawingContext());
    }


}