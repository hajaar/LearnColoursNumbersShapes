package com.skva.learncolours;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends FragmentActivity {

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





}
