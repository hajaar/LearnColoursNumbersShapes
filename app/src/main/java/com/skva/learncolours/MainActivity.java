package com.skva.learncolours;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final int COUNT = 10;
    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout swipeContainer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int GAME_TYPE = 0;
    Animation animScale;
    TextToSpeech t1;
    String[] colorText = {"Red", "Blue", "Black", "White", "Yellow", "Green", "Purple", "Pink", "Orange", "Brown"};
    String[] colorHexCode = {"#FF0000", "#0000FF", "#000000", "#FFFFFF", "#FFFF00", "#008000", "#800080", "#ff69b4", "#FFA500", "#8B4513"};
    String[] numberText = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    int[] numberCode = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] tmpRank = new int[COUNT];
    int[] buttonID = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10};
    Button[] colorButton = new Button[COUNT];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
        GAME_TYPE = getIntent().getExtras().getInt("TypeofGame", 0);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffleColors();
                swipeContainer.setRefreshing(false);

            }
        });

        shuffleColors();


    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void onClick(View v) {
        int i = v.getId();
        v.startAnimation(animScale);
        Log.d(TAG, "onClick: game type" + GAME_TYPE);

        switch (i) {
            case R.id.b1:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[0]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[0]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b2:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[1]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[1]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b3:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[2]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[2]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b4:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[3]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[3]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b5:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[4]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[4]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b6:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[5]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[5]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b7:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[6]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[6]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b8:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[7]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[7]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b9:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[8]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[8]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.b10:
                if (GAME_TYPE == 0) t1.speak(colorText[tmpRank[9]], TextToSpeech.QUEUE_FLUSH, null);
                else t1.speak(numberText[tmpRank[9]], TextToSpeech.QUEUE_FLUSH, null);
                break;


        }

    }


    private void setRandomizedArray() {
        for (int i = 0; i < COUNT; i++) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) {
                tmpRank[i] = tmpRank[j];
            }
            tmpRank[j] = colorRank[i];
        }
    }

    private void shuffleColors() {
        Log.d(TAG, "shuffleColors: game type" + GAME_TYPE);
        setRandomizedArray();
        for (int i = 0; i < COUNT; i++) {
            colorButton[i] = (Button) findViewById(buttonID[i]);
            colorButton[i].setOnClickListener(this);
            switch (GAME_TYPE) {
                case 0: {
                    colorButton[i].setBackgroundResource(R.drawable.customborder1);
                    GradientDrawable drawable = (GradientDrawable) colorButton[i].getBackground();
                    drawable.setColor(Color.parseColor(colorHexCode[tmpRank[i]]));
                    colorButton[i].setText(colorText[tmpRank[i]]);
                    colorButton[i].setTextAppearance(this, android.R.style.TextAppearance_Medium);
                    break;
                }
                case 1: {
                    colorButton[i].setBackgroundResource(R.drawable.skipcustomborder);
                    colorButton[i].setText("" + numberCode[tmpRank[i]]);
                    colorButton[i].setTextAppearance(this, android.R.style.TextAppearance_Large);
                    break;
                }

            }
            colorButton[i].setElevation(18);
            colorButton[i].setTranslationZ(12);

        }
    }


}
