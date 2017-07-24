package agrawal.snehal.allwidgetexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

class Utils {

    static boolean isSameDomain(String url, String url1) {
        return getRootDomainUrl(url.toLowerCase()).equals(getRootDomainUrl(url1.toLowerCase()));
    }

    private static String getRootDomainUrl(String url) {
        String[] domainKeys = url.split("/")[2].split("\\.");
        int length = domainKeys.length;
        int dummy = domainKeys[0].equals("www") ? 1 : 0;
        if (length - dummy == 2)
            return domainKeys[length - 2] + "." + domainKeys[length - 1];
        else {
            if (domainKeys[length - 1].length() == 2) {
                return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
            } else {
                return domainKeys[length - 2] + "." + domainKeys[length - 1];
            }
        }
    }

    static void tintMenuIcon(Context context, MenuItem item, int color) {
        Drawable drawable = item.getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    static void bookmarkUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("kepi", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        if (pref.getBoolean(url, false)) {
            editor.putBoolean(url, false);
        } else {
            editor.putBoolean(url, true);
        }
        editor.apply();
    }

    static boolean isBookmarked(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("kepi", 0);
        return pref.getBoolean(url, false);
    }
}
