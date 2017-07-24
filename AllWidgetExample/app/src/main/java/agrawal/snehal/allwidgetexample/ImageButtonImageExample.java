package agrawal.snehal.allwidgetexample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class ImageButtonImageExample extends AppCompatActivity {
    ImageView tvex;
    LinearLayout llback;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagebutton_image_example);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvex = (ImageView) findViewById(R.id.tvex);
        llback = (LinearLayout) findViewById(R.id.llback);
        VideoView videoView = (VideoView) findViewById(R.id.videoView1);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/1.mp4");
        Log.d("Videopath", "onCreate: " + uri);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


        choice = 1;
        tvex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (choice == 1) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.violet));
                    choice++;
                } else if (choice == 2) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.indigo));
                    choice++;
                } else if (choice == 3) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.blue));
                    choice++;
                } else if (choice == 4) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.green));
                    choice++;
                } else if (choice == 5) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.yellow));
                    choice++;
                } else if (choice == 6) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.orange));
                    choice++;
                } else if (choice == 7) {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.red));
                    choice++;
                } else {
                    llback.setBackgroundColor(ContextCompat.getColor(ImageButtonImageExample.this, R.color.black));
                    choice = 1;
                }
            }
        });
    }
}
