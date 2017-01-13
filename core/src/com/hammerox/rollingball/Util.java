package com.hammerox.rollingball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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

    public static BitmapFont generateFont(String fontPath, int size) {
        FileHandle fontFile = Gdx.files.internal(fontPath);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    public static Util getInstance() {
        return INSTANCE;
    }
}
