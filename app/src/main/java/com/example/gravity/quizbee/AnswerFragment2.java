package com.example.gravity.quizbee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gravity on 5/23/2017.
 * This Fragment contains the radiobutton options layout
 */

public class AnswerFragment2 extends Fragment {

    //Intent Extra constant
    private static final String ARG_USERNAME = "username";

    // Creates instance variables for objects
    private TextView mQueNumText;
    private RadioButton mAnsRadio1;
    private RadioButton mAnsRadio2;
    private RadioButton mAnsRadio3;
    private Button mAnsButton1;
    private Button mAnsButton2;
    private Button mAnsButton3;
    private Button mNextButton;

    // Obtains a reference to the playerModel
    PlayerModel mPlayerModel;
    private int mPlayerScore = 0;

    // boolean for checking if the check change was just refreshing view
    // boolean that ensures result is not calculated twice
    // boolean that says when the questions are finished
    private boolean mToggled = false;
    private boolean mCalcResult = false;
    private boolean mFinished = false;

    // Interface for sending commands to host activity
    AnswerFragment2Listener quizCommander2;

    // Interface methods to be called on the host activity
    public interface AnswerFragment2Listener {
        void updateQuestion(int QueResId);
        void removeFragment2();
        void questionFinished();
    }

    // Initializes interface on fragment attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            quizCommander2 = (AnswerFragment2Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    // Create an array of the QuizModel and sets its questions
    // and options.
    private QuizModel[] mTechQue2 = new QuizModel[]{
            new QuizModel(R.string.tech_que2_1, R.string.tech_ans2_11, R.string.tech_ans2_21, R.string.tech_ans2_31, R.id.ans_radio2, "5"),
            new QuizModel(R.string.tech_que2_2, R.string.tech_ans2_12, R.string.tech_ans2_22, R.string.tech_ans2_32, R.id.ans_radio3, "6"),
    };
    // keeps track of the current index of the array
    private int mCurrentIndex = 0;

    // newInstance is called by the host activity
    //it returns a new instance of AnswerFragment2
    public static AnswerFragment2 newInstance() {
        return new AnswerFragment2();
    }

    // Gets called by the activity's fragment manager
    // it returns the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer2, container, false);

        // Sends the current question to question fragment through the interface quizCommander
        quizCommander2.updateQuestion(mTechQue2[mCurrentIndex].getQueResId());

        // Extracts its activities intent extra
        final String userName = getActivity().getIntent().getStringExtra(ARG_USERNAME);
        mPlayerModel = PlayerModel.get(userName);

        // Initializes instance variables
        mNextButton = (Button) view.findViewById(R.id.next_button);
        Button mPrevButton = (Button) view.findViewById(R.id.prev_button);
        mAnsButton1 = (Button) view.findViewById(R.id.ans_button1);
        mAnsButton2 = (Button) view.findViewById(R.id.ans_button2);
        mAnsButton3 = (Button) view.findViewById(R.id.ans_button3);
        mAnsRadio1 = (RadioButton) view.findViewById(R.id.ans_radio1);
        mAnsRadio2 = (RadioButton) view.findViewById(R.id.ans_radio2);
        mAnsRadio3 = (RadioButton) view.findViewById(R.id.ans_radio3);
        mQueNumText = (TextView) view.findViewById(R.id.question_num);

        // Updates the question number of the current question
        mQueNumText.setText(mTechQue2[mCurrentIndex].getQueNum());

        // Next button click listener
        // Increases the current index by one
        // checks if the questions are finished
        // if no
        // refreshes the view
        // if yes, does nothing
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCurrentIndex();
                if (!mFinished) {
                    updateView();
                }
            }
        });
        // Back button click listener
        // Decreases the current index by one
        // refreshes the view
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusCurrentIndex();
                updateView();
            }
        });

        // sets the first option for the current question
        // toggles the radiobutton1 when clicked
        mAnsButton1.setText(mTechQue2[mCurrentIndex].getOptResId1());
        mAnsButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsRadio1.toggle();
            }
        });

        // sets the second option for the current question
        // toggles the radiobutton2 when clicked
        mAnsButton2.setText(mTechQue2[mCurrentIndex].
                getOptResId2());
        mAnsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsRadio2.toggle();

            }
        });

        // sets the third option for the current question
        // toggles the radiobutton3 when clicked
        mAnsButton3.setText(mTechQue2[mCurrentIndex].getOptResId3());
        mAnsButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsRadio3.toggle();
            }
        });

        // Listens for change in the checked state of radiobutton1
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives points if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsRadio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mAnsRadio1.isChecked()) {
                    checkAnswer(R.id.ans_radio1);
                    mAnsButton1.setBackgroundResource(R.drawable.edit_text_background);
                } else {
                    secureScore(R.id.ans_radio1);
                    mAnsButton1.setBackgroundResource(R.drawable.buttun_states);
                }
            }
        });

        // Listens for change in the checked state of radiobutton2
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives points if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsRadio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mAnsRadio2.isChecked()) {
                    checkAnswer(R.id.ans_radio2);
                    mAnsButton2.setBackgroundResource(R.drawable.edit_text_background);
                } else {
                    secureScore(R.id.ans_radio2);
                    mAnsButton2.setBackgroundResource(R.drawable.buttun_states);
                }
            }
        });

        // Listens for change in the checked state of radiobutton3
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives points if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsRadio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mAnsRadio3.isChecked()) {
                    checkAnswer(R.id.ans_radio3);
                    mAnsButton3.setBackgroundResource(R.drawable.edit_text_background);
                } else {
                    secureScore(R.id.ans_radio3);
                    mAnsButton3.setBackgroundResource(R.drawable.buttun_states);
                }
            }
        });
        return view;
    }// end of onCreateView method

    // checks if the users answer is correct
    public void checkAnswer(int userAnsResId) {
        if (!mToggled) {
            int answerResId = mTechQue2[mCurrentIndex].getAnsResId1();

            int toastMsgResId;

            if (userAnsResId == answerResId) {
                toastMsgResId = R.string.correct;
                mPlayerScore += 10;
            } else {
                toastMsgResId = R.string.incorrect;
            }
            Toast.makeText(getActivity(), toastMsgResId, Toast.LENGTH_SHORT).show();
        }
    }

    // secures the score if the user unchecks a correct answer
    public void secureScore(int userUncheckedId) {
        if (!mToggled) {
            int answerResId = mTechQue2[mCurrentIndex].getAnsResId1();

            int toastMsgResId;

            if (userUncheckedId == answerResId) {
                mPlayerScore -= 10;
                toastMsgResId = R.string.secure_score;
                Toast.makeText(getActivity(), toastMsgResId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // increases current index by 1
    // if its the last question of the fragment
    // it calls the activity to swap to the next
    // fragment
    public void addCurrentIndex() {
        if (mCurrentIndex == 1) {
            mFinished = true;
            quizCommander2.questionFinished();
            disableOptions();
        } else {
            mCurrentIndex = (mCurrentIndex + 1);
        }
    }

    // Decreases the current index by 1
    public void minusCurrentIndex() {
        if (mCurrentIndex == 0) {
            quizCommander2.removeFragment2();
        } else {
            mCurrentIndex = (mCurrentIndex - 1);
            enableOptions();
        }
    }

    // Refreshes views
    public void updateView() {
        quizCommander2.updateQuestion(mTechQue2[mCurrentIndex].getQueResId());
        mAnsButton1.setText(mTechQue2[mCurrentIndex].getOptResId1());
        mAnsButton2.setText(mTechQue2[mCurrentIndex].getOptResId2());
        mAnsButton3.setText(mTechQue2[mCurrentIndex].getOptResId3());
        mQueNumText.setText(mTechQue2[mCurrentIndex].getQueNum());
        mAnsButton1.setBackgroundResource(R.drawable.buttun_states);
        mAnsButton2.setBackgroundResource(R.drawable.buttun_states);
        mAnsButton3.setBackgroundResource(R.drawable.buttun_states);
        toggleRadio();
        mToggled = false;
    }

    // sets all the checked states to false
    public void toggleRadio() {
        mToggled = true;
        mAnsRadio1.setChecked(false);
        mAnsRadio2.setChecked(false);
        mAnsRadio3.setChecked(false);
    }

    // disables all options when the questions are finish
    public void disableOptions() {
        mAnsRadio1.setEnabled(false);
        mAnsRadio2.setEnabled(false);
        mAnsRadio3.setEnabled(false);
        mAnsButton1.setEnabled(false);
        mAnsButton2.setEnabled(false);
        mAnsButton3.setEnabled(false);
    }

    // enables all options if the clicks back after disabling
    public void enableOptions() {
        mAnsRadio1.setEnabled(true);
        mAnsRadio2.setEnabled(true);
        mAnsRadio3.setEnabled(true);
        mAnsButton1.setEnabled(true);
        mAnsButton2.setEnabled(true);
        mAnsButton3.setEnabled(true);
    }

    // ensures the result isnt calculated twice
    //  checks and displays result toast
    public void displayResult() {
        if (!mCalcResult) {
            int score = mPlayerModel.getPlayerScore();
            mPlayerScore = mPlayerScore + score;
            mPlayerModel.setPlayerScore(mPlayerScore);
        }
        mCalcResult = true;

        String toastMsg = (mPlayerModel.getPlayerName() + " , Your Score is " + mPlayerModel.getPlayerScore());
        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
