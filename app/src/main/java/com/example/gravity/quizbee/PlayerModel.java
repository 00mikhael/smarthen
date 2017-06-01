package com.example.gravity.quizbee;

/**
 * Created by Gravity on 5/23/2017.
 * This is a singleton class
 * it will only create one instance of a player
 * throughout the applications life cycle
 */

public class PlayerModel {
    // create a static instance of a player
    private static PlayerModel sPlayerModel;

    // creates player instance variables
    private String mPlayerName;
    private String mPlayerLastName;
    private int mPlayerScore;

    // returns player first name
    public String getPlayerName() {
        return mPlayerName;
    }

    // sets player first name
    public void setPlayerName(String playerName) {
        mPlayerName = playerName;
    }

    // returns player last name
    public String getPlayerLastName() {
        return mPlayerLastName;
    }

    // sets player last name
    public void setPlayerLastName(String playerLastName) {
        mPlayerLastName = playerLastName;
    }

    // returns player score
    public int getPlayerScore() {
        return mPlayerScore;
    }

    // sets player score
    public void setPlayerScore(int playerScore) {
        mPlayerScore = playerScore;
    }

    // get method which creates a single instance of a user
    public static PlayerModel get(String playerOne) {
        if (sPlayerModel == null) {
            sPlayerModel = new PlayerModel(playerOne);
        }
        return sPlayerModel;
    }

    //  private constructor for playermodel
    private PlayerModel(String playerOne) {
    }
}
