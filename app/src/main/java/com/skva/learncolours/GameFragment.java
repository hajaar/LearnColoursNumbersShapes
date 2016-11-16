package com.skva.learncolours;


import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    private static final int COUNT = 10;
    private static final String TAG = "GameFragment";
    int GAME_TYPE = 0;
    Animation animScale;
    TextToSpeech t1;
    String[] colorText = {"Black", "Blue", "Brown", "Gray", "Green", "Pink", "Purple", "Red", "White", "Yellow"};
    int[] colorCode = {R.drawable.black, R.drawable.blue, R.drawable.brown, R.drawable.gray, R.drawable.green, R.drawable.pink, R.drawable.purple, R.drawable.red, R.drawable.white, R.drawable.yellow};

    String[] numberText = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    String[] animalText = {"Cat", "Cow", "Deer", "Dog", "Goat", "Horse", "Kangaroo", "Lion", "Monkey", "Tiger"};
    int[] animalCode = {R.drawable.cat, R.drawable.cow, R.drawable.deer, R.drawable.dog, R.drawable.goat, R.drawable.horse, R.drawable.kangaroo, R.drawable.lion, R.drawable.monkey, R.drawable.tiger};
    String[] birdsText = {"Crow", "Flamingo", "Kingfisher", "Ostrich", "Owl", "Parrot", "Peacock", "Penguin", "Sparrow", "Swan"};
    int[] birdsCode = {R.drawable.crow, R.drawable.flamingo, R.drawable.kingfisher, R.drawable.ostrich, R.drawable.owl, R.drawable.parrot, R.drawable.peacock, R.drawable.penguin, R.drawable.sparrow, R.drawable.swan};
    String[] fruitsText = {"Apple", "brinjal", "cabbage", "carrot", "coconut", "grapes", "lemon", "okra", "orange", "tomato"};
    int[] fruitsCode = {R.drawable.apple, R.drawable.brinjal, R.drawable.cabbage, R.drawable.carrot, R.drawable.coconut, R.drawable.grapes, R.drawable.lemon, R.drawable.okra, R.drawable.orange, R.drawable.tomato};

    String[] shapeText = {"Rectangle", "Square", "Oval", "Circle", "Semi-circle", "Diamond", "Heart", "Arrow", "Triangle", "Star"};
    int[] shapeCode = {R.drawable.shape1, R.drawable.shape2, R.drawable.shape3, R.drawable.shape4, R.drawable.shape5, R.drawable.shape6, R.drawable.shape7, R.drawable.shape8, R.drawable.shape9, R.drawable.shape10};
    int[] numberCode = {R.drawable.number1, R.drawable.number2, R.drawable.number3, R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7, R.drawable.number8, R.drawable.number9, R.drawable.number10};
    int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] tmpRank = new int[COUNT];
    int[] buttonID = new int[COUNT];
    int[] layoutID = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10};
    ImageView[] imageView = new ImageView[COUNT];
    LinearLayout layout, gamelayout;
    private SwipeRefreshLayout swipeContainer;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(Integer game_type) {

        GameFragment f = new GameFragment();
        Bundle b = new Bundle();
        b.putInt("GAME_TYPE", game_type);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout = (LinearLayout) getActivity().findViewById(R.id.progressbar_view);
        gamelayout = (LinearLayout) getActivity().findViewById(R.id.game_layout);
        new Task().execute();
        animScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale);
        for (int i = 0; i < COUNT; i++) {
            LinearLayout ll = (LinearLayout) getActivity().findViewById(layoutID[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            imageView[i] = new ImageView(getContext());
            imageView[i].setId(i);
            ll.addView(imageView[i], params);
            imageView[i].setOnClickListener(this);
            buttonID[i] = i;


        }
        switch (GAME_TYPE) {
            case 0:
                getActivity().setTitle(R.string.new_colours_game);
                break;
            case 1:
                getActivity().setTitle(R.string.new_numbers_game);
                break;
            case 2:
                getActivity().setTitle(R.string.new_shapes_game);
                break;
            case 3:
                getActivity().setTitle(R.string.new_animals_game);
                break;
            case 4:
                getActivity().setTitle(R.string.new_birds_game);
                break;
            case 5:
                getActivity().setTitle(R.string.new_fruits_game);
                break;
        }

        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuffleColors();
                swipeContainer.setRefreshing(false);

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
    public void onResume() {
        super.onResume();
        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    public void onDestroy() {

        t1.shutdown();
        super.onDestroy();
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
        GAME_TYPE = getArguments().getInt("GAME_TYPE");
        int imageResource = 0;
        for (int i = 0; i < COUNT; i++) {

            imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            switch (GAME_TYPE) {
                case 0: {
                    imageResource = (colorCode[tmpRank[i]]);

                    break;
                }
                case 1: {
                    imageResource = (numberCode[tmpRank[i]]);

                    break;
                }
                case 2: {
                    imageResource = (shapeCode[tmpRank[i]]);


                    break;

                }
                case 3: {
                    imageResource = (animalCode[tmpRank[i]]);


                    break;

                }
                case 4: {
                    imageResource = (birdsCode[tmpRank[i]]);


                    break;

                }
                case 5: {
                    imageResource = (fruitsCode[tmpRank[i]]);


                    break;

                }

            }
            Glide.with(this).load(imageResource).into(imageView[i]);
        }
    }

    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            layout.setVisibility(View.VISIBLE);
            gamelayout.setVisibility(View.GONE);
            Log.d(TAG, "pre");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            layout.setVisibility(View.GONE);
            gamelayout.setVisibility(View.VISIBLE);
            Log.d(TAG, "post");
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.UK);
                    }
                }
            });
            Log.d(TAG, "background");
            /*try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return null;
        }
    }


}
