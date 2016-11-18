package com.skva.learncolours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;


public class MainActivity extends FragmentActivity implements HomeScreenFragment.OnItemSelectedListener, SimpleGestureFilter.SimpleGestureListener {
    TextToSpeech t1;
    private SimpleGestureFilter detector;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Button button1, button2;
    private Boolean shouldIspeak = true;
    private Switch speech_switch;
    private SharedPreferences getPrefs;
    private Boolean isScreenSizeLarge = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fullScreen();
        if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            isScreenSizeLarge = true;
        }

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

        checkforSpeech();



        button1 = (Button) findViewById(R.id.back);
        button1.setText("Exit");
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveBack();
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
        shouldIspeak = getPrefs.getBoolean("shouldIspeak", true);
        if (shouldIspeak) {
            t1.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
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
        fullScreen();

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

        switch (gametype) {
            case 0:
                speakOut(getString(R.string.new_colours_game));
                break;
            case 1:
                speakOut(getString(R.string.new_numbers_game));
                break;
            case 2:
                speakOut(getString(R.string.new_shapes_game));
                break;
            case 3:
                speakOut(getString(R.string.new_animals_game));
                break;
            case 4:
                speakOut(getString(R.string.new_birds_game));
                break;
            case 5:
                speakOut(getString(R.string.new_fruits_game));
                break;
            case 6:
                speakOut(getString(R.string.new_underwater_game));
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        int rootid = R.id.rootframe;
        String msg = "Home";
        if (isScreenSizeLarge) {
            rootid = R.id.rootframe2;
            msg = "Exit";
        }
        button1.setText(msg);

        transaction.replace(rootid, newFragment, "GAME");
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
                //moveBack();
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

    private void moveBack() {
        if (isScreenSizeLarge || getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            speakOut("Bye Bye");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakOut("Bye, Bye!");
                    finishAndRemoveTask();
                }
            }, 1000);
        } else {

            getSupportFragmentManager().popBackStack();
            button1.setText("Exit");
            speakOut("Home");


        }
    }

    private void fullScreen() {
        detector = new SimpleGestureFilter(this, this);
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    private void checkforSpeech() {
        getPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        shouldIspeak = getPrefs.getBoolean("shouldIspeak", true);
        speech_switch = (Switch) findViewById(R.id.speech_switch);
        speech_switch.setChecked(shouldIspeak);
        speech_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor e = getPrefs.edit();
                shouldIspeak = speech_switch.isChecked();
                e.putBoolean("shouldIspeak", shouldIspeak);
                e.apply();
            }
        });
    }
}
