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


        LinearLayout ll = (LinearLayout) findViewById(R.id.home_list);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(8, 8, 0, 8);
        for (int i = 0; i < COUNT; i++) {

            imageView[i] = new ImageView(this);
            imageView[i].setId(i);
            ll.addView(imageView[i], params);
            imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView[i].setPadding(4, 4, 0, 4);

            imageView[i].setOnClickListener(this);

            Glide.with(this).load(getResources().getIdentifier("launcher" + i, "drawable", this.getPackageName())).into(imageView[i]);

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
