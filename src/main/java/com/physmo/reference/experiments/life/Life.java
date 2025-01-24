package com.physmo.reference.experiments.life;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.support.GuiMessage;

import java.awt.Color;

public class Life extends MinvioApp {
    GuiContext guiContext;
    GuiPanel panel1;
    GuiPanel panel2;

    public static void main(String[] args) {
        MinvioApp app = new Life();
        app.start(400, 400, "Matrix Drawer Example", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        super.init(bd);

        panel1 = new GuiPanel(new Rect(0, 0, 200, 200));
        panel2 = new GuiPanel(new Rect(200, 0, 200, 200)) {
            @Override
            public void onMessage(GuiMessage guiMessage, Object object) {
                super.onMessage(guiMessage, object);
                if (guiMessage == GuiMessage.MOUSE_BUTTON_DOWN) {
                    //System.out.println("flaps");
                }
            }
        };

        guiContext = new GuiContext(getBasicDisplay());
        guiContext.add(panel1);
        guiContext.add(panel2);

    }

    @Override
    public void draw(double delta) {
        //if (panel1 == null) return;

        DrawingContext dc1 = panel1.getDc();
        if (Math.random() < 0.1) {
            dc1.setDrawColor(Color.ORANGE);
        } else {
            dc1.setDrawColor(Color.WHITE);
        }
        dc1.drawFilledRect(10, 10, 40, 40);
        panel1.setDirty(true);

        guiContext.tick();
        guiContext.drawAll(getDrawingContext());
    }
}
