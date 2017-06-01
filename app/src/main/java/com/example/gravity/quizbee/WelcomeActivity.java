package com.example.gravity.quizbee;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Gravity on 5/23/2017.
 */

public class WelcomeActivity extends SingleFragmentActivity {

    private static final String EXTRA_USER_NAME = "com.example.gravity.quizbee.username";

    public static Intent newIntent(Context packageContext, String userName) {
        Intent intent = new Intent(packageContext, WelcomeActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String userName = getIntent()
                .getStringExtra(EXTRA_USER_NAME);
        return WelcomeFragment.newInstance(userName);
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
