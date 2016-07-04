package com.example.dima.cookrecipes;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.FrameLayout;

import com.example.dima.cookrecipes.ui.CategoryFragment;
import com.example.dima.cookrecipes.ui.TimerFragment;
import com.example.dima.cookrecipes.utils.FragmentsStack;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


public class MainActivity extends AppCompatActivity {
    FrameLayout mContainer;
    FragmentManager mFragmentManager;
    TimerFragment mTimerFragment;
    final static String TAG_1 = "Category_Fragment";
    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frame_activity);
        mContainer = (FrameLayout) findViewById(R.id.container);

        mFragmentManager = getSupportFragmentManager();

        if (FragmentsStack.getStack().getSize() == 0) {
            CategoryFragment categoryFragment = new CategoryFragment();
            FragmentsStack.getStack().pushFragment(categoryFragment);

            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            fragmentTransaction.add(R.id.container, categoryFragment, TAG_1);
            fragmentTransaction.commit();
        }

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_category) {

                    hideTimer();
                    FragmentsStack.getStack().disableTimer();
                } else if (menuItemId == R.id.bb_menu_timer) {

                    showTimer();
                    FragmentsStack.getStack().enableTimer();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_category) {

                } else if (menuItemId == R.id.bb_menu_timer) {

                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (FragmentsStack.getStack().getTimerState()) {
            finish();
            return;
        }

        int stackSize = FragmentsStack.getStack().getSize();
        if (stackSize > 1) {
            FragmentsStack.getStack().popFragment();
            Fragment fragment = FragmentsStack.getStack().getLastFragment();

            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        } else if (stackSize == 1) {
            Fragment fragment = FragmentsStack.getStack().getLastFragment();

            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();

            FragmentsStack.getStack().popFragment();

            finish();
        } else
            super.onBackPressed();
    }

    void showTimer() {
        if (FragmentsStack.getStack().getTimerState())
            return;

        if (mTimerFragment == null) {
            mTimerFragment = new TimerFragment();


            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            fragmentTransaction.replace(R.id.container, mTimerFragment);
            fragmentTransaction.commit();

        }
        else
        {
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            fragmentTransaction.replace(R.id.container, mTimerFragment);
            fragmentTransaction.commit();

        }
    }

    void hideTimer() {
        if (!FragmentsStack.getStack().getTimerState())
            return;

        Fragment fragment = FragmentsStack.getStack().getLastFragment();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();



    }
}
