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

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;

public class HomeScreenActivity extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private SliderLayout mDemoSlider;


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
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);


        HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put(getString(R.string.new_colours_game), R.drawable.green);
        file_maps.put(getString(R.string.new_numbers_game), R.drawable.number1);
        file_maps.put(getString(R.string.new_shapes_game), R.drawable.shape1);
        file_maps.put(getString(R.string.new_animals_game), R.drawable.cat);
        file_maps.put(getString(R.string.new_birds_game), R.drawable.parrot);
        file_maps.put(getString(R.string.new_fruits_game), R.drawable.tomato);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("TypeOfGame", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.CubeIn);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


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

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String gametype = slider.getBundle().getString("TypeOfGame");
        int TypeofGame = 0;
        if (gametype == getString(R.string.new_colours_game)) TypeofGame = 0;
        if (gametype == getString(R.string.new_numbers_game)) TypeofGame = 1;
        if (gametype == getString(R.string.new_shapes_game)) TypeofGame = 2;
        if (gametype == getString(R.string.new_animals_game)) TypeofGame = 3;
        if (gametype == getString(R.string.new_birds_game)) TypeofGame = 4;
        if (gametype == getString(R.string.new_fruits_game)) TypeofGame = 5;
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TypeofGame", TypeofGame);
        startActivity(intent);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }


}
