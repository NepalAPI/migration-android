package com.yipl.nrna.util;

import android.content.res.ColorStateList;

/**
 * Created by julian on 12/17/15.
 */
public class Utils {
    public static ColorStateList getIconColorTint(int color, int checkedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        checkedColor,
                        color
                }
        );
    }
}
