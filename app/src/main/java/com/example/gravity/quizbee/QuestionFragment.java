package com.example.gravity.quizbee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gravity on 5/22/2017.
 */

public class QuestionFragment extends Fragment {
    private static final String ARG_USERNAME = "username";

    private TextView mQueText;
    private Button mSubButton;
    private ImageView mCoolResult;
    private ImageView mBadResult;
    private ImageView mAwesomeResult;
    private TextView mScoreText;

    PlayerModel mPlayerModel;
    private boolean reactionShown = false;

    QuestionFragmentListener quizCommander0;

    public interface QuestionFragmentListener {
        void showResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            quizCommander0 = (QuestionFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        final String userName = getActivity().getIntent().getStringExtra(ARG_USERNAME);
        mPlayerModel = PlayerModel.get(userName);

        mScoreText = (TextView) view.findViewById(R.id.score_text);
        mCoolResult = (ImageView) view.findViewById(R.id.cool);
        mCoolResult.setVisibility(View.INVISIBLE);
        mAwesomeResult = (ImageView) view.findViewById(R.id.awesome);
        mAwesomeResult.setVisibility(View.INVISIBLE);
        mBadResult = (ImageView) view.findViewById(R.id.bad);
        mBadResult.setVisibility(View.INVISIBLE);
        mQueText = (TextView) view.findViewById(R.id.question_text);
        TextView mNameText = (TextView) view.findViewById(R.id.username);
        mNameText.setText(mPlayerModel.getPlayerName());
        mSubButton = (Button) view.findViewById(R.id.sub_button);
        mSubButton.setVisibility(View.GONE);
        mScoreText.setVisibility(View.GONE);
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reactionShown) {
                    quizCommander0.showResult();
                    mScoreText.setVisibility(View.VISIBLE);
                    mScoreText.setText("Score: " + mPlayerModel.getPlayerScore()+"");
                    resultReaction();
                    reactionShown = true;
                    mSubButton.setText("Done");
                }else {
                    Intent intent = WelcomeActivity.newIntent(getActivity(), userName);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void setQueText(int mQueResId) {
        mQueText.setText(mQueResId);
        mSubButton.setVisibility(View.GONE);
        mScoreText.setVisibility(View.GONE);
        mSubButton.setText("submit");
    }
    public void onQueFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mSubButton.getWidth() / 2;
            int cy = mSubButton.getHeight() / 2;
            float radius = mSubButton.getWidth();
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(mSubButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mQueText.setText("That's all for now!!");
                    mSubButton.setVisibility(View.VISIBLE);
                }
            });
            anim.start();
        }
        else {
            mQueText.setText("That's all for now!!");
            mSubButton.setVisibility(View.VISIBLE);
        }
    }
    public void resultReaction() {
        int finalScore = mPlayerModel.getPlayerScore();
        if (finalScore <= 50) {
            startBadAnimation();
            mQueText.setText("Read more!, " + mPlayerModel.getPlayerName()+".");
        }else if (finalScore <= 90) {
            startCoolAnimation();
            mQueText.setText("Good Job!! " + mPlayerModel.getPlayerName()+".");
        }else {
            startAwesomeAnimation();
            mQueText.setText("On Fire!!! " + mPlayerModel.getPlayerName()+".");
        }
    }
    private void startCoolAnimation() {
        mCoolResult.setVisibility(View.VISIBLE);
        float sunYStart = mCoolResult.getTop();
        float sunYEnd = mQueText.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mCoolResult, "y", sunYStart, sunYEnd)
                .setDuration(2000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator);
        animatorSet.start();
    }
    private void startAwesomeAnimation() {
        mAwesomeResult.setVisibility(View.VISIBLE);
        float sunYStart = mAwesomeResult.getTop();
        float sunYEnd = mQueText.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mAwesomeResult, "y", sunYStart, sunYEnd)
                .setDuration(2000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator);
        animatorSet.start();
    }
    private void startBadAnimation() {
        mBadResult.setVisibility(View.VISIBLE);
        float sunYStart = mBadResult.getTop();
        float sunYEnd = mQueText.getTop();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mBadResult, "y", sunYStart, sunYEnd)
                .setDuration(2000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator);
        animatorSet.start();
    }
}
