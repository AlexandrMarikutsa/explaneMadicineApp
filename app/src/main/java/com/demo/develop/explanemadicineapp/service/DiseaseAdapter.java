package com.demo.develop.explanemadicineapp.service;


import android.os.Environment;

import com.demo.develop.explanemadicineapp.pojo.Disease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter {

  public static List<String> getAllDiseases(String json) {
    final List<String> diseases = new ArrayList<String>();
    try {
      JSONArray diseasesJSON = new JSONArray(json);

      for (int i = 0; i < diseasesJSON.length(); i++) {
        JSONObject diseaseJSON = diseasesJSON.getJSONObject(i);
        diseases.add(diseaseJSON.getString("condition"));
      }

    } catch (JSONException e) {
        e.printStackTrace();
      }
    return diseases;
  }
}
