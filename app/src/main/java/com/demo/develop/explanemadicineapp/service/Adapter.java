package com.demo.develop.explanemadicineapp.service;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.develop.explanemadicineapp.R;
import com.demo.develop.explanemadicineapp.pojo.Disease;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter extends BaseAdapter {
    private List<Disease> diseases;
    private ArrayList<Disease> arraylist;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(Context context, List<Disease> diseases) {
        this.diseases = diseases;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Disease>();
        this.arraylist.addAll(diseases);
    }

    static class ViewHolder {
        TextView diseaseCondition;
        ImageView conditionNext;
    }

    @Override
    public int getCount() {
        return diseases.size();
    }

    @Override
    public Disease getItem(int position) {
        return diseases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.disease_view,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.diseaseCondition = (TextView) convertView.findViewById(R.id.condition);
            viewHolder.conditionNext = (ImageView) convertView.findViewById(R.id.next);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Disease disease =  getItem(position);
        viewHolder.diseaseCondition.setText(disease.getCondition());
        return convertView;
    }

    public List<Disease> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        diseases.clear();
        if (charText.length() == 0) {
            diseases.addAll(arraylist);
        } else {
            for (Disease disease : arraylist) {
                if (disease.getCondition().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    diseases.add(disease);
                }
            }
        }
        return diseases;
    }

}