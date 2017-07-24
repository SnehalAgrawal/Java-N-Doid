package agrawal.snehal.allwidgetexample;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeechActivty extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttospeech);
        try {
            tts = new TextToSpeech(this, this);
            btnSpeak = (Button) findViewById(R.id.btnSpeak);
            txtText = (EditText) findViewById(R.id.txtText);
            btnSpeak.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    speakOut();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {
        String text = txtText.getText().toString();
        tts.setSpeechRate(1.0f);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}