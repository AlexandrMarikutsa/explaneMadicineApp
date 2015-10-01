package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.demo.develop.explanemadicineapp.service.DiseaseAdapter;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    private ViewGroup container;
    private SearchView goButton;
    private ListView listDiseases;

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
        TransitionManager.go(Scene.getSceneForLayout(container, sceneLayout, this), getT());
        initView();
        setOnClickListener();
        fillListDiseases(sceneLayout);
    }

    private void initView() {
        container = (ViewGroup) findViewById(R.id.container);
        goButton = (SearchView) findViewById(R.id.searchView);
        listDiseases = (ListView) findViewById(R.id.list_diseases);
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
    private List<String> loadJSONFromAsset() throws IOException {
        String json = null;
        BufferedReader br = null;
        StringBuilder contents = new StringBuilder();

            try {
                BufferedReader input =  new BufferedReader(new InputStreamReader(getAssets().open("conditions.json")));
                try {
                    String line = null;
                    while (( line = input.readLine()) != null) {
                        contents.append(line);
                    }
                }
                finally {
                    input.close();
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        return DiseaseAdapter.getAllDiseases(contents.toString());
    }

    private void fillListDiseases(int sceneLayout){
        if(sceneLayout == R.layout.search_layout){
            ArrayAdapter<String> adapter = null;
            try {
                adapter = new ArrayAdapter<String>(this, R.layout.search_layout, loadJSONFromAsset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listDiseases.setAdapter(adapter);
        }
    }
}
