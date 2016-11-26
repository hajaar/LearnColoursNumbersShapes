package com.skva.learncolours;

import android.content.Context;
import android.util.Log;

import java.util.Random;

/**
 * Created by kartikn on 19-11-2016.
 */

public class GameData {
    private static int TOTAL_COUNT = 10;
    public String mGameName;
    String TAG = "GameData";
    private int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] tmpRank = new int[TOTAL_COUNT];
    private Context mContext;

    public GameData(Context context, Integer game_type) // constructor
    {
        mContext = context;
        int actual_game_type = game_type;
        if (game_type == 0) {
            Random random = new Random();

            actual_game_type = random.nextInt(mContext.getResources().getStringArray(R.array.games).length - 1) + 1;
        }
        mGameName = mContext.getResources().getStringArray(R.array.games)[actual_game_type];
    }


    public String getName(int position) {
        return mContext.getResources().getStringArray(mContext.getResources().getIdentifier(mGameName, "array", mContext.getPackageName()))[tmpRank[position]];

    }

    public Integer getCode(int position) {
        Log.d(TAG, "getCode: " + position + mGameName + getName(position).toLowerCase());
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
