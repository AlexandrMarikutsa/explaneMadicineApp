package com.demo.develop.explanemadicineapp.service;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.develop.explanemadicineapp.R;
import com.demo.develop.explanemadicineapp.pojo.Disease;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter extends BaseAdapter {
    private List<Disease> adapterDiseasesList;
    private ArrayList<Disease> generalDiseasesList;
    private LayoutInflater layoutInflater;
    private Activity context;
    private String charText;

    public Adapter(Activity context, List<Disease> diseases) {
        this.adapterDiseasesList = diseases;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.generalDiseasesList = new ArrayList<Disease>();
        this.generalDiseasesList.addAll(diseases);
    }

    static class ViewHolder {
        private TextView diseaseCondition;
    }

    @Override
    public int getCount() {
        return adapterDiseasesList.size();
    }

    @Override
    public Disease getItem(int position) {
        return adapterDiseasesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.disease_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.diseaseCondition = (TextView) convertView.findViewById(R.id.condition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Disease disease = getItem(position);
        if (!TextUtils.isEmpty(charText)) {
            viewHolder.diseaseCondition.setText(makeBoldText(disease));
        } else {
            viewHolder.diseaseCondition.setText(disease.getCondition());
        }
        ;

        return convertView;
    }

    public List<Disease> filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        adapterDiseasesList.clear();
        if (charText.length() != 0) {
            for (Disease disease : generalDiseasesList) {
                if (disease.getCondition().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    adapterDiseasesList.add(disease);
                }
            }
        }
        return adapterDiseasesList;
    }

    public SpannableString makeBoldText(Disease disease) {

        String condition = disease.getCondition();
        String conditionLowerCase = condition.toLowerCase();
        String chartextLowerCase = charText.toLowerCase();

        SpannableString conditionChanged = new SpannableString(condition);
        int startindex = conditionLowerCase.indexOf(chartextLowerCase);
        if (startindex >= 0) {
            conditionChanged.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    startindex,
                    startindex + charText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            conditionChanged.setSpan(new ForegroundColorSpan(ContextCompat
                            .getColor(context, R.color.searchview_bold_text)), startindex,
                    startindex + charText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return conditionChanged;
        }
        return null;
    }

    public List<Disease> getAdapterDiseasesList() {
        return adapterDiseasesList;
    }
}