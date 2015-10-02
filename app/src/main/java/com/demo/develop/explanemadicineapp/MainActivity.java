package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements
        SearchView.OnQueryTextListener{
    private ViewGroup container;
    private SearchView searchViewButton;
    private ListView listDiseases;
    private LinearLayout searchViewUpLayer;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setOnClickListener();
    }

    private TransitionSet getT() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(600);
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(800);
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(800);
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
        if(sceneLayout == R.layout.search_layout)
            try {
                fillListDiseases(adapter = new Adapter(getApplication(), getAllDiseases()));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void initView() {
        container = (ViewGroup) findViewById(R.id.container);
        searchViewButton = (SearchView) findViewById(R.id.searchView);
        listDiseases = (ListView) findViewById(R.id.list_diseases);
        searchViewUpLayer = (LinearLayout) findViewById(R.id.search_view_up_layer);
//        TextView searchText = (TextView) searchViewButton.findViewById(android.support.v7.appcompat.R.id.search_src_text);
    }

    private void setOnClickListener() {
        if(searchViewUpLayer != null){
            searchViewUpLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goScene(R.layout.search_layout);
                    findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchViewButton.clearFocus();
                            goScene(R.layout.activity_main);
                        }
                    });
                }
            });
        }else {
            searchViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showKeyboard(searchViewButton);
                }
            });
        }
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

    private void fillListDiseases(Adapter adapter){
        listDiseases.setAdapter(adapter);
        listDiseases.setTextFilterEnabled(false);
        searchViewButton.setIconifiedByDefault(false);
        searchViewButton.setOnQueryTextListener(this);
        searchViewButton.setIconified(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText)) {
            adapter.filter(newText.toString());
            listDiseases.setAdapter(adapter);
            return false;
        }

        return false;
    }
}
