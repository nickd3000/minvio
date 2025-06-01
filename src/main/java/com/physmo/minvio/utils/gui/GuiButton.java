package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiUtils;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.awt.Color;
import java.awt.Font;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

/**
 * Represents a graphical user interface button within a GUI system.
 * The GuiButton class is responsible for rendering a button, handling mouse interaction,
 * and executing an associated action when the button is activated.
 * <p>
 * This class extends GuiContainer and provides functionality specific to button interaction
 * and visual representation, including displaying a text label and processing mouse events.
 */
public class GuiButton extends GuiContainer {
    Color col = Color.BLUE;
    private final Font font = new Font("Verdana", Font.PLAIN, 15);
    boolean visiblyPressed = false;
    boolean buttonActivated = false;
    Runnable action = null;
    private String text = null;

    public GuiButton(Rect rect) {
        super(rect);
    }

    public GuiButton(Rect rect, String text) {
        super(rect);
        this.text = text;
    }

    /**
     * Sets the action to be executed when the button is activated.
     *
     * @param action the {@link Runnable} to be executed when the button is triggered.
     *               If set to null, no action will be performed upon activation.
     */
    public void setAction(Runnable action) {
        this.action = action;
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
            GuiUtils.drawTextWithinRect(dc, rect, guiContext.getGuiStyle(), font, text, 0, yShift);
        }
    }

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
                if (action != null) action.run();
            }

            this.dirty = true;
            col = Color.BLACK;
            buttonActivated = false;
            visiblyPressed = false;
        }

    }

}
