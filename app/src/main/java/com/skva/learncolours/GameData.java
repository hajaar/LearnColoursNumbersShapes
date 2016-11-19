package com.skva.learncolours;

import android.content.Context;
import android.util.Log;

/**
 * Created by kartikn on 19-11-2016.
 */

public class GameData {
    private static int TOTAL_COUNT = 10;
    private String[] colorsText = {"black", "blue", "brown", "gray", "green", "pink", "purple", "red", "white", "yellow"};
    private String[] underwaterText = {"Crab", "Dolphin", "Fish", "Jellyfish", "Octopus", "Seahorse", "Shark", "Starfish", "Turtle", "Whale"};
    private String[] numbersText = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private String[] animalsText = {"Cat", "Cow", "Deer", "Dog", "Goat", "Horse", "Kangaroo", "Lion", "Monkey", "Tiger"};
    private String[] birdsText = {"Crow", "Flamingo", "Kingfisher", "Ostrich", "Owl", "Parrot", "Peacock", "Penguin", "Sparrow", "Swan"};
    private String[] fruitsText = {"Apple", "brinjal", "cabbage", "carrot", "coconut", "grapes", "lemon", "okra", "orange", "tomato"};
    private String[] shapesText = {"Rectangle", "Square", "Oval", "Circle", "Semicircle", "Diamond", "Heart", "Arrow", "Triangle", "Star"};
    private int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] tmpRank = new int[TOTAL_COUNT];
    private Context mContext;

    public GameData(Context context) // constructor
    {
        mContext = context;
    }


    public String getName(int gametype, int position) {
        String name = "";
        switch (gametype) {
            case 0:
                name = animalsText[tmpRank[position]];
                break;
            case 1:
                name = birdsText[tmpRank[position]];
                break;
            case 2:
                name = colorsText[tmpRank[position]];
                break;
            case 3:
                name = fruitsText[tmpRank[position]];
                break;
            case 4:
                name = numbersText[tmpRank[position]];
                break;
            case 5:
                name = shapesText[tmpRank[position]];
                break;
            case 6:
                name = underwaterText[tmpRank[position]];
                break;

        }
        return name;
    }

    public Integer getCode(int gametype, int position) {
        String gamename = mContext.getResources().getStringArray(R.array.games)[gametype];
        Log.d("GameData", gametype + " " + position + " " + gamename);
        return mContext.getResources().getIdentifier(gamename + "_" + getName(gametype, position).toLowerCase(), "drawable", mContext.getPackageName());

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
