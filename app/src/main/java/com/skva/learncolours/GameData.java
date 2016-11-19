package com.skva.learncolours;

/**
 * Created by kartikn on 19-11-2016.
 */

public class GameData {
    private static int TOTAL_COUNT = 10;
    private int gametype = 0;
    private String[] colorText = {"Black", "Blue", "Brown", "Gray", "Green", "Pink", "Purple", "Red", "White", "Yellow"};
    private int[] colorCode = {R.drawable.black, R.drawable.blue, R.drawable.brown, R.drawable.gray, R.drawable.green, R.drawable.pink, R.drawable.purple, R.drawable.red, R.drawable.white, R.drawable.yellow};
    private String[] underwaterText = {"Crab", "Dolphin", "Fish", "Jellyfish", "Octopus", "Seahorse", "Shark", "Starfish", "Turtle", "Whale"};
    private int[] underwaterCode = {R.drawable.crab, R.drawable.dolphin, R.drawable.fish, R.drawable.jellyfish, R.drawable.octopus, R.drawable.seahorse, R.drawable.shark, R.drawable.starfish, R.drawable.turtle, R.drawable.whale};

    private String[] numberText = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private String[] animalText = {"Cat", "Cow", "Deer", "Dog", "Goat", "Horse", "Kangaroo", "Lion", "Monkey", "Tiger"};
    private int[] animalCode = {R.drawable.cat, R.drawable.cow, R.drawable.deer, R.drawable.dog, R.drawable.goat, R.drawable.horse, R.drawable.kangaroo, R.drawable.lion, R.drawable.monkey, R.drawable.tiger};
    private String[] birdsText = {"Crow", "Flamingo", "Kingfisher", "Ostrich", "Owl", "Parrot", "Peacock", "Penguin", "Sparrow", "Swan"};
    private int[] birdsCode = {R.drawable.crow, R.drawable.flamingo, R.drawable.kingfisher, R.drawable.ostrich, R.drawable.owl, R.drawable.parrot, R.drawable.peacock, R.drawable.penguin, R.drawable.sparrow, R.drawable.swan};
    private String[] fruitsText = {"Apple", "brinjal", "cabbage", "carrot", "coconut", "grapes", "lemon", "okra", "orange", "tomato"};
    private int[] fruitsCode = {R.drawable.apple, R.drawable.brinjal, R.drawable.cabbage, R.drawable.carrot, R.drawable.coconut, R.drawable.grapes, R.drawable.lemon, R.drawable.okra, R.drawable.orange, R.drawable.tomato};

    private String[] shapeText = {"Rectangle", "Square", "Oval", "Circle", "Semi-circle", "Diamond", "Heart", "Arrow", "Triangle", "Star"};
    private int[] shapeCode = {R.drawable.shape1, R.drawable.shape2, R.drawable.shape3, R.drawable.shape4, R.drawable.shape5, R.drawable.shape6, R.drawable.shape7, R.drawable.shape8, R.drawable.shape9, R.drawable.shape10};
    private int[] numberCode = {R.drawable.number1, R.drawable.number2, R.drawable.number3, R.drawable.number4, R.drawable.number5, R.drawable.number6, R.drawable.number7, R.drawable.number8, R.drawable.number9, R.drawable.number10};
    private int[] colorRank = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] tmpRank = new int[TOTAL_COUNT];


    public String getName(int gametype, int position) {
        String name = "";
        switch (gametype) {
            case 0:
                name = colorText[tmpRank[position]];
                break;
            case 1:
                name = numberText[tmpRank[position]];
                break;
            case 2:
                name = shapeText[tmpRank[position]];
                break;
            case 3:
                name = animalText[tmpRank[position]];
                break;
            case 4:
                name = birdsText[tmpRank[position]];
                break;
            case 5:
                name = fruitsText[tmpRank[position]];
                break;
            case 6:
                name = underwaterText[tmpRank[position]];
                break;

        }
        return name;
    }

    public Integer getCode(int gametype, int position) {
        Integer gamecode = 0;
        switch (gametype) {
            case 0:
                gamecode = colorCode[tmpRank[position]];
                break;
            case 1:
                gamecode = numberCode[tmpRank[position]];
                break;
            case 2:
                gamecode = shapeCode[tmpRank[position]];
                break;
            case 3:
                gamecode = animalCode[tmpRank[position]];
                break;
            case 4:
                gamecode = birdsCode[tmpRank[position]];
                break;
            case 5:
                gamecode = fruitsCode[tmpRank[position]];
                break;
            case 6:
                gamecode = underwaterCode[tmpRank[position]];
                break;

        }
        return gamecode;
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
