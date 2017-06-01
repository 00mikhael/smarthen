package com.example.gravity.quizbee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Gravity on 5/22/2017.
 */

public class QuizActivity extends AppCompatActivity
        implements AnswerFragment.AnswerFragmentListener, AnswerFragment2.AnswerFragment2Listener, AnswerFragment1.AnswerFragment1Listener, QuestionFragment.QuestionFragmentListener{

    FragmentManager fm = getSupportFragmentManager();

    protected int getLayoutResId() {
        return R.layout.activity_fragment1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = QuestionFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        Fragment fragment1 = fm.findFragmentById(R.id.fragment_container1);

        if (fragment1 == null) {
            fragment1 = AnswerFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container1, fragment1)
                    .commit();
        }
    }

    @Override
    public void replaceFragment1() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container1);
        if (fragment != null) {
            fragment = AnswerFragment1.newInstance();
            fm.beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void replaceFragment2() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container1);
        if (fragment != null) {
            fragment = AnswerFragment2.newInstance();
            fm.beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void removeFragment2() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container1);
        if (fragment != null) {
            fragment = AnswerFragment1.newInstance();
            fm.beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit();
        }
    }

    @Override
    public void removeFragment1() {
        Fragment fragment = fm.findFragmentById(R.id.fragment_container1);
        if (fragment != null) {
            fragment = AnswerFragment.newInstance();
            fm.beginTransaction()
                    .replace(R.id.fragment_container1, fragment)
                    .commit();
        }
    }

    //this gets called by the AnswerFragment to update the questions
    @Override
    public void updateQuestion(int QueResId) {
        QuestionFragment questionFragment =
                (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        questionFragment.setQueText(QueResId);
    }

    @Override
    public void questionFinished() {
        QuestionFragment questionFragment =
                (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        questionFragment.onQueFinish();
    }

    @Override
    public void showResult() {
        AnswerFragment2 answerFragment2 =
                (AnswerFragment2) getSupportFragmentManager().findFragmentById(R.id.fragment_container1);
        answerFragment2.displayResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
