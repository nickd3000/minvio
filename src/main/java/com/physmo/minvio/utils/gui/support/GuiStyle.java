package com.physmo.minvio.utils.gui.support;

import java.awt.Color;

/**
 * Supplies colors used by GUI controls and drawing helpers.
 */
public interface GuiStyle {
    /** @return background fill color */
    Color getBackgroundColor();

    /** @return button fill color */
    Color getButtonColor();

    /** @return light bevel edge color */
    Color getBevelLight();

    /** @return dark bevel edge color */
    Color getBevelDark();

    /** @return accent color for active or highlighted control elements */
    Color getAccent();

    /** @return text color */
    Color getTextColor();
}
