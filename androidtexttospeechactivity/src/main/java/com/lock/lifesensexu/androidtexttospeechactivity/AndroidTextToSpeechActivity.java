package com.lock.lifesensexu.androidtexttospeechactivity;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class AndroidTextToSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private Context mContext = AndroidTextToSpeechActivity.this;
    private EditText input_content;
    private Button speech;
    private TextToSpeech ttf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_text_to_speech);
        input_content = (EditText) findViewById(R.id.input_content);
        speech = ((Button) findViewById(R.id.speech));
        ttf = new TextToSpeech(mContext, this);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               speakOut();
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = ttf.setLanguage(Locale.CHINESE);
            ttf.setSpeechRate(0.5f);
            ttf.setPitch(1.6f);
            ttf.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(mContext, "This Language is not supported", Toast.LENGTH_SHORT);
            } else {
              //
                speakOut();
            }
        }else {
            Toast.makeText(mContext, "init failed", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttf != null) {
            ttf.stop();
            ttf.shutdown();
        }
    }
    private void speakOut() {
        String s = input_content.getText().toString();
        if (s != null) {
            ttf.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}
