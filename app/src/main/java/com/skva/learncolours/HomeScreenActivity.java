package com.skva.learncolours;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeScreenActivity extends Activity {
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(HomeScreenActivity.this, IntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });
        t.start();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setGameButtonAttributes(R.id.new_fruits_game, 5, getString(R.string.new_fruits_game));
        setGameButtonAttributes(R.id.new_birds_game, 4, getString(R.string.new_birds_game));
        setGameButtonAttributes(R.id.new_animals_game, 3, getString(R.string.new_animals_game));
        setGameButtonAttributes(R.id.new_shapes_game, 2, getString(R.string.new_shapes_game));
        setGameButtonAttributes(R.id.new_numbers_game, 1, getString(R.string.new_numbers_game));
        setGameButtonAttributes(R.id.new_colours_game, 0, getString(R.string.new_colours_game));
        setEmailButtonAttributes(R.id.action_feedback);
        setExitButtonAttributes(R.id.exit_game);
    }

    private void setGameButtonAttributes(int button_id, final int TypeofGame, final String game_name) {
        Button button = (Button) findViewById(button_id);
        final Intent intent = new Intent(this, MainActivity.class);
        final Typeface font = Typeface.createFromAsset(getAssets(), "ComicRelief.ttf");
        button.setTypeface(font);
        button.setTransformationMethod(null);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", game_name);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, game_name);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, game_name);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                intent.putExtra("TypeofGame", TypeofGame);
                startActivity(intent);

            }
        });
    }

    private void setEmailButtonAttributes(int button_id) {
        Button button = (Button) findViewById(button_id);
        final Typeface font = Typeface.createFromAsset(getAssets(), "ComicRelief.ttf");
        button.setTypeface(font);
        button.setTransformationMethod(null);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Feedback");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Feedback");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Feedback");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kartik.narayanan@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Maths Play for Kids");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
    }

    private void setExitButtonAttributes(int button_id) {
        Button button = (Button) findViewById(button_id);
        final Typeface font = Typeface.createFromAsset(getAssets(), "ComicRelief.ttf");
        button.setTypeface(font);
        button.setTransformationMethod(null);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Exit");
                finish();
                moveTaskToBack(true);

            }
        });
    }



}
