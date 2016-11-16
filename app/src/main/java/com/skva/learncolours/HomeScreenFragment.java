package com.skva.learncolours;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {
    final static int COUNT = 6;
    ImageView[] imageView = new ImageView[COUNT];
    private FirebaseAnalytics mFirebaseAnalytics;
    private OnItemSelectedListener listener;

    public HomeScreenFragment() {
        // Required empty public constructor
    }

    public static HomeScreenFragment newInstance() {

        HomeScreenFragment f = new HomeScreenFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(getContext(), IntroActivity.class);
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


        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.home_list);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(8, 8, 0, 8);
        for (int i = 0; i < COUNT; i++) {

            imageView[i] = new ImageView(getActivity());
            imageView[i].setId(i);
            ll.addView(imageView[i], params);
            imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView[i].setPadding(4, 4, 0, 4);

            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGameSelected(view.getId());
                    Log.d("home", view.getId() + "");
                }
            });

            Glide.with(this).load(getResources().getIdentifier("launcher" + i, "drawable", getActivity().getPackageName())).into(imageView[i]);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement HomeScreenFragment.OnItemSelectedListener");
        }
    }


    public interface OnItemSelectedListener {
        void onGameSelected(Integer gametype);
    }
}