package agrawal.snehal.allwidgetexample.imagezoom;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import agrawal.snehal.allwidgetexample.R;

public class ImageZoomViewPagerActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom_viewpager);
        ImageZoomViewPager mViewPager = (ImageZoomViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());
    }

    private static class TouchImageAdapter extends PagerAdapter {
        private static int[] images = {R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop, R.drawable.marshmellow, R.drawable.nougat};

        public int getCount() {
            return images.length;
        }

        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
            img.setImageResource(images[position]);
            container.addView(img, -1, -1);
            return img;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
