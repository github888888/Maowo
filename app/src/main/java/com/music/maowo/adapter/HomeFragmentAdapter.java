package com.music.maowo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017-9-24 0024.
 */

public class HomeFragmentAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public HomeFragmentAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = new TextView(context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        tv.setLayoutParams(params);
        tv.setText(list.get(i));
        tv.setTextColor(0xFF666666);
        return tv;
    }
}
