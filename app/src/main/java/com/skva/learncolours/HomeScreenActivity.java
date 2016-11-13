package com.skva.learncolours;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class HomeScreenActivity extends Activity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        final Intent coloursintent = new Intent(this, MainActivity.class);
        final Intent numbersintent = new Intent(this, MainActivity.class);


        final Button button1 = (Button) findViewById(R.id.new_colours_game);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Colours Game");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Colours");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Colours");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                coloursintent.putExtra("TypeofGame", 0);
                startActivity(coloursintent);
            }
        });

        final Button button3 = (Button) findViewById(R.id.exit_game);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Exit");
                finish();
                moveTaskToBack(true);
            }
        });

        final Button button4 = (Button) findViewById(R.id.action_feedback);
        button4.setOnClickListener(new View.OnClickListener() {
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
        final Button button5 = (Button) findViewById(R.id.new_numbers_game);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ButtonClick", "Patterns Game");
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Numbers");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Numbers");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                numbersintent.putExtra("TypeofGame", 1);
                startActivity(numbersintent);
            }
        });

    }


}
