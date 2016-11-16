package com.skva.learncolours;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;


public class MainActivity extends FragmentActivity {

    TextToSpeech t1;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int GAME_TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        GAME_TYPE = getIntent().getExtras().getInt("TypeofGame", 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.gameframe, GameFragment.newInstance(GAME_TYPE), "Game" + GAME_TYPE)
                    .commit();
        }


    }


    public void speakOut(String msg) {
        t1.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onPause() {

        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    public void onDestroy() {

        t1.shutdown();
        super.onDestroy();
    }
}
