package com.skva.learncolours;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.


        /*addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);*/

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Learn to Identify", "Welcome! We built this game to help you teach your toddler new words ", R.drawable.applauncher, Color.parseColor("#0f1b07")));
        addSlide(AppIntroFragment.newInstance("How To Use", "Touch the categories to show their associated objects", R.drawable.help, Color.parseColor("#0f1b07")));
        addSlide(AppIntroFragment.newInstance("How To Use", "Learn Mode: Touch objects to hear their names spoken out." + '\n' + " Play mode: Turn speech off and ask your child to choose objects.", R.drawable.help, Color.parseColor("#0f1b07")));

        addSlide(AppIntroFragment.newInstance("Things to Know", "This game uses Google's Text to Speech engine. This might take upto 10 seconds to get ready. We appreciate your patience.", R.drawable.applauncher, Color.parseColor("#0f1b07")));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#00C853"));
        setSeparatorColor(Color.parseColor("#4cb5f5"));

        // Hide Skip/Done button.
        showSkipButton(true);

        setProgressButtonEnabled(true);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override

    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}