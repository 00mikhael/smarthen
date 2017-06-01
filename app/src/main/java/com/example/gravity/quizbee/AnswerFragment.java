package com.example.gravity.quizbee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gravity on 5/22/2017.
 * This Fragment contains the checkbox options layout
 */

public class AnswerFragment extends Fragment {

    //Intent Extra constant
    private static final String ARG_USERNAME = "username";

    // Creates instance variables for objects
    private TextView mQueNumText;
    private CheckBox mAnsCheckBox1;
    private CheckBox mAnsCheckBox2;
    private CheckBox mAnsCheckBox3;
    private Button mAnsButton1;
    private Button mAnsButton2;
    private Button mAnsButton3;
    private Button mPrevButton;
    private boolean mToggled = false;

    // Obtains a reference to the playerModel
    PlayerModel mPlayerModel;
    private int mPlayerScore = 0;

    // Interface for sending commands to host activity
    AnswerFragmentListener quizCommander;

    // Interface methods to be called on the host activity
    public interface AnswerFragmentListener {
        void updateQuestion(int QueResId);
        void replaceFragment1();
    }

    // Initializes interface on fragment attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            quizCommander = (AnswerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    // Create an array of the QuizModel and sets its questions
    // and options.
    private QuizModel[] mTechQue = new QuizModel[]{
            new QuizModel(R.string.tech_que_1, R.string.tech_ans_11, R.string.tech_ans_21, R.string.tech_ans_31, R.id.ans_checkbox1, R.id.ans_checkbox3, "1"),
            new QuizModel(R.string.tech_que_2, R.string.tech_ans_12, R.string.tech_ans_22, R.string.tech_ans_32, R.id.ans_checkbox1, R.id.ans_checkbox2, "2"),
    };
    // keeps track of the current index of the array
    private int mCurrentIndex = 0;

    // newInstance is called by the host activity
    //it returns a new instance of AnswerFragment
    public static AnswerFragment newInstance() {
        return new AnswerFragment();
    }

    // Gets called by the activity's fragment manager
    // it returns the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);

        // Sends the current question to question fragment through the interface quizCommander
        quizCommander.updateQuestion(mTechQue[mCurrentIndex].getQueResId());

        // Extracts its activities intent extra
        final String userName = getActivity().getIntent().getStringExtra(ARG_USERNAME);
        mPlayerModel = PlayerModel.get(userName);

        // Initializes instance variables
        mPrevButton = (Button) view.findViewById(R.id.prev_button);
        Button mNextButton = (Button) view.findViewById(R.id.next_button);
        mAnsButton1 = (Button) view.findViewById(R.id.ans_button1);
        mAnsButton2 = (Button) view.findViewById(R.id.ans_button2);
        mAnsButton3 = (Button) view.findViewById(R.id.ans_button3);
        mAnsCheckBox1 = (CheckBox) view.findViewById(R.id.ans_checkbox1);
        mAnsCheckBox2 = (CheckBox) view.findViewById(R.id.ans_checkbox2);
        mAnsCheckBox3 = (CheckBox) view.findViewById(R.id.ans_checkbox3);
        mQueNumText = (TextView) view.findViewById(R.id.question_num);

        // Updates the question number of the current question
        mQueNumText.setText(mTechQue[mCurrentIndex].getQueNum());

        // Next button click listener
        // Increases the current index by one
        // refreshes the view
        // enables previews button option
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCurrentIndex();
                updateView();
                mPrevButton.setEnabled(true);
            }
        });
        // Back button click listener
        // Decreases the current index by one
        // refreshes the view
        mPrevButton.setEnabled(false);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusCurrentIndex();
                updateView();
            }
        });
        // sets the first option for the current question
        // toggles the checkbox1 when clicked
        mAnsButton1.setText(mTechQue[mCurrentIndex].getOptResId1());
        mAnsButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsCheckBox1.toggle();
            }
        });
        // // sets the second option for the current question
        // toggles the checkbox2 when clicked
        mAnsButton2.setText(mTechQue[mCurrentIndex].getOptResId2());
        mAnsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsCheckBox2.toggle();

            }
        });
        // sets the third option for the current question
        // toggles the checkbox3 when clicked
        mAnsButton3.setText(mTechQue[mCurrentIndex].getOptResId3());
        mAnsButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnsCheckBox3.toggle();
            }
        });

        // Listens for change in the checked state of checkbox1
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives points if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mToggled) {
                    if (mAnsCheckBox2.isChecked() && mAnsCheckBox3.isChecked()) {
                        mAnsCheckBox1.toggle();
                    } else {
                        if (mAnsCheckBox1.isChecked()) {
                            checkAnswer(R.id.ans_checkbox1);
                            mAnsButton1.setBackgroundResource(R.drawable.edit_text_background);
                        } else {
                            secureScore(R.id.ans_checkbox1);
                            mAnsButton1.setBackgroundResource(R.drawable.buttun_states);
                        }
                    }
                }
            }
        });

        // Listens for change in the checked state of checkbox2
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives points if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mToggled) {
                    if (mAnsCheckBox1.isChecked() && mAnsCheckBox3.isChecked()) {
                        mAnsCheckBox2.toggle();
                    } else {
                        if (mAnsCheckBox2.isChecked()) {
                            checkAnswer(R.id.ans_checkbox2);
                            mAnsButton2.setBackgroundResource(R.drawable.edit_text_background);
                        } else {
                            secureScore(R.id.ans_checkbox2);
                            mAnsButton2.setBackgroundResource(R.drawable.buttun_states);
                        }
                    }
                }
            }
        });

        // Listens for change in the checked state of checkbox3
        // Checks if the check change just refreshing the view
        // if yes, ignores
        // if no, checks answer
        // gives point if correct
        // also highlights associated button
        // if the checkbox is unchecked,
        // and the answer correct
        //it secures the points previously given
        mAnsCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mToggled) {
                    if (mAnsCheckBox1.isChecked() && mAnsCheckBox2.isChecked()) {
                        mAnsCheckBox3.toggle();
                    } else {
                        if (mAnsCheckBox3.isChecked()) {
                            checkAnswer(R.id.ans_checkbox3);
                            mAnsButton3.setBackgroundResource(R.drawable.edit_text_background);
                        } else {
                            secureScore(R.id.ans_checkbox3);
                            mAnsButton3.setBackgroundResource(R.drawable.buttun_states);
                        }
                    }
                }
            }
        });
        return view;
    }// end of onCreateView method

    // checks if the users answer is correct
    public void checkAnswer(int userAnsResId) {
        if (!mToggled) {
            int answerResId = mTechQue[mCurrentIndex].getAnsResId1();
            int answerResId2 = mTechQue[mCurrentIndex].getAnsResId2();

            int toastMsgResId;

            if (userAnsResId == answerResId) {
                toastMsgResId = R.string.correct;
                mPlayerScore += 10;
            } else if (userAnsResId == answerResId2) {
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
            int answerResId = mTechQue[mCurrentIndex].getAnsResId1();
            int answerResId2 = mTechQue[mCurrentIndex].getAnsResId2();

            int toastMsgResId;

            if (userUncheckedId == answerResId) {
                mPlayerScore -= 10;
                toastMsgResId = R.string.secure_score;
                Toast.makeText(getActivity(), toastMsgResId, Toast.LENGTH_SHORT).show();
            } else if (userUncheckedId == answerResId2) {
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
            mPlayerModel.setPlayerScore(mPlayerScore);
            quizCommander.replaceFragment1();
        } else {
            mCurrentIndex = (mCurrentIndex + 1);
        }
    }

    // Decreases the current index by 1
    public void minusCurrentIndex() {
        if (mCurrentIndex == 0) {
            mPrevButton.setEnabled(false);
        } else {
            mCurrentIndex = (mCurrentIndex - 1);
        }
    }

    // Refreshes views
    public void updateView() {
        quizCommander.updateQuestion(mTechQue[mCurrentIndex].getQueResId());
        mAnsButton1.setText(mTechQue[mCurrentIndex].getOptResId1());
        mAnsButton2.setText(mTechQue[mCurrentIndex].getOptResId2());
        mAnsButton3.setText(mTechQue[mCurrentIndex].getOptResId3());
        mQueNumText.setText(mTechQue[mCurrentIndex].getQueNum());
        mAnsButton1.setBackgroundResource(R.drawable.buttun_states);
        mAnsButton2.setBackgroundResource(R.drawable.buttun_states);
        mAnsButton3.setBackgroundResource(R.drawable.buttun_states);
        toggleCheck();
        mToggled = false;
    }

    // sets all the checked states to false
    public void toggleCheck() {
        mToggled = true;
        mAnsCheckBox1.setChecked(false);
        mAnsCheckBox2.setChecked(false);
        mAnsCheckBox3.setChecked(false);
    }
}
