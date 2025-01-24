package com.physmo.reference.experiments.filedecoder;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;


// Experiment to decode atari st xenon data files
public class FileDecoder extends MinvioApp {

    String filePath = "/Volumes/DATA_2TB/Dropbox/Emulaton/unpacked/atariSt_xenon/sprit32.dat";
    Color background = new Color(218, 132, 78);
    byte[] data = null;
    int scale = 2;
    int stride = 128;

    public static void main(String[] args) {
        MinvioApp app = new FileDecoder();
        app.start(700, 700, "Anchor Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        super.init(bd);

        try {
            data = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void draw(double delta) {
        cls(Color.darkGray);
        int width = getMouseX() / 2;
        if (width < 8) width = 8;
        int pos = 0;
        int x, y;
        for (int i = 0; i < data.length; i++) {


            int d = data[i];
            //if (d<0) d+=128;

            int b = (d >> 4) & 0xf;
            int a = (d) & 0xf;

            int cs = 1;
            int yy = getMouseY() * 4;

            int g = 0;
            if ((d & 1) > 0) g = 128;

            x = pos % width;
            y = pos / width;
            setDrawColor(new Color(g, g, g));
            drawFilledRect(x * scale, y * scale - yy, scale, scale);
            pos++;
//            x = pos%width;
//            y = pos/width;
//            dc.setDrawColor(new Color(a*cs,a*cs,a*cs));
//            dc.drawFilledRect(x*scale,y*scale-yy,scale,scale);
//            pos++;
        }

        int size = stride;
        if (getBasicDisplay().getMouseButtonLeft()) stride = getMouseY();
        int[] extracted = extractSingleBitplaneWordRegion(data, getMouseX() * 16, size, size);
        renderBitplaneRegion(getDrawingContext(), extracted, 200, 200, size, size, 200);

        extracted = extractBitplaneSprite(data, getMouseX() * 16, 16, 32, 4, 16);
        renderBitplaneRegion(getDrawingContext(), extracted, 200, 100, 16, 32, 15);

        setDrawColor(Color.ORANGE);
        drawText("X:" + getMouseX(), 20, 20);
        drawText("Y:" + stride, 20, 40);
    }


    public byte safeGet(byte[] d, int i) {
        if (i < 0 || i >= d.length) return 0;
        return d[i];
    }

    public int[] extractSingleBitplaneWordRegion(byte[] data, int startIndex, int w, int h) {
        int byteCount = (w * h) / 8; // bytes per plane
        int writeHead = 0;

        int[] output = new int[w * h];

        for (int i = 0; i < byteCount; i += 2) {

            int pa = Byte.toUnsignedInt(safeGet(data, startIndex + i));
            int pb = Byte.toUnsignedInt(safeGet(data, startIndex + i + 1));
            int pixel = (pa << 8) | pb;

            // Scan bits in the byte.
            for (int bit = 0; bit < 16; bit++) {
                if (writeHead >= (w * h)) continue;
                if ((pixel & (1 << bit)) > 0) {
                    output[writeHead] = 1;
                }
                writeHead++;
            }
        }

        return output;
    }

    public int[] extractBitplaneSprite(byte[] data, int startIndex, int w, int h, int planes, int stride) {

        int[] output = new int[w * h];

        for (int row = 0; row < h; row++) {

            for (int plane = 0; plane < planes; plane++) {
                int index = startIndex + (plane * 2) + (row * stride);

                int pa = Byte.toUnsignedInt(safeGet(data, index));
                int pb = Byte.toUnsignedInt(safeGet(data, index + 1));
                int pixel = (pa << 8) | pb;

                // Scan bits in the byte.
                for (int bit = 0; bit < 16; bit++) {
                    boolean set = ((pixel & (1 << bit)) > 0);
                    if (!set) continue;

                    output[(row * 16) + bit] |= 1 << plane;
                }

            }
        }

        return output;
    }

    public void renderBitplaneRegion(DrawingContext dc, int[] sprite, int _x, int _y, int w, int h, int colorScale) {
        int scale = 3;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int p = sprite[x + (y * w)] * colorScale;
                if (p > 255) p = 255;
                dc.setDrawColor(new Color(p, p, p));
                dc.drawFilledRect(_x + (x * scale), _y + (y * scale), scale, scale);
            }
        }
    }


    public byte[] readFile(String file) throws IOException {
        DataInputStream reader = new DataInputStream(new FileInputStream(file));
        int nBytesToRead = reader.available();
        if (nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            reader.read(bytes);
            return bytes;
        }
        return new byte[0];
    }

}
