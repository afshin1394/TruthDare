package com.afshin.truthordare.Utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class CustomViewUtils {
    public static int generateRandomColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(230), rnd.nextInt(230), rnd.nextInt(230));

        if (colors.contains(color)) {
            return generateRandomColor();
        } else {
            colors.add(color);
            return color;
        }
    }
}
