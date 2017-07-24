package agrawal.snehal.allwidgetexample.filepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import agrawal.snehal.allwidgetexample.R;

public class PhotoSelectGallery extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiphotoselectactivity);
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        @SuppressLint("Recycle") Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
        ArrayList<String> imageUrls = new ArrayList<>();
        assert imagecursor != null;
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(columns[0]);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
        }
        ImageAdapter imageAdapter = new ImageAdapter(this, imageUrls);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);
    }

    public class ImageAdapter extends BaseAdapter {
        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray mSparseBooleanArray;

        ImageAdapter(Context context, ArrayList<String> imageList) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<>();
            mList = imageList;
        }

        ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                }
            }
            return mTempArry;
        }

        public int getCount() {
            return mList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_multiphotoselectactivity_single_item, null);
            }
            CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            Glide.with(getBaseContext()).load("file://" + mList.get(position)).into(imageView);
            mCheckBox.setTag(position);
            mCheckBox.setChecked(mSparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
            return convertView;
        }

        CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                // Toast.makeText(mContext, "" + mList.get((Integer) buttonView.getTag()), Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("filelink", mList.get((Integer) buttonView.getTag()));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
