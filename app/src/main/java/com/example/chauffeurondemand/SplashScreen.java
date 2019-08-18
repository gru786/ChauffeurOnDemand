package com.example.chauffeurondemand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        textToSpeech = new TextToSpeech(this,this);




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg= "Welcome to the Chauffer On Demand App";
                textToSpeech.speak(msg,TextToSpeech.QUEUE_FLUSH,null);


                Intent intent = new Intent(SplashScreen.this,MainActivity.class);

                startActivity(intent);
                SplashScreen.this.finish();// to destroy the activity so that it isn't visible on going back.
            }
        }, 5000);
    }
    public void onInit(int status)
    {
         textToSpeech.setLanguage(Locale.ENGLISH);

        if(status == TextToSpeech.LANG_NOT_SUPPORTED||status== TextToSpeech.LANG_MISSING_DATA)
        {
            Toast.makeText(this, "Not supported", Toast.LENGTH_SHORT).show();
        }

    }


}
