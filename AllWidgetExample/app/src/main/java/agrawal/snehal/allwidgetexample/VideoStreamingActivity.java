package agrawal.snehal.allwidgetexample;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoStreamingActivity extends AppCompatActivity {
    VideoView videoview;
    ProgressBar progress;
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videostreaming);
        videoview = (VideoView) findViewById(R.id.VideoView);
        progress = (ProgressBar) findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        try {
            MediaController mediacontroller = new MediaController(VideoStreamingActivity.this);
            mediacontroller.setAnchorView(videoview);
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                progress.setVisibility(View.GONE);
                videoview.start();
            }
        });
    }
}