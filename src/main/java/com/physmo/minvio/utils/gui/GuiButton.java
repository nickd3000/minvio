package com.physmo.minvio.utils.gui;

import com.physmo.minvio.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiUtils;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.awt.Color;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

public class GuiButton extends GuiContainer {
    Color col = Color.BLUE;
    boolean visiblyPressed = false;
    boolean buttonActivated = false;
    Runnable action = new Runnable() {
        @Override
        public void run() {

        }
    };

    public GuiButton(Rect rect) {
        super(rect);
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    @Override
    public void draw() {
        if (!visiblyPressed)
            GuiUtils.drawBevelBoxOut(dc, 0, 0, rect.w, rect.h);
        else
            GuiUtils.drawBevelBoxIn(dc, 0, 0, rect.w, rect.h);
    }

    public boolean isPointInside(int x, int y) {
        if (x < 0 || y < 0) return false;
        if (x > rect.w || y > rect.h) return false;
        return true;
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
            if (isPointInside(md.x, md.y)) action.run();
            this.dirty = true;
            col = Color.BLACK;
            buttonActivated = false;
            visiblyPressed = false;
        }

    }

}
