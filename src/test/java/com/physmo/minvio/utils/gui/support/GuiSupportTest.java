package com.physmo.minvio.utils.gui.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GuiSupportTest {

    @Test
    void mouseMessageDataMapsConstructorArguments() {
        MouseMessageData data = new MouseMessageData(-10, 0, 3);

        assertEquals(-10, data.x);
        assertEquals(0, data.y);
        assertEquals(3, data.button);
    }

    @Test
    void guiMessageValuesRemainStableForEventRouting() {
        assertArrayEquals(new GuiMessage[]{
                GuiMessage.MOUSE_ENTER,
                GuiMessage.MOUSE_LEAVE,
                GuiMessage.MOUSE_MOVE,
                GuiMessage.MOUSE_BUTTON_DOWN,
                GuiMessage.MOUSE_BUTTON_UP
        }, GuiMessage.values());
    }
}
