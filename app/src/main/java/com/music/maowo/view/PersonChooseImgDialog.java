package com.music.maowo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.music.maowo.R;

/**
 * Created by zhoushaopei on 2017/10/21.
 */

public class PersonChooseImgDialog extends Dialog implements View.OnClickListener {

    chooseHeadImg listener;
    private boolean mHaveTakePhoto;
    private TextView takePhotoTxt;
    private TextView chooseImgTxt;

    public PersonChooseImgDialog(Context context, int theme, boolean haveTakePhoto) {
        super(context, R.style.load_dialog);
        mHaveTakePhoto = haveTakePhoto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();

    }
    public void setOnChooseListener(chooseHeadImg listener) {
        this.listener = listener;
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = inflater.inflate(R.layout.choose_head, null);
        setContentView(view, params);
        chooseImgTxt = (TextView) findViewById(R.id.choose_img);
        TextView cancelImg = (TextView) findViewById(R.id.cancel);

        if(mHaveTakePhoto){
            takePhotoTxt = (TextView) findViewById(R.id.take_photo);
            View lineView = findViewById(R.id.lineView);
            lineView.setVisibility(View.VISIBLE);
            takePhotoTxt.setVisibility(View.VISIBLE);
            takePhotoTxt.setOnClickListener(this);
        }
        chooseImgTxt.setOnClickListener(this);
        cancelImg.setOnClickListener(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        lp.width = d.widthPixels;
        lp.height = d.heightPixels;
        dialogWindow.setAttributes(lp);
    }

    public void setText(int position,int ResId){
        switch (position){
            case 1:
                takePhotoTxt.setText(ResId);
                break;
            case 2:
                chooseImgTxt.setText(ResId);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_img:
                if (listener != null){
                    listener.chooseImg();
                }
                break;

            case R.id.take_photo:
                if(listener != null){
                    listener.takePhoto();
                }
            case R.id.cancel:
                PersonChooseImgDialog.this.dismiss();
                break;
        }
    }
    public  interface chooseHeadImg{
        void chooseImg();
        void takePhoto();
    }
}
