package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;

import com.demo.develop.explanemadicineapp.pojo.Disease;
import com.demo.develop.explanemadicineapp.service.Adapter;
import com.demo.develop.explanemadicineapp.service.JSONParser;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        changeBounds.setDuration(800);
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(400);
//        Fade fadeIn = new Fade(Fade.IN);
//        fadeIn.setDuration(800);
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set
                .addTransition(fadeOut)
                .addTransition(changeBounds);
//                .addTransition(fadeIn);
        return set;
    }

    private void goScene(int sceneLayout) {
        TransitionManager.go(Scene.getSceneForLayout(container, sceneLayout, this), getT());
        initView();
        setOnClickListener();
        if(sceneLayout == R.layout.search_layout)
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
                showKeyboard(goButton);
                findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goScene(R.layout.activity_main);
                    }
                });
            }

            ;

        });
    }

    private void showKeyboard(SearchView searchView){
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

    }

    private List<Disease> getAllDiseases() throws IOException {
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
        return JSONParser.getAllDiseases(contents.toString());
    }

    private void fillListDiseases(int sceneLayout){
        Adapter adapter = null;
        try {
            adapter = new Adapter(getApplication(), getAllDiseases());
        } catch (IOException e) {
            e.printStackTrace();
        }
        listDiseases.setAdapter(adapter);
    }
}
