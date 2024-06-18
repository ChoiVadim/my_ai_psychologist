package com.choivadim.my_ai_psychologist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter {

    private Context context;
    private String[] tests;
    private int[] icons;

    public TestAdapter(Context context, String[] tests, int[] icons) {
        this.context = context;
        this.tests = tests;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return tests.length;
    }

    @Override
    public Object getItem(int position) {
        return tests[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_test, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.test_icon);
        TextView name = convertView.findViewById(R.id.test_name);

        icon.setImageResource(icons[position]);
        name.setText(tests[position]);

        return convertView;
    }
}
