package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        final Scene scene = Scene.getSceneForLayout(container,
                R.layout.search_layout, this);

        ImageView goButton = (ImageView) findViewById(R.id.browse);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene);
            }
        });
    }



 }
