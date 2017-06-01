package com.example.gravity.quizbee;

/**
 * Created by Gravity on 5/23/2017.
 * This is the quiz model class
 * it has three constructors
 * one for each type of question
 * checkboxes
 * radiobuttons
 * free text
 */

public class QuizModel {

    // creates instance quiz variables
    private int mQueResId;
    private int mOptResId1;
    private int mOptResId2;
    private int mOptResId3;
    private int mAnsResId1;
    private int mAnsResId2;
    private String mAnswer;
    private String mAnswerSmallCaps;
    private String mAnswerAllCaps;
    private String mQueNum;

    // returns the resId of the current question
    public int getQueResId() {
        return mQueResId;
    }

    // returns first option resId for each question
    public int getOptResId1() {
        return mOptResId1;
    }

    // returns second option resId for each question
    public int getOptResId2() {
        return mOptResId2;
    }

    // returns third option resId for each question
    public int getOptResId3() {
        return mOptResId3;
    }

    // returns correct ans1 resId for checkbox questions
    public int getAnsResId1() {
        return mAnsResId1;
    }

    // returns correct ans2 resId for checkbox questions
    public int getAnsResId2() {
        return mAnsResId2;
    }


    public String getQueNum() {
        return mQueNum;
    }

    public void setQueResId(int queResId) {
        mQueResId = queResId;
    }

    public void setQueNum(String queNum) {
        mQueNum = queNum;
    }

    public QuizModel(int QueResId, int CheckBoxOptResId1, int CheckBoxOptResId2, int CheckBoxOptResId3,
                     int CheckBoxAnsResId1, int CheckBoxAnsResId2, String QueNum) {
        mQueResId = QueResId;
        mOptResId1 = CheckBoxOptResId1;
        mOptResId2 = CheckBoxOptResId2;
        mOptResId3 = CheckBoxOptResId3;
        mAnsResId1 = CheckBoxAnsResId1;
        mAnsResId2 = CheckBoxAnsResId2;
        mQueNum = QueNum;

    }

    public QuizModel(int QueResId, int RadioOptResId1, int RadioOptResId2, int RadioOptResId3,
                     int RadioAnsResId1, String QueNum) {
        mQueResId = QueResId;
        mOptResId1 = RadioOptResId1;
        mOptResId2 = RadioOptResId2;
        mOptResId3 = RadioOptResId3;
        mAnsResId1 = RadioAnsResId1;
        mQueNum = QueNum;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public String getAnswerSmallCaps() {
        return mAnswerSmallCaps;
    }

    public String getAnswerAllCaps() {
        return mAnswerAllCaps;
    }

    public QuizModel(int QueResId, String FreeTextAnswer, String FreeTextAnswerSmallCaps, String FreeTextAnswerAllCaps, String QueNum) {
        mQueResId = QueResId;
        mAnswer = FreeTextAnswer;
        mAnswerSmallCaps = FreeTextAnswerSmallCaps;
        mAnswerAllCaps = FreeTextAnswerAllCaps;
        mQueNum = QueNum;

    }
}
