package com.skva.learncolours;

import android.content.Context;

/**
 * Created by kartikn on 19-11-2016.
 */

public class GameData {
    private static int TOTAL_COUNT = 10;
    private int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] tmpRank = new int[TOTAL_COUNT];
    private Context mContext;
    private String mGameName;

    public GameData(Context context, Integer game_type) // constructor
    {
        mContext = context;
        mGameName = mContext.getResources().getStringArray(R.array.games)[game_type];
    }


    public String getName(int position) {
        return mContext.getResources().getStringArray(mContext.getResources().getIdentifier(mGameName, "array", mContext.getPackageName()))[tmpRank[position]];

    }

    public Integer getCode(int position) {
        return mContext.getResources().getIdentifier(mGameName + "_" + getName(position).toLowerCase(), "drawable", mContext.getPackageName());

    }

    public void setRandomizedArray() {
        for (int i = 0; i < TOTAL_COUNT; i++) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) {
                tmpRank[i] = tmpRank[j];
            }
            tmpRank[j] = colorRank[i];
        }
    }
}
