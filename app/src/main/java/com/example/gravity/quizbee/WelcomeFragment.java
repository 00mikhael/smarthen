package com.example.gravity.quizbee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Gravity on 5/23/2017.
 */

public class WelcomeFragment extends Fragment {
    private static final String ARG_USERNAME = "username";

    PlayerModel mPlayerModel;

    public static WelcomeFragment newInstance(String userName) {
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, userName);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        welcomeFragment.setArguments(args);
        return welcomeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        final String userName = getArguments().getString(ARG_USERNAME);
        TextView mWelcomeTextView = (TextView) view.findViewById(R.id.welcome_text_view);
        mPlayerModel = PlayerModel.get(userName);
        if (mPlayerModel.getPlayerScore() != 0) {
            mWelcomeTextView.setText("Welcome Back!\n" + mPlayerModel.getPlayerName()+"");
        }else {
            mWelcomeTextView.setText("Welcome!\n" + mPlayerModel.getPlayerName()+"");
        }

        Button mStartButton = (Button) view.findViewById(R.id.start_quiz);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra(ARG_USERNAME, userName);
                startActivity(intent);
            }
        });
        return view;
    }
}
