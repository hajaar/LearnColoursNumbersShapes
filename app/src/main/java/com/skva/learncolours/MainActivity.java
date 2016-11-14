package com.skva.learncolours;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final int COUNT = 10;
    private static final String TAG = "MainActivity";
    Animation animScale;
    TextToSpeech t1;
    String[] colorText = {"Red", "Blue", "Black", "White", "Yellow", "Green", "Purple", "Pink", "Orange", "Brown"};
    String[] colorHexCode = {"#FF0000", "#0000FF", "#000000", "#FFFFFF", "#FFFF00", "#008000", "#800080", "#ff69b4", "#FFA500", "#8B4513"};
    String[] numberText = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    String[] animalText = {"Cat", "Cow", "Deer", "Dog", "Goat", "Horse", "Kangaroo", "Lion", "Monkey", "Tiger"};
    int[] animalCode = {R.drawable.cat, R.drawable.cow, R.drawable.deer, R.drawable.dog, R.drawable.goat, R.drawable.horse, R.drawable.kangaroo, R.drawable.lion, R.drawable.monkey, R.drawable.tiger};
    String[] birdsText = {"Crow", "Flamingo", "Kingfisher", "Ostrich", "Owl", "Parrot", "Peacock", "Penguin", "Sparrow", "Swan"};
    int[] birdsCode = {R.drawable.crow, R.drawable.flamingo, R.drawable.kingfisher, R.drawable.ostrich, R.drawable.owl, R.drawable.parrot, R.drawable.peacock, R.drawable.penguin, R.drawable.sparrow, R.drawable.swan};
    String[] fruitsText = {"Apple", "brinjal", "cabbage", "carrot", "coconut", "grapes", "lemon", "okra", "orange", "tomato"};
    int[] fruitsCode = {R.drawable.apple, R.drawable.brinjal, R.drawable.cabbage, R.drawable.carrot, R.drawable.coconut, R.drawable.grapes, R.drawable.lemon, R.drawable.okra, R.drawable.orange, R.drawable.tomato};

    String[] shapeText = {"Rectangle", "Square", "Oval", "Circle", "Semi-circle", "Diamond", "Heart", "Arrow", "Triangle", "Star"};
    int[] shapeCode = {R.drawable.shape1, R.drawable.shape2, R.drawable.shape3, R.drawable.shape4, R.drawable.shape5, R.drawable.shape6, R.drawable.shape7, R.drawable.shape8, R.drawable.shape9, R.drawable.shape10};
    int[] numberCode = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] tmpRank = new int[COUNT];
    int[] buttonID = new int[COUNT];
    int[] layoutID = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10};
    Button[] colorButton = new Button[COUNT];
    ImageView[] imageView = new ImageView[COUNT];
    private SwipeRefreshLayout swipeContainer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int GAME_TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        GAME_TYPE = getIntent().getExtras().getInt("TypeofGame", 0);
        switch (GAME_TYPE) {
            case 0:
                this.setTitle(R.string.new_colours_game);
                t1.speak(getString(R.string.new_colours_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 1:
                this.setTitle(R.string.new_numbers_game);
                t1.speak(getString(R.string.new_numbers_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 2:
                this.setTitle(R.string.new_shapes_game);
                t1.speak(getString(R.string.new_shapes_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 3:
                this.setTitle(R.string.new_animals_game);
                t1.speak(getString(R.string.new_animals_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 4:
                this.setTitle(R.string.new_birds_game);
                t1.speak(getString(R.string.new_birds_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 5:
                this.setTitle(R.string.new_fruits_game);
                t1.speak(getString(R.string.new_fruits_game), TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        for (int i = 0; i < COUNT; i++) {
            LinearLayout ll = (LinearLayout) findViewById(layoutID[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            switch (GAME_TYPE) {
                case 0: {
                    colorButton[i] = new Button(this);
                    colorButton[i].setId(i);
                    ll.addView(colorButton[i], params);
                    colorButton[i].setOnClickListener(this);
                    break;
                }
                case 1: {
                    colorButton[i] = new Button(this);
                    colorButton[i].setId(i);
                    ll.addView(colorButton[i], params);
                    colorButton[i].setOnClickListener(this);
                    break;
                }
                case 2: {
                    imageView[i] = new ImageView(this);
                    imageView[i].setId(i);
                    ll.addView(imageView[i], params);
                    imageView[i].setOnClickListener(this);
                    break;
                }
                case 3: {
                    imageView[i] = new ImageView(this);
                    imageView[i].setId(i);
                    ll.addView(imageView[i], params);
                    imageView[i].setOnClickListener(this);
                    break;
                }
                case 4: {
                    imageView[i] = new ImageView(this);
                    imageView[i].setId(i);
                    ll.addView(imageView[i], params);
                    imageView[i].setOnClickListener(this);
                    break;
                }
                case 5: {
                    imageView[i] = new ImageView(this);
                    imageView[i].setId(i);
                    ll.addView(imageView[i], params);
                    imageView[i].setOnClickListener(this);
                    break;
                }
            }
            buttonID[i] = i;
        }


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffleColors();
                swipeContainer.setRefreshing(false);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Refresh");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Refresh");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Swipe");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        });

        shuffleColors();


    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void onClick(View v) {
        int i = v.getId();
        v.startAnimation(animScale);
        int clickid = 0;
        for (int j = 0; j < COUNT; j++) {
            if (buttonID[j] == i) {
                clickid = j;
                break;
            }
        }
        int pos = 0;

        switch (layoutID[clickid]) {
            case R.id.b1:
                pos = 0;
                break;
            case R.id.b2:
                pos = 1;
                break;
            case R.id.b3:
                pos = 2;
                break;
            case R.id.b4:
                pos = 3;
                break;
            case R.id.b5:
                pos = 4;
                break;
            case R.id.b6:
                pos = 5;
                break;
            case R.id.b7:
                pos = 6;
                break;
            case R.id.b8:
                pos = 7;
                break;
            case R.id.b9:
                pos = 8;
                break;
            case R.id.b10:
                pos = 9;
                break;
        }

        switch (GAME_TYPE) {
            case 0:
                t1.speak(colorText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 1:
                t1.speak(numberText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 2:
                t1.speak(shapeText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 3:
                t1.speak(animalText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 4:
                t1.speak(birdsText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 5:
                t1.speak(fruitsText[tmpRank[pos]], TextToSpeech.QUEUE_FLUSH, null);
                break;
        }

    }


    private void setRandomizedArray() {
        for (int i = 0; i < COUNT; i++) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) {
                tmpRank[i] = tmpRank[j];
            }
            tmpRank[j] = colorRank[i];
        }
    }

    private void shuffleColors() {
        setRandomizedArray();
        for (int i = 0; i < COUNT; i++) {


            switch (GAME_TYPE) {
                case 0: {
                    colorButton[i].setBackgroundResource(R.drawable.customborder1);
                    GradientDrawable drawable = (GradientDrawable) colorButton[i].getBackground();
                    drawable.setColor(Color.parseColor(colorHexCode[tmpRank[i]]));
                    colorButton[i].setText(colorText[tmpRank[i]]);
                    colorButton[i].setTextAppearance(this, android.R.style.TextAppearance_Medium);
                    break;
                }
                case 1: {
                    colorButton[i].setBackgroundResource(R.drawable.skipcustomborder);
                    colorButton[i].setText("" + numberCode[tmpRank[i]]);
                    colorButton[i].setTextAppearance(this, android.R.style.TextAppearance_Large);
                    break;
                }
                case 2: {
                    imageView[i].setImageResource(shapeCode[tmpRank[i]]);
                    imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    break;

                }
                case 3: {
                    imageView[i].setImageResource(animalCode[tmpRank[i]]);
                    imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    break;

                }
                case 4: {
                    imageView[i].setImageResource(birdsCode[tmpRank[i]]);
                    imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    break;

                }
                case 5: {
                    imageView[i].setImageResource(fruitsCode[tmpRank[i]]);
                    imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    break;

                }
            }
        }
    }

}
