package agrawal.snehal.searchview.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class Util {
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }
}
