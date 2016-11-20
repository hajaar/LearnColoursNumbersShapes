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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends FragmentActivity implements HomeScreenFragment.OnItemSelectedListener {
    TextToSpeech t1;

    private ImageButton backButton, feedbackButton, informationButton, notificationButton;
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


        backButton = (ImageButton) findViewById(R.id.goback);
        backButton.setImageResource(R.drawable.exit);
        //Glide.with(this).load(R.drawable.exit).fitCenter().into(backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moveBack();
            }
        });
        feedbackButton = (ImageButton) findViewById(R.id.feedback);
        feedbackButton.setImageResource(R.drawable.feedback);
        //Glide.with(this).load(R.drawable.feedback).fitCenter().into(feedbackButton);

        feedbackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kartik.narayanan@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Learn to Speak");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        notificationButton = (ImageButton) findViewById(R.id.notification);
        notificationButton.setImageResource(R.drawable.notification);
        //Glide.with(this).load(R.drawable.notification).fitCenter().into(notificationButton);

        notificationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        informationButton = (ImageButton) findViewById(R.id.information);
        informationButton.setImageResource(R.drawable.information);
        //Glide.with(this).load(R.drawable.information).fitCenter().into(informationButton);

        informationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }


    public void speakOut(String msg) {
        fullScreen();
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
        startSpeech();
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
        speakOut(getResources().getStringArray(R.array.games)[gametype]);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        int rootid = R.id.rootframe;
        int imageResource = R.drawable.home;

        if (isScreenSizeLarge) {
            rootid = R.id.rootframe2;
            imageResource = R.drawable.exit;

        }
        backButton.setImageResource(R.drawable.home);
        //Glide.with(this).load(imageResource).fitCenter().into(backButton);

        transaction.replace(rootid, newFragment, "GAME");
        transaction.addToBackStack("GAME");
            transaction.commit();

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

            backButton.setImageResource(R.drawable.exit);
            speakOut("Home");
            //Glide.with(this).load(R.drawable.exit).fitCenter().into(backButton);


        }

    }

    private void fullScreen() {

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

    private void startSpeech() {
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int available = t1.setLanguage(Locale.US);
                    if (available == TextToSpeech.LANG_MISSING_DATA || available == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getBaseContext(), "Please install Text To Speech from your settings menu", Toast.LENGTH_LONG).show();
                    }
                }
               /* switch (status) {
                    case TextToSpeech.ERROR_NOT_INSTALLED_YET :
                        Toast.makeText(getBaseContext(),"Please install Text To Speech from your settings menu",Toast.LENGTH_LONG).show();break;
                    case TextToSpeech.LANG_MISSING_DATA :
                        Toast.makeText(getBaseContext(),"Please install Text To Speech from your settings menu",Toast.LENGTH_LONG).show();break;
                }*/
            }
        });
    }



}
