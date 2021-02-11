package com.u1tramarinet.mytodoapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;
import com.u1tramarinet.mytodoapp.R;
import com.u1tramarinet.mytodoapp.ui.create.CreateFragment;
import com.u1tramarinet.mytodoapp.ui.detail.DetailFragment;
import com.u1tramarinet.mytodoapp.ui.list.done.DoneFragment;
import com.u1tramarinet.mytodoapp.ui.list.progress.ProgressFragment;
import com.u1tramarinet.mytodoapp.ui.setting.SettingFragment;
import com.u1tramarinet.mytodoapp.util.ActivityUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ProgressFragment.Listener, DoneFragment.Listener, CreateFragment.Listener, SettingFragment.Listener, DetailFragment.Listener {
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentWithBackStack(CreateFragment.newInstance(), "Create");
            }
        });
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        showFragment(ProgressFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showFragmentWithBackStack(SettingFragment.newInstance(), "Setting");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(MainActivity.class.getSimpleName(), "#onBackPressed() backStack's count=" + backStackCount);
        if (0 == backStackCount) {
            showBottomUi();
            updateTitle("Todo list");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_progress) {
            showFragment(ProgressFragment.newInstance());
        } else if (itemId == R.id.action_done) {
            showFragment(DoneFragment.newInstance());
        } else {
            return false;
        }
        return true;
    }

    private void showFragment(@NonNull Fragment fragment) {
        ActivityUtils.showFragment(getSupportFragmentManager(), fragment, R.id.container);
        updateTitle("Todo list");
    }

    private void showFragmentWithBackStack(@NonNull Fragment fragment, @NonNull String title) {
        ActivityUtils.showFragment(getSupportFragmentManager(), fragment, R.id.container, true);
        hideBottomUi();
        updateTitle(title);
    }

    private void updateTitle(@NonNull String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showBottomUi() {
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        fadeIn(bottomAppBar, shortAnimationDuration);
        fadeIn(floatingActionButton, shortAnimationDuration);
    }

    private void hideBottomUi() {
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        fadeOut(bottomAppBar, shortAnimationDuration);
        fadeOut(floatingActionButton, shortAnimationDuration);
    }

    private void fadeIn(@NonNull View contentView, int duration) {
        float height = contentView.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "translationY", height, 0);
        animator.setIntValues(android.R.interpolator.fast_out_slow_in);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                contentView.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private void fadeOut(@NonNull View contentView, int duration) {
        float height = contentView.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "translationY", height);
        animator.setIntValues(android.R.interpolator.fast_out_slow_in);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                contentView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    @Override
    public void onEvent(ProgressFragment.Event event) {

    }

    @Override
    public void onEvent(DoneFragment.Event event) {

    }

    @Override
    public void onEvent(CreateFragment.Event event) {

    }

    @Override
    public void onEvent(SettingFragment.Event event) {

    }

    @Override
    public void onEvent(DetailFragment.Event event) {

    }
}