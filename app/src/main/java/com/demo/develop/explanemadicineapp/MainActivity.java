package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class MainActivity extends Activity {
    private ViewGroup container;
    private SearchView goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setOnClickListener();
    }

    private TransitionSet getT() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(1800);
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(1800);
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(1800);
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set
                .addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);
        return set;
    }

    private void goScene(int sceneLayout) {
        TransitionManager.go(Scene.getSceneForLayout(container, sceneLayout, this),getT());
        initView();
        setOnClickListener();
    }

    private void initView() {
        container = (ViewGroup) findViewById(R.id.container);
        goButton = (SearchView) findViewById(R.id.searchView);
    }

    private void setOnClickListener() {
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goScene(R.layout.search_layout);

                findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goScene(R.layout.activity_main);
                    }
                });
            };

        });
    }

 }
