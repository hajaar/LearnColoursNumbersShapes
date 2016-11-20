package com.skva.learncolours;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements MyRecyclerViewAdapter.OnItemClickListener {
    private int GAME_COUNT;
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

        GAME_COUNT = getResources().getStringArray(R.array.games).length;


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
        listener.onGameSelected(position);
    }

    private void prepareItems() {

        for (int i = 0; i < GAME_COUNT; i++) {


            myRecyclerViewAdapter.add(
                    myRecyclerViewAdapter.getItemCount(),
                    getResources().getStringArray(R.array.games)[i],
                    getResources().getIdentifier("launcher_" + getResources().getStringArray(R.array.games)[i], "drawable", getActivity().getPackageName()));


        }
    }


    public interface OnItemSelectedListener {
        void onGameSelected(Integer gametype);
    }
}
