package agrawal.snehal.allwidgetexample.imagezoom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ImageZoomViewPager extends ViewPager {
    public ImageZoomViewPager(Context context) {
        super(context);
    }

    public ImageZoomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if ((v instanceof TouchImageView)) {
            return ((TouchImageView) v).canScrollHorizontallyFroyo(-dx);
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
