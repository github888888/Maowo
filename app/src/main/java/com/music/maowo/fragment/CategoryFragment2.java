package com.music.maowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.music.maowo.R;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class CategoryFragment2 extends Fragment {

    public CategoryFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText("第二个Fragment");
        Log.e("HEHE", "2日狗");
        return view;
    }
}
