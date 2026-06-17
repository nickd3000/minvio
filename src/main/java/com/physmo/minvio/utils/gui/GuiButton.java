package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiUtils;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

/**
 * Represents a graphical user interface button within a GUI system.
 * The GuiButton class is responsible for rendering a button, handling mouse interaction,
 * and executing an associated action when the button is activated.
 *
 * This class extends GuiContainer and provides functionality specific to button interaction
 * and visual representation, including displaying a text label and processing mouse events.
 */
public class GuiButton extends GuiContainer {
    Color col = Color.BLUE;
    private final Font font = new Font("Verdana", Font.PLAIN, 15);
    boolean visiblyPressed = false;
    boolean buttonActivated = false;
    private final List<Runnable> actionListeners = new ArrayList<>();
    private String text = null;

    /**
     * Creates a button with no label.
     *
     * @param rect button bounds
     */
    public GuiButton(Rect rect) {
        super(rect);
    }

    /**
     * Creates a button with a label.
     *
     * @param rect button bounds
     * @param text label text
     */
    public GuiButton(Rect rect, String text) {
        super(rect);
        this.text = text;
    }

    /**
     * Adds an action listener to the button.
     *
     * @param action the {@link Runnable} to be executed when the button is triggered.
     */
    public void addActionListener(Runnable action) {
        this.actionListeners.add(action);
    }

    @Override
    public void draw(GuiContext guiContext) {

        dc.setDrawColor(guiContext.getGuiStyle().getButtonColor());
        dc.drawFilledRect(0, 0, rect.w, rect.h);

        if (!visiblyPressed) {
            GuiUtils.drawBevelBorderOut(dc, guiContext.getGuiStyle(), 0, 0, rect.w, rect.h);
        } else {
            GuiUtils.drawBevelBorderIn(dc, guiContext.getGuiStyle(), 0, 0, rect.w, rect.h);
        }

        if (text != null) {
            int yShift = visiblyPressed ? 2 : 0;
            yShift += 2;
            GuiUtils.drawTextWithinRect(dc, rect, guiContext.getGuiStyle(), font, text, 0, yShift);
        }
    }

    /**
     * Tests a local point against inclusive button bounds.
     *
     * @param x local x-coordinate
     * @param y local y-coordinate
     * @return {@code true} when inside or on the right/bottom edge
     */
    public boolean isPointInside(int x, int y) {
        if (x < 0 || y < 0) return false;
        return x <= rect.w && y <= rect.h;
    }


    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {
        if (guiMessage == MOUSE_MOVE) {

            if (!buttonActivated) return;
            MouseMessageData md = (MouseMessageData) object;

            var wasVisiblyPressed = visiblyPressed;

            visiblyPressed = isPointInside(md.x, md.y);

            if (wasVisiblyPressed != visiblyPressed) this.dirty = true;
        }

        if (guiMessage == MOUSE_BUTTON_DOWN) {
            this.dirty = true;
            col = Color.YELLOW;
            buttonActivated = true;
            visiblyPressed = true;
        }
        if (guiMessage == MOUSE_BUTTON_UP) {
            MouseMessageData md = (MouseMessageData) object;
            if (isPointInside(md.x, md.y)) {
                for (Runnable actionListener : actionListeners) {
                    actionListener.run();
                }
            }

            this.dirty = true;
            col = Color.BLACK;
            buttonActivated = false;
            visiblyPressed = false;
        }

    }

}
