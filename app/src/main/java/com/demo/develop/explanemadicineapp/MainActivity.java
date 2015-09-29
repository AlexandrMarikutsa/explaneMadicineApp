package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        final Scene scene = Scene.getSceneForLayout(container,
                R.layout.search_layout, this);

        SearchView goButton = (SearchView) findViewById(R.id.searchView);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet set = new TransitionSet();
                set.addTransition(new Fade());
                set.addTransition(new ChangeBounds());
                // выполняться они будут одновременно
                set.setOrdering(TransitionSet.ORDERING_TOGETHER);
                // уставим свою длительность анимации
                set.setDuration(500);
                // и изменим Interpolator
                set.setInterpolator(new AccelerateInterpolator());
                TransitionManager.go(scene);
            }
        });
    }



 }
