package com.demo.develop.explanemadicineapp.service;


import android.os.Environment;

import com.demo.develop.explanemadicineapp.pojo.Disease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter {

  public static List<Disease> getAllDiseases(String json) {
    final List<Disease> diseases = new ArrayList<Disease>();
    try {
      JSONArray diseasesJSON = new JSONArray(json);

      for (int i = 0; i < diseasesJSON.length(); i++) {
        JSONObject diseaseJSON = diseasesJSON.getJSONObject(i);
        Disease disease = new Disease(diseaseJSON.getString("_id"),diseaseJSON.getString("specialty"),diseaseJSON.getString("condition"));
        diseases.add(disease);
      }

    } catch (JSONException e) {
        e.printStackTrace();
      }
    return diseases;
  }
}
