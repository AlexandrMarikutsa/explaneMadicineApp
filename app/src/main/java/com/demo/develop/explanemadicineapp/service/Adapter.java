package com.demo.develop.explanemadicineapp.service;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
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
    private Activity context;
    String charText;

    public Adapter(Activity context, List<Disease> diseases) {
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
        if(charText != null) {
            viewHolder.diseaseCondition.setText(makeBoldText(disease));
        }else {
            viewHolder.diseaseCondition.setText(disease.getCondition());
        };

        return convertView;
    }

    public List<Disease> filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        diseases.clear();
        if (charText.length() != 0) {
            for (Disease disease : arraylist) {
                if (disease.getCondition().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    diseases.add(disease);
                }
            }
        }
        return diseases;
    }
    public SpannableString makeBoldText(Disease disease){

        String condition = disease.getCondition();
        String conditionLowerCase = condition.toLowerCase();
        String chartextLowerCase = charText.toLowerCase();

        SpannableString conditionChanged = new SpannableString(condition);
        int startindex = conditionLowerCase.indexOf(chartextLowerCase);
        if(startindex>=0) {
            conditionChanged.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    startindex,
                    startindex + charText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            conditionChanged.setSpan(new ForegroundColorSpan(context
                            .getResources().getColor(R.color.searchview_bold_text)), startindex,
                    startindex + charText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return conditionChanged;
        }
        return null;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }
}