package com.example.gravity.quizbee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Gravity on 5/22/2017.
 * This is the first fragment on application launch
 * it asks the user for first name and last name
 */

public class HelloFragment extends Fragment {

    // Creates instance variables for objects
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    PlayerModel mPlayerModel;

    // newInstance is called by the host activity
    //it returns a new instance of HelloFragment
    public static HelloFragment newInstance() {
        return new HelloFragment();
    }

    // Gets called by the activity's fragment manager
    // it returns the view of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello, container, false);

        // Initializes instance variables
        // gets user first name and last name if the edit text is not empty
        // if its empty, sets error
        mFirstNameEditText = (EditText) view.findViewById(R.id.first_name_edit_text);
        mLastNameEditText = (EditText) view.findViewById(R.id.last_name_edit_text);
        final Button mContinueButton = (Button) view.findViewById(R.id.continue_button);
        mContinueButton.setVisibility(View.VISIBLE);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mFirstNameEditText.getText().toString().isEmpty()) {
                    if (!mLastNameEditText.getText().toString().isEmpty()) {
                        String firstName = mFirstNameEditText.getText().toString();
                        String lastName = mLastNameEditText.getText().toString();
                        mPlayerModel = PlayerModel.get(firstName);
                        mPlayerModel.setPlayerName(firstName);
                        mPlayerModel.setPlayerLastName(lastName);
                        Intent intent = WelcomeActivity.newIntent(getActivity(), firstName);
                        startActivity(intent);
                    } else {
                        mLastNameEditText.setError("last name");
                    }
                } else {
                    mFirstNameEditText.setError("first name");
                }
            }
        });
        return view;
    }
}
