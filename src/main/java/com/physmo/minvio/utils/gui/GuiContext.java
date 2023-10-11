package com.physmo.minvio.utils.gui;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.PointInt;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiStyle;
import com.physmo.minvio.utils.gui.support.MouseConnector;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuiContext {
    List<GuiContainer> containers;
    MouseConnector mouseConnector;
    //    Map<GuiContainer, Rect> map;
    //List<GuiContainer> allChildren = new ArrayList<>();
    Collection<GuiContainer> allChildren = Collections.synchronizedCollection(new ArrayList<>());

    BasicDisplay basicDisplay;
    List<GuiContainer> buttonDownList = new ArrayList<>();
    private GuiStyle guiStyle;

    public GuiContext(BasicDisplay basicDisplay) {
        this.basicDisplay = basicDisplay;
        containers = new ArrayList<>();
        initMouseConnector();
        basicDisplay.addMouseConnector(mouseConnector);
        //map = new HashMap<>();
        guiStyle = new GuiStyle() {
            @Override
            public Color getBackgroundColor() {
                return new Color(189, 189, 189, 255);
            }

            @Override
            public Color getBevelLight() {
                return new Color(231, 231, 231, 255);
            }

            @Override
            public Color getBevelDark() {
                return new Color(143, 143, 143, 255);
            }

            @Override
            public Color getAccent() {
                return new Color(40, 40, 213, 255);
            }
        };
    }

    public GuiStyle getGuiStyle() {
        return guiStyle;
    }

    public void setGuiStyle(GuiStyle guiStyle) {
        this.guiStyle = guiStyle;
    }

    public void tick() {
        locateAll();
        for (GuiContainer guiContainer : allChildren) {
            guiContainer.drawIfDirty(this);
        }
    }

    public void add(GuiContainer container) {
        containers.add(container);
    }

    public void initMouseConnector() {
        mouseConnector = new MouseConnector() {
            @Override
            public void onMouseMoved(int x, int y) {
                synchronized (allChildren) {
                    for (GuiContainer guiContainer : allChildren) {

                        PointInt p = guiContainer.getInheritedPosition();
                        guiContainer.onMessage(GuiMessage.MOUSE_MOVE, new MouseMessageData(x - p.x, y - p.y, 0));

                    }
                }
            }

            @Override
            public void onButtonDown(int x, int y, int buttonId) {

                buttonDownList.clear();
                for (GuiContainer guiContainer : getListOfContainersAtPoint(x, y)) {
                    PointInt p = guiContainer.getInheritedPosition();
                    guiContainer.onMessage(GuiMessage.MOUSE_BUTTON_DOWN, new MouseMessageData(x - p.x, y - p.y, buttonId));
                    buttonDownList.add(guiContainer);
                }

            }

            @Override
            public void onButtonUp(int x, int y, int buttonId) {

                for (GuiContainer guiContainer : buttonDownList) {
                    PointInt p = guiContainer.getInheritedPosition();
                    guiContainer.onMessage(GuiMessage.MOUSE_BUTTON_UP, new MouseMessageData(x - p.x, y - p.y, buttonId));
                }

            }
        };
    }


    public void drawAll(DrawingContext topLevelContext) {
        locateAll();
        for (GuiContainer container : containers) {
            container.recursiveDraw(this, topLevelContext, 0, 0);
        }

    }

    public void locateAll() {
        allChildren.clear();
        for (GuiContainer container : containers) {
            container.recursiveLocate(allChildren);
        }
    }

    public List<GuiContainer> getListOfContainersAtPoint(int x, int y) {
        List<GuiContainer> list = new ArrayList<>();
        for (GuiContainer guiContainer : allChildren) {
            PointInt ip = guiContainer.getInheritedPosition();
            if (x < ip.x || y < ip.y) continue;
            if (x > ip.x + guiContainer.getRect().w) continue;
            if (y > ip.y + guiContainer.getRect().h) continue;

            list.add(guiContainer);

        }
        return list;
    }

}
