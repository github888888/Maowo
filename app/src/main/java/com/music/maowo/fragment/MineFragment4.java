package com.music.maowo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.R;
import com.music.maowo.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class MineFragment4 extends Fragment implements View.OnClickListener {
    @BindView(R.id.civ_header)
    CircleImageView civ_header;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.iv_user_setting)
    ImageView iv_user_setting;
    @BindView(R.id.tv_user_edit)
    TextView tv_user_edit;
    @BindView(R.id.tv_submit_article)
    TextView tv_submit_article;
    @BindView(R.id.tv_publish_topic)
    TextView tv_publish_topic;
    @BindView(R.id.tv_join_topic)
    TextView tv_join_topic;
    @BindView(R.id.tv_mine_reply)
    TextView tv_mine_reply;
    @BindView(R.id.tv_mine_private_letter)
    TextView tv_mine_private_letter;
    @BindView(R.id.tv_mine_collection)
    TextView tv_mine_collection;
    @BindView(R.id.tv_mine_system_message)
    TextView tv_mine_system_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        iv_user_setting.setOnClickListener(this);
        tv_user_edit.setOnClickListener(this);
        tv_submit_article.setOnClickListener(this);
        tv_publish_topic.setOnClickListener(this);
        tv_join_topic.setOnClickListener(this);
        tv_mine_reply.setOnClickListener(this);
        tv_mine_private_letter.setOnClickListener(this);
        tv_mine_collection.setOnClickListener(this);
        tv_mine_system_message.setOnClickListener(this);

        Glide.with(getActivity()).load("http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg")
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                civ_header.setImageBitmap(resource);
            }
        });
        tv_user_name.setText("小龙哥");
    }

    @Override
    public void onClick(View view) {
        if (view == iv_user_setting) {
            Toast.makeText(getActivity(), "iv_user_setting", Toast.LENGTH_LONG).show();
        } else if (view == tv_user_edit) {
            Toast.makeText(getActivity(), "tv_user_edit", Toast.LENGTH_LONG).show();
        } else if (view == tv_submit_article) {
            Toast.makeText(getActivity(), "tv_submit_article", Toast.LENGTH_LONG).show();
        } else if (view == tv_publish_topic) {
            Toast.makeText(getActivity(), "tv_publish_topic", Toast.LENGTH_LONG).show();
        } else if (view == tv_join_topic) {
            Toast.makeText(getActivity(), "tv_join_topic", Toast.LENGTH_LONG).show();
        } else if (view == tv_mine_reply) {
            Toast.makeText(getActivity(), "tv_mine_reply", Toast.LENGTH_LONG).show();
        } else if (view == tv_mine_private_letter) {
            Toast.makeText(getActivity(), "tv_mine_private_letter", Toast.LENGTH_LONG).show();
        } else if (view == tv_mine_collection) {
            Toast.makeText(getActivity(), "tv_mine_collection", Toast.LENGTH_LONG).show();
        } else if (view == tv_mine_system_message) {
            Toast.makeText(getActivity(), "tv_mine_system_message", Toast.LENGTH_LONG).show();
        }
    }
}
