package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.List;

public class MainActivity extends Activity implements
        SearchView.OnQueryTextListener{
    private ViewGroup container;
    private SearchView searchViewButton;
    private ListView listDiseases;
    private LinearLayout searchViewUpLayer;
    private Adapter adapter;
    private String TAG = "my_log";
    private ImageButton profile;
    private LinearLayout browseLayout;
    private LinearLayout recentLayout;
    private RelativeLayout favoritesLayout;
    private View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(R.layout.activity_main);
        setOnClickListener(R.layout.activity_main);
    }

    private TransitionSet getTransitionSet() {
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
        TransitionManager.go(Scene.getSceneForLayout(container, sceneLayout, this), getTransitionSet());
        initView(sceneLayout);
        setOnClickListener(sceneLayout);
        if(sceneLayout == R.layout.search_layout)
            try {
                fillListDiseases(adapter = new Adapter(getApplication(), getAllDiseases()));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void initView(int layout) {
        if (layout == R.layout.activity_main){
            profile = (ImageButton) findViewById(R.id.profile);
            browseLayout = (LinearLayout) findViewById(R.id.browse_layout);
            recentLayout = (LinearLayout) findViewById(R.id.recent_layout);
            favoritesLayout = (RelativeLayout) findViewById(R.id.favorites_layout);
        };
        container = (ViewGroup) findViewById(R.id.container);
        searchViewButton = (SearchView) findViewById(R.id.searchView);
        listDiseases = (ListView) findViewById(R.id.list_diseases);
        searchViewUpLayer = (LinearLayout) findViewById(R.id.search_view_up_layer);
     }

    private void setOnClickListener(int layout) {
        if(layout == R.layout.activity_main) {
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.profile:
                            Log.e(TAG, "PROFILE");
                            break;
                        case R.id.browse_layout:
                            Log.e(TAG, "BROWSE");
                            break;
                        case R.id.favorites_layout:
                            Log.e(TAG, "FAVORITES");
                            break;
                        case R.id.recent_layout:
                            Log.e(TAG, "RECENT");
                            break;
                    }
                }
            };
            profile.setOnClickListener(clickListener);
            browseLayout.setOnClickListener(clickListener);
            recentLayout.setOnClickListener(clickListener);
            favoritesLayout.setOnClickListener(clickListener);
        }
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
        }
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

    private void fillListDiseases(final Adapter adapter){
        listDiseases.setAdapter(adapter);
        listDiseases.setTextFilterEnabled(false);
        searchViewButton.setIconifiedByDefault(false);
        searchViewButton.setOnQueryTextListener(this);
        searchViewButton.setIconified(true);
        listDiseases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getDiseases().get(+position);
                Log.e(TAG, adapter.getDiseases().get(+position).getCondition());
            }
        });
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
