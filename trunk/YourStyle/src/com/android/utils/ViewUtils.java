package com.android.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ViewUtils {

    public static float dpToPx(Context paramContext, int paramInt) {
        DisplayMetrics localDisplayMetrics = paramContext.getResources()
                .getDisplayMetrics();
        float f = paramInt;
        return TypedValue.applyDimension(1, f, localDisplayMetrics);
    }

    public static int getScreenDensity(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeightPixels(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidthPixels(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean isHdpi(Context paramContext) {
        if (getScreenDensity(paramContext) == 240) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLdpi(Context paramContext) {
        if (getScreenDensity(paramContext) == 120) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMdpi(Context paramContext) {
        if (getScreenDensity(paramContext) == 160) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isXhdpi(Context paramContext) {
        if (getScreenDensity(paramContext) == 320) {
            return true;
        } else {
            return false;
        }
    }
}
