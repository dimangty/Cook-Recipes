package com.example.dima.cookrecipes.utils;

import android.support.v4.app.Fragment;

import java.util.LinkedList;

/**
 * Created by dima on 02.07.16.
 */
public class FragmentsStack {
    private LinkedList<Fragment> stack = new LinkedList<>();
    private static FragmentsStack instance;
    private boolean timer;

    public static FragmentsStack getStack() {
        FragmentsStack localInstance = instance;
        if (localInstance == null) {
            synchronized (FragmentsStack.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FragmentsStack();
                }
            }
        }
        return localInstance;
    }

    public FragmentsStack() {
        timer = false;
    }

    public void pushFragment(Fragment fragment) {
        stack.add(fragment);
    }

    public void popFragment() {
        stack.removeLast();
    }

    public Fragment getLastFragment() {

        return stack.getLast();
    }

    public int getSize() {
        return stack.size();
    }

    public void enableTimer()
    {
        timer = true;
    }

    public void disableTimer()
    {
        timer = false;
    }

    public Boolean getTimerState()
    {
        return timer;
    }

}
