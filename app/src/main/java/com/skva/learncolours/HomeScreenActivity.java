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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;

public class HomeScreenActivity extends Activity implements View.OnClickListener {
    final static int COUNT = 6;
    ImageView[] imageView = new ImageView[COUNT];
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



        HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put(getString(R.string.new_colours_game), R.drawable.green);
        file_maps.put(getString(R.string.new_numbers_game), R.drawable.number1);
        file_maps.put(getString(R.string.new_shapes_game), R.drawable.shape1);
        file_maps.put(getString(R.string.new_animals_game), R.drawable.cat);
        file_maps.put(getString(R.string.new_birds_game), R.drawable.parrot);
        file_maps.put(getString(R.string.new_fruits_game), R.drawable.tomato);
        String gametype = "TypeOfGame";
        int TypeofGame = 0;
        if (gametype == getString(R.string.new_colours_game)) TypeofGame = 0;
        if (gametype == getString(R.string.new_numbers_game)) TypeofGame = 1;
        if (gametype == getString(R.string.new_shapes_game)) TypeofGame = 2;
        if (gametype == getString(R.string.new_animals_game)) TypeofGame = 3;
        if (gametype == getString(R.string.new_birds_game)) TypeofGame = 4;
        if (gametype == getString(R.string.new_fruits_game)) TypeofGame = 5;

        LinearLayout ll = (LinearLayout) findViewById(R.id.home_list);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < COUNT; i++) {
            imageView[i] = new ImageView(this);
            imageView[i].setId(i);
            ll.addView(imageView[i], params);
            imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView[i].setOnClickListener(this);
            int imageResource = R.drawable.blue;
            Glide.with(this).load(imageResource).into(imageView[i]);

        }


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


    public void onClick(View v) {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TypeofGame", v.getId());
        startActivity(intent);

    }


}
