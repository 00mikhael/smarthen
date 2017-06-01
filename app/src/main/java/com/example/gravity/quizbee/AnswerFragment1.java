package com.example.gravity.quizbee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gravity on 5/23/2017.
 * This Fragment contains the free text input layout
 */

public class AnswerFragment1 extends Fragment {

    //Intent Extra constant
    private static final String ARG_USERNAME = "username";

    // Creates instance variables for objects
    private EditText mAnsEditText;
    private Button mNextButton;
    private TextView mQueNumText;
    private Button mSubAnsButton;

    // Obtains a reference to the playerModel
    PlayerModel mPlayerModel;
    private int mPlayerScore = 0;

    // checks if the user have submitted an answer
    private boolean mSubmitted = false;

    // Interface for sending commands to host activity
    AnswerFragment1Listener quizCommander1;

    // Interface methods to be called on the host activity
    public interface AnswerFragment1Listener {
        void updateQuestion(int QueResId);
        void replaceFragment2();
        void removeFragment1();
    }

    // Initializes interface on fragment attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            quizCommander1 = (AnswerFragment1Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    // Create an array of the QuizModel and provides
    // three correct answer for case sensitivity
    private QuizModel[] mTechQue1 = new QuizModel[]{
            new QuizModel(R.string.tech_que1_1, "Mark Zuckerberg", "mark zuckerberg", "MARK ZUCKERBERG", "3"),
            new QuizModel(R.string.tech_que1_2, "Boolean", "boolean", "BOOLEAN", "4"),
    };
    // keeps track of the current index of the array
    private int mCurrentIndex = 0;

    // newInstance is called by the host activity
    //it returns a new instance of AnswerFragment1
    public static AnswerFragment1 newInstance() {
        return new AnswerFragment1();
    }

    // Gets called by the activity's fragment manager
    // it returns the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer1, container, false);

        // Sends the current question to question fragment through the interface quizCommander
        quizCommander1.updateQuestion(mTechQue1[mCurrentIndex].getQueResId());

        // Extracts its activities intent extra
        final String userName = getActivity().getIntent().getStringExtra(ARG_USERNAME);
        mPlayerModel = PlayerModel.get(userName);

        // Initializes instance variables
        mNextButton = (Button) view.findViewById(R.id.next_button);
        Button mPrevButton = (Button) view.findViewById(R.id.prev_button);
        mAnsEditText = (EditText) view.findViewById(R.id.ans_edit_text);
        mSubAnsButton = (Button) view.findViewById(R.id.ans_sub_button);
        mQueNumText = (TextView) view.findViewById(R.id.question_num);

        // Updates the question number of the current question
        mQueNumText.setText(mTechQue1[mCurrentIndex].getQueNum());

        // Next button click listener
        // Increases the current index by one
        // refreshes the view
        // sets submitted status to false for the next question
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCurrentIndex();
                updateView();
                mSubmitted = false;
            }
        });
        // Back button click listener
        // Decreases the current index by one
        // refreshes the view
        // sets submitted status to true for the previous question
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusCurrentIndex();
                updateView();
                mSubmitted = true;
            }
        });

        // Listens for clicks on the submit button
        // Checks if te edittext is empty
        // if yes, sets error
        // if no, takes text and convert to string
        // calls checks answer
        // disables edit text
        // if os is above 5.0 and above
        // animates button to invisible
        // else, just makes it go away
        // secures answer if the user tries to get smart
        mSubAnsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAnsEditText.getText().toString().isEmpty()) {
                    String userAnswer = mAnsEditText.getText().toString();
                    checkAnswer(userAnswer);
                    mAnsEditText.setText("");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int cx = mSubAnsButton.getWidth() / 2;
                        int cy = mSubAnsButton.getHeight() / 2;
                        float radius = mSubAnsButton.getWidth();
                        Animator anim = ViewAnimationUtils
                                .createCircularReveal(mSubAnsButton, cx, cy, radius, 0);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mAnsEditText.setEnabled(false);
                                mSubAnsButton.setVisibility(View.INVISIBLE);
                            }
                        });
                        anim.start();
                    }
                    else {
                        mAnsEditText.setEnabled(false);
                        mSubAnsButton.setVisibility(View.INVISIBLE);
                    }
                } else {
                    mAnsEditText.setError("Hi " + mPlayerModel.getPlayerName() + ", Please Type Answer!");
                }
            }
        });
        return view;
    }

    // checks answer against the three case formats
    public void checkAnswer(String userAnswer) {
        String answer = mTechQue1[mCurrentIndex].getAnswer();
        String answerSmallCaps = mTechQue1[mCurrentIndex].getAnswerSmallCaps();
        String answerAllCaps = mTechQue1[mCurrentIndex].getAnswerAllCaps();

        int toastMsgResId;

        if (!mSubmitted && (userAnswer.equals(answer) || userAnswer.equals(answerSmallCaps) ||
                userAnswer.equals(answerAllCaps))) {
            mSubmitted = true;
            mPlayerScore += 20;
            toastMsgResId = R.string.correct;
            Toast.makeText(getActivity(), toastMsgResId, Toast.LENGTH_SHORT).show();
        } else if (mSubmitted && (userAnswer.equals(answer) || userAnswer.equals(answerSmallCaps) ||
                userAnswer.equals(answerAllCaps))) {
            mPlayerScore -= 20;
            toastMsgResId = R.string.secure_score;
            Toast.makeText(getActivity(), toastMsgResId, Toast.LENGTH_SHORT).show();
        }
    }

    // increases current index by 1
    public void addCurrentIndex() {
        if (mCurrentIndex == 1) {
            int score = mPlayerModel.getPlayerScore();
            mPlayerScore = mPlayerScore + score;
            mPlayerModel.setPlayerScore(mPlayerScore);
            quizCommander1.replaceFragment2();
        } else {
            mCurrentIndex = (mCurrentIndex + 1);
        }
    }

    //decreases current index by 1
    public void minusCurrentIndex() {
        if (mCurrentIndex == 0) {
            quizCommander1.removeFragment1();
        } else {
            mCurrentIndex = (mCurrentIndex - 1);
        }
    }

    // refreshes view
    public void updateView() {
        quizCommander1.updateQuestion(mTechQue1[mCurrentIndex].getQueResId());
        mQueNumText.setText(mTechQue1[mCurrentIndex].getQueNum());
        mSubAnsButton.setVisibility(View.VISIBLE);
        mAnsEditText.setEnabled(true);
    }
}
