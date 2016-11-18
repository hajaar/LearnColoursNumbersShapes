package com.skva.learncolours;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;


public class MainActivity extends FragmentActivity implements HomeScreenFragment.OnItemSelectedListener, SimpleGestureFilter.SimpleGestureListener {
    TextToSpeech t1;
    private SimpleGestureFilter detector;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new SimpleGestureFilter(this, this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (savedInstanceState != null) {
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getSupportFragmentManager().
                    findFragmentById(R.id.rootframe);
            if (fragmentById != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }
        HomeScreenFragment homeScreenFragment = new HomeScreenFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootframe, homeScreenFragment, "HOME").addToBackStack("HOME").commit();

        button1 = (Button) findViewById(R.id.back);
        button1.setText("Exit");
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                    button1.setText("Exit");
                } else {
                    finishAndRemoveTask();
                }
            }
        });
        button2 = (Button) findViewById(R.id.feedback);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kartik.narayanan@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Learn to Speak");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

    }


    public void speakOut(String msg) {
        Log.d("Main", msg);
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

    @Override
    public void onGameSelected(Integer gametype) {
        Log.d("Main", "" + gametype);

        GameFragment newFragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt("GAME_TYPE", gametype);
        newFragment.setArguments(args);
        button1.setText("Home");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.rootframe, newFragment, "GAME");
        transaction.addToBackStack("GAME");
            transaction.commit();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT:
                str = "Swipe Right";
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP:
                str = "Swipe Up";
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }
}
