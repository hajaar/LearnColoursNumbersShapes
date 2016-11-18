package com.skva.learncolours;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements MyRecyclerViewAdapter.OnItemClickListener {
    final static int COUNT = 6;
    ImageView[] imageView = new ImageView[COUNT];
    private FirebaseAnalytics mFirebaseAnalytics;
    private OnItemSelectedListener listener;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

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


       /* LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.home_list);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(16, 8, 16, 8);
        for (int i = 0; i < COUNT; i++) {

            imageView[i] = new ImageView(getActivity());
            imageView[i].setId(i);


            imageView[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView[i].setPadding(4, 4, 4, 4);
            //imageView[i].setBackground(getResources().getDrawable(R.drawable.skipcustomborder));
            ll.addView(imageView[i], params);

            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGameSelected(view.getId());
                    Log.d("home", view.getId() + "");
                }
            });

            Glide.with(this).load(getResources().getIdentifier("launcher" + i, "drawable", getActivity().getPackageName())).into(imageView[i]);

        }*/
        myRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        myRecyclerView.setAdapter(myRecyclerViewAdapter);
        myRecyclerView.setLayoutManager(mLinearLayoutManager);
        myRecyclerViewAdapter.setOnItemClickListener(this);
        prepareItems();
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

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onItemClick(MyRecyclerViewAdapter.ItemHolder item, int position) {
        Animation animScale =
                AnimationUtils.loadAnimation(getContext(),
                        R.anim.anim_scale);
        item.imageView.setAnimation(animScale);
        listener.onGameSelected(position);
    }

    private void prepareItems() {

        for (int i = 0; i < COUNT; i++) {


            myRecyclerViewAdapter.add(
                    myRecyclerViewAdapter.getItemCount(),
                    "R.drawable.launcher" + i,
                    getResources().getIdentifier("launcher" + i, "drawable", getActivity().getPackageName()));


        }
    }


    public interface OnItemSelectedListener {
        void onGameSelected(Integer gametype);
    }
}
