package agrawal.snehal.allwidgetexample;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class AudioStreamingActivity extends AppCompatActivity implements OnClickListener, OnTouchListener, OnCompletionListener, OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {
    private ImageButton buttonPlayPause;
    private SeekBar seekBarProgress;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds;
    private final Handler handler = new Handler();
    boolean firsttime = true;
    ProgressBar pbuffering;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiostreaming);
        initView();
    }

    private void initView() {
        pbuffering = (ProgressBar) findViewById(R.id.pbuffering);
        buttonPlayPause = (ImageButton) findViewById(R.id.bplaypause);
        buttonPlayPause.setOnClickListener(this);
        seekBarProgress = (SeekBar) findViewById(R.id.sbprogress);
        seekBarProgress.setMax(99); // It means 100% .0-99
        seekBarProgress.setOnTouchListener(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    private void primarySeekBarProgressUpdater() {
        seekBarProgress.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bplaypause) {
            if (firsttime) {
                pbuffering.setVisibility(View.VISIBLE);
                try {
                    mediaPlayer.setDataSource("http://cloud3.raag.me/Single%20Hindi/Jeene%20Laga%20Hoon-(Sachin%20Jigar)/Jeene%20Laga%20Hoon[48]::Raag.Me::.mp3".toString());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                firsttime = false;
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                buttonPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
            } else {
                mediaPlayer.pause();
                buttonPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
            primarySeekBarProgressUpdater();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.sbprogress) {
            if (mediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        buttonPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBarProgress.setSecondaryProgress(percent);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        pbuffering.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        finish();
    }
}