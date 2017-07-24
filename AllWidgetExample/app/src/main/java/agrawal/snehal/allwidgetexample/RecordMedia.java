package agrawal.snehal.allwidgetexample;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class RecordMedia extends AppCompatActivity {
    MediaRecorder recorder;
    File audiofile = null;
    static final String TAG = "MediaRecording";
    Button startButton, stopButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordmedia);
        startButton = (Button) findViewById(R.id.button1);
        stopButton = (Button) findViewById(R.id.button2);
    }

    public void startRecording(View view) throws IOException {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        File dir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    public void stopRecording(View view) {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        recorder.stop();
        recorder.release();
        addRecordingToMediaLibrary();
    }

    protected void addRecordingToMediaLibrary() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
    }
}