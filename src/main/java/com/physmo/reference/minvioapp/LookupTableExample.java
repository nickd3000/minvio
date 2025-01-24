package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.LookupTable;

import java.awt.Color;

public class LookupTableExample extends MinvioApp {

    int x = 0;

    public static void main(String... args) {
        MinvioApp app = new LookupTableExample();
        app.start(400, 400, "Lookup Table Example", 30);
    }

    @Override
    public void draw(double delta) {
        LookupTable lookupTable = new LookupTable(0.0, 400.0, 100, operand -> (Math.sin(operand / 100) + 1.0) * 50.0);

        for (int x = 0; x < 400; x++) {
            setDrawColor(Color.black);
            drawPoint(x, (int) (lookupTable.getValue(x)));
            setDrawColor(Color.blue);
            drawPoint(x, (int) ((Math.sin((double) x / 100) + 1.0) * 50.0));
        }
    }
}