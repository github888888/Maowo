package com.music.maowo.fragment;

import android.content.Intent;
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
import com.music.maowo.activity.JoinTopicActivity;
import com.music.maowo.activity.MyCollectionListActivity;
import com.music.maowo.activity.MyMessageListActivity;
import com.music.maowo.activity.MyReplyListActivity;
import com.music.maowo.activity.PublishTopicActivity;
import com.music.maowo.activity.SubmitArticleActivity;
import com.music.maowo.activity.SystemMessageListActivity;
import com.music.maowo.activity.mine.SettingActivity;
import com.music.maowo.activity.mine.UserInfoActivity;
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
        Intent intent;
        if (view == iv_user_setting) {
            intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        } else if (view == tv_user_edit) {
            intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        } else if (view == tv_submit_article) {
            intent = new Intent(getActivity(), SubmitArticleActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_publish_topic) {
            intent = new Intent(getActivity(), PublishTopicActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_join_topic) {
            intent = new Intent(getActivity(), JoinTopicActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_mine_reply) {
            intent = new Intent(getActivity(), MyReplyListActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_mine_private_letter) {
            intent = new Intent(getActivity(), MyMessageListActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_mine_collection) {
            intent = new Intent(getActivity(), MyCollectionListActivity.class);
            getActivity().startActivity(intent);
        } else if (view == tv_mine_system_message) {
            intent = new Intent(getActivity(), SystemMessageListActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
