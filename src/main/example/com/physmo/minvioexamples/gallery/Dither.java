package com.physmo.minvioexamples.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Dither extends MinvioApp {
    List<Color> palette = new ArrayList<>();
    BufferedImage srcImage;
    BufferedImage dstImage;
    Graphics srcGraphics;
    Graphics dstGraphics;
    int er, eg, eb; // errors.

    public static void main(String[] args) {
        MinvioApp app = new Dither();
        app.start(new BasicDisplayAwt(600, 600), "Dither", 20);

    }

    @Override
    public void draw(BasicDisplay bd, double delta) {

    }
}
