package com.hammerox.rollingbal;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Mauricio on 24-Dec-16.
 */

public class Util {

    private final static Util INSTANCE;

    private static DecimalFormat decimalFormat;

    static {
        INSTANCE = new Util();
    }

    public static float removeImprecision(float number) {
        if (decimalFormat == null) decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#.####");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        return Float.valueOf(decimalFormat.format(number).replace(",", "."));
    }

    public static Util getInstance() {
        return INSTANCE;
    }
}
