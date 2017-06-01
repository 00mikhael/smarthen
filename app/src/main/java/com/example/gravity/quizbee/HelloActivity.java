package com.example.gravity.quizbee;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This activity hosts the hello fragment
 */

public class HelloActivity extends SingleFragmentActivity {
    // Calls the newInstance method in HelloFragment
    @Override
    protected Fragment createFragment() {
        return HelloFragment.newInstance();
    }

    // Creates options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Checks which options menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
