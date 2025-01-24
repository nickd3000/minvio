package com.physmo.reference.experiments;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.VoronoiNoise;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.GuiPanel;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

/**
 * The FunctionExplorer class extends the MinvioApp framework to create
 * an interactive graphical tool for visualizing mathematical functions
 * and noise-based patterns, such as Mandelbrot and custom fractals.
 * The application allows users to pan and zoom into the graphical representation
 * of the functions, providing dynamic exploration capabilities.
 * <p>
 * Key Features:
 * - Interactive visual exploration through panning and zooming.
 * - Support for rendering mathematical functions and noise patterns.
 * - Efficient rendering control using dirty flag and fine-grained rendering.
 * - GUI integration for intuitive user interaction.
 */
public class FunctionExplorer extends MinvioApp {
    GuiContext guiContext;
    GuiPanel guiPanel;
    double scale = 0.010;
    BufferedImage bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
    boolean dirty = false;
    int renderRow = 0;

    double centerX = 0;
    double centerY = 0;
    double scrollX = 0;
    double scrollY = 0;

    boolean renderFine = false;

    public static void main(String... args) {
        MinvioApp app = new FunctionExplorer();
        app.start(600, 600, "Function Explorer", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        guiContext = new GuiContext(getBasicDisplay());

        guiPanel = new GuiPanel(new Rect(20, 20, 400, 400)) {
            boolean leftHeld = false;
            boolean rightHeld = false;

            int dragInitialX = 0;
            int dragInitialY = 0;

            @Override
            public void onMessage(GuiMessage guiMessage, Object object) {
                MouseMessageData mouseMessageData = (MouseMessageData) object;

                if (guiMessage == MOUSE_MOVE) {
                    if (rightHeld) {
                        scrollX = ((double) (dragInitialX - mouseMessageData.x)) * scale;
                        scrollY = ((double) (dragInitialY - mouseMessageData.y)) * scale;
                        setRenderDirty(true);
                    }
                }

                if (guiMessage == MOUSE_BUTTON_DOWN) {
                    if (mouseMessageData.button == 1) {
                        leftHeld = true;
                        dragInitialX = mouseMessageData.x;
                        dragInitialY = mouseMessageData.y;
                    } else if (mouseMessageData.button == 3) {
                        rightHeld = true;
                        dragInitialX = mouseMessageData.x;
                        dragInitialY = mouseMessageData.y;
                    } else if (mouseMessageData.button == 2) {
                        // middle
                        changeZoom(scale * 2);
                    }
                }

                if (guiMessage == MOUSE_BUTTON_UP) {
                    if (mouseMessageData.button == 3) {
                        rightHeld = false;
                        centerX += scrollX;
                        centerY += scrollY;
                        scrollX = 0;
                        scrollY = 0;
                    } else if (mouseMessageData.button == 1) {
                        leftHeld = false;

                        int dx = Math.abs(mouseMessageData.x - dragInitialX);
                        int dy = Math.abs(mouseMessageData.y - dragInitialY);
                        double v = (dx * scale) / 400.0;

                        double mx = ((mouseMessageData.x + dragInitialX) / 2.0 - 200) * scale;
                        double my = ((mouseMessageData.y + dragInitialY) / 2.0 - 200) * scale;

                        centerX += mx;
                        centerY += my;

                        changeZoom(v);
                        setRenderDirty(true);
                    }
                }
            }
        };

        guiContext.add(guiPanel);

        setRenderDirty(true);
    }

    @Override
    public void draw(double delta) {
        guiContext.tick();

        if (dirty) {
            render();
        }

        guiPanel.getDc().drawImage(bufferedImage, 0, 0);
        guiContext.drawAll(getDrawingContext());
    }

    public void changeZoom(double newZoom) {
        scale = newZoom;
        dirty = true;
    }

    public void setRenderDirty(boolean value) {
        if (value) {
            if (!dirty) renderRow = 0;
            dirty = true;
            renderFine = false;

        } else {
            renderRow = 0;
            dirty = false;
        }
    }

    public void render() {
        long startTime = System.nanoTime();

        var g = bufferedImage.getGraphics();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        int skip = renderFine ? 1 : 4;
        int endHeight = height - skip;

        double cx = centerX + scrollX - (200 * scale);
        double cy = centerY + scrollY - (200 * scale);

        for (int y = renderRow; y < height; y += skip) {
            System.out.println(y);
            for (int x = 0; x < width; x += skip) {
                double xx = x * scale;
                double yy = y * scale;
                //double noise = PerlinNoise.noise(cx + xx, cy + yy, 0);
                double noise = functionMandelbrot((float) (cx + xx), (float) (cy + yy));
                //double noise = voronioNoise((float) (cx + xx), (float) (cy + yy));
                //double noise = functionNickbrot((float) (cx + xx), (float) (cy + yy));
                int c = (int) ((noise) % 255) & 0xff;
                g.setColor(new Color(c, c, c));
                g.fillRect(x, y, skip, skip);

            }

            renderRow = y;

            if (y >= endHeight - 1) {
                if (renderFine) {
                    setRenderDirty(false);
                } else {
                    renderFine = true;
                    renderRow = 0;
                }
            }

            if (System.nanoTime() - startTime > (16670000) * 0.5) {
                return;
            }

        }
    }

    private int functionNickbrot(double x, double y) {
        int MAX_ITERATIONS = 50;
        double xx = 0.0;
        double yy = 0.0;
        int iter = 0;
        while (xx * xx + yy * yy <= 18.0 && iter < MAX_ITERATIONS) {
            double temp = Math.tan(xx * yy);
            yy += Math.sin(x + xx + temp) * 2;
            xx += Math.cos(y + yy + temp) * 2;
            iter++;
        }
        return iter;
    }

    private int functionMandelbrot(double x, double y) {
        int MAX_ITERATIONS = 1600;
        double xx = 0.0;
        double yy = 0.0;
        int iter = 0;
        while (xx * xx + yy * yy <= 4.0 && iter < MAX_ITERATIONS) {
            double temp = xx * xx - yy * yy + x;
            yy = 2.0 * xx * yy + y;
            xx = temp;
            iter++;
        }
        return iter;
    }

    private int voronioNoise(double x, double y) {
        return (int) (255 * VoronoiNoise.noise(x, y, 0.1));
    }
}
