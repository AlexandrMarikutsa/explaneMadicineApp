package com.demo.develop.explanemadicineapp.service;


import android.util.Log;

import com.demo.develop.explanemadicineapp.pojo.Disease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.demo.develop.explanemadicineapp.constants.DataBase.CONDITION;
import static com.demo.develop.explanemadicineapp.constants.DataBase.ID;
import static com.demo.develop.explanemadicineapp.constants.DataBase.SPECIALTY;

public class JSONParser {

    public static List<Disease> getAllDiseases(String json) {
        final List<Disease> diseases = new ArrayList<Disease>();
        try {
            JSONArray diseasesJSON = new JSONArray(json);
            int diseaseJSONLength = diseasesJSON.length();
            for (int i = 0; i < diseaseJSONLength; i++) {
                JSONObject diseaseJSON = diseasesJSON.getJSONObject(i);
                Disease disease = new Disease(diseaseJSON.getString(ID), diseaseJSON.getString(SPECIALTY),
                        diseaseJSON.getString(CONDITION));
                diseases.add(disease);
            }

        } catch (JSONException e) {
            Log.e(JSONParser.class.getSimpleName(), Util.nullToEmpty(e.getMessage()));
        }
        return diseases;
    }
}
