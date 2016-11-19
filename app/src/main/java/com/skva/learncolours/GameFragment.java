package com.skva.learncolours;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    private static final int COUNT = 10;
    private static final String TAG = "GameFragment";
    static int GAME_TYPE = 0;
    Animation animScale, animLeft;

    int[] buttonID = new int[COUNT];
    int[] layoutID = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.b10};
    ImageView[] imageView = new ImageView[COUNT];
    TextView[] textView = new TextView[COUNT];
    private GameData mGameData;
    private SwipeRefreshLayout swipeContainer;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(Integer game_type) {

        GameFragment f = new GameFragment();
        Bundle b = new Bundle();
        b.putInt("GAME_TYPE", game_type);
        GAME_TYPE = game_type;
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

        animScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale);
        animLeft = AnimationUtils.loadAnimation(getContext(), R.anim.appear);

        for (int i = 0; i < COUNT; i++) {
            LinearLayout ll = (LinearLayout) getActivity().findViewById(layoutID[i]);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 4);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 1);
            imageView[i] = new ImageView(getContext());
            imageView[i].setId(i);
            textView[i] = new TextView(getContext());
            textView[i].setGravity(Gravity.CENTER_HORIZONTAL);
            textView[i].setTypeface(Typeface.createFromAsset(getContext().getAssets(), "ComicRelief.ttf"));
            textView[i].setTextColor(getResources().getColor(R.color.colorPrimaryText));

            ll.addView(imageView[i], params1);
            ll.addView(textView[i], params2);
            imageView[i].setOnClickListener(this);
            buttonID[i] = i;


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

        ((MainActivity) getActivity()).speakOut(mGameData.getName(pos));

    }


    private void shuffleColors() {

        mGameData = new GameData(getContext(), getArguments().getInt("GAME_TYPE"));
        ((TextView) getActivity().findViewById(R.id.game_header)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "ComicRelief.ttf"));
        ((TextView) getActivity().findViewById(R.id.game_header)).setText(mGameData.mGameName);
        mGameData.setRandomizedArray();

        for (int i = 0; i < COUNT; i++) {

            Glide.with(this).load(mGameData.getCode(i)).animate(animLeft).fitCenter().into(imageView[i]);
            textView[i].setAnimation(animLeft);
            textView[i].setText(mGameData.getName(i));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
