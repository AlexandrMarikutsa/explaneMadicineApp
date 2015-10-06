package com.demo.develop.explanemadicineapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.demo.develop.explanemadicineapp.constants.Constants;
import com.demo.develop.explanemadicineapp.pojo.Disease;
import com.demo.develop.explanemadicineapp.service.Adapter;
import com.demo.develop.explanemadicineapp.service.JSONParser;
import com.demo.develop.explanemadicineapp.service.Util;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

import static com.demo.develop.explanemadicineapp.constants.DataBase.DATABASE_NAME;
import static com.demo.develop.explanemadicineapp.constants.NamesOfIcons.BROWSE;
import static com.demo.develop.explanemadicineapp.constants.NamesOfIcons.FAVORITES;
import static com.demo.develop.explanemadicineapp.constants.NamesOfIcons.PROFILE;
import static com.demo.develop.explanemadicineapp.constants.NamesOfIcons.RECENT;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    public static final String TAG = "my_log";

    private ViewGroup container;
    private SearchView searchViewButton;
    private ListView listDiseases;
    private LinearLayout searchViewUpLayer;
    private ImageButton profile;
    private LinearLayout browseLayout;
    private LinearLayout recentLayout;
    private RelativeLayout favoritesLayout;
    private TextView numberOfNotificationsMain;
    private TextView numberOfNotificationsFavorites;
    private ImageButton notificationsButton;

    private Adapter adapter;
    private Integer numberOfNotifications = Constants.NUMBER_OF_NOTIFICATIONS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(R.layout.activity_main);
        setOnClickListener(R.layout.activity_main);
    }

    private TransitionSet getTransitionSet() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(Constants.DURATION_FOR_BOUNDS);
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(Constants.DURATION_FOR_FADE_OUT);
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(Constants.DURATION_FOR_FADE_IN);
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
        if (sceneLayout == R.layout.search_layout) {
            try {
                fillListDiseases(adapter = new Adapter(this, getAllDiseases()));
                searchViewButton.requestFocus();
                searchViewButton.post(new Runnable() {
                    @Override
                    public void run() {
                        showSoftInputUnchecked();
                    }
                });
            } catch (IOException e) {
                Log.e(TAG, Util.nullToEmpty(e.getMessage()));
            }
        }
    }

    private void showSoftInputUnchecked() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            Method showSoftInputUnchecked = null;
            try {
                showSoftInputUnchecked = imm.getClass()
                        .getMethod("showSoftInputUnchecked", int.class, ResultReceiver.class);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, Util.nullToEmpty(e.getMessage()));
            }

            if (showSoftInputUnchecked != null) {
                try {
                    showSoftInputUnchecked.invoke(imm, 0, null);
                } catch (Exception e) {
                    Log.e(TAG, Util.nullToEmpty(e.getMessage()));
                }
            }
        }
    }

    private void initView(int layout) {
        if (layout == R.layout.activity_main) {
            profile = (ImageButton) findViewById(R.id.profile);
            browseLayout = (LinearLayout) findViewById(R.id.browse_layout);
            recentLayout = (LinearLayout) findViewById(R.id.recent_layout);
            notificationsButton = (ImageButton) findViewById(R.id.notifications);
            favoritesLayout = (RelativeLayout) findViewById(R.id.favorites_layout);
            numberOfNotificationsFavorites = (TextView) findViewById(R.id.num_of_notifications);
            numberOfNotificationsMain = (TextView) findViewById(R.id.num_of_notifications_main);
            numberOfNotificationsMain.setText(numberOfNotifications.toString());
            numberOfNotificationsFavorites.setText(numberOfNotifications.toString());
        }
        ;
        container = (ViewGroup) findViewById(R.id.container);
        searchViewButton = (SearchView) findViewById(R.id.searchView);
        listDiseases = (ListView) findViewById(R.id.list_diseases);
        searchViewUpLayer = (LinearLayout) findViewById(R.id.search_view_up_layer);
    }

    private void setOnClickListener(int layout) {
        if (layout == R.layout.activity_main) {
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.profile:
                            Log.e(TAG, PROFILE);
                            break;
                        case R.id.browse_layout:
                            Log.e(TAG, BROWSE);
                            break;
                        case R.id.favorites_layout:
                            Log.e(TAG, FAVORITES);
                            break;
                        case R.id.recent_layout:
                            Log.e(TAG, RECENT);
                            break;
                        case R.id.notifications:
                            changeNumberOfNotifications();
                            break;
                        case R.id.num_of_notifications_main:
                            changeNumberOfNotifications();
                            break;
                    }
                }
            };
            notificationsButton.setOnClickListener(clickListener);
            numberOfNotificationsMain.setOnClickListener(clickListener);
            profile.setOnClickListener(clickListener);
            browseLayout.setOnClickListener(clickListener);
            recentLayout.setOnClickListener(clickListener);
            favoritesLayout.setOnClickListener(clickListener);
        }
        if (searchViewUpLayer != null) {
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

    private void changeNumberOfNotifications() {
        numberOfNotifications = 0;
        numberOfNotificationsFavorites.setText(numberOfNotifications.toString());
        numberOfNotificationsMain.setText(numberOfNotifications.toString());
    }

    private List<Disease> getAllDiseases() throws IOException {
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(getAssets().open(DATABASE_NAME)));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            Log.e(TAG, Util.nullToEmpty(ex.getMessage()));
        }
        return JSONParser.getAllDiseases(contents.toString());
    }

    private void fillListDiseases(final Adapter adapter) {
        onQueryTextChange("");
        listDiseases.setTextFilterEnabled(false);
        searchViewButton.setIconifiedByDefault(false);
        searchViewButton.setOnQueryTextListener(this);
        searchViewButton.setIconified(true);
        listDiseases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getAdapterDiseasesList().get(+position);
                Log.e(TAG, adapter.getAdapterDiseasesList().get(+position).getCondition());
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
            adapter.filter(newText);
            listDiseases.setAdapter(adapter);
            return false;
        } else {
            adapter.filter("");
            listDiseases.setAdapter(adapter);
        }

        return false;
    }
}
