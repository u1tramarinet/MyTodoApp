package com.u1tramarinet.mytodoapp.util;

import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    private ActivityUtils() {
    }

    public static void showFragment(FragmentManager manager, Fragment fragment, @IdRes int containerId) {
        showFragment(manager, fragment, containerId, false);
    }

    public static void showFragment(FragmentManager manager, Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (addToBackStack) {
            transaction.replace(containerId, fragment);
            transaction.addToBackStack(null);
        } else {
            transaction.replace(containerId, fragment);
        }
        transaction.setReorderingAllowed(true);
        transaction.commit();
        outputFragmentBackStack(manager);
    }

    private static void outputFragmentBackStack(FragmentManager manager) {
        int count = manager.getBackStackEntryCount();
        Log.d(ActivityUtils.class.getSimpleName(), String.format("FragmentBackStack's count=%d", count));
        for (int i = 0; i < count; i++) {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(i);
            Log.d(ActivityUtils.class.getSimpleName(), String.format("[#%d] id=%d name=%s", count, entry.getId(), entry.getName()));
        }
    }
}
