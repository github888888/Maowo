package com.music.maowo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.anno.Layout;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.net.response.SubmitArticleResponse;
import com.music.maowo.net.response.UserInfoResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-10-17 0017.
 */

@Layout(R.layout.activity_mine_common_layout)
public class SubmitArticleActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
//    private List<SubmitArticleInfo> list;
    // test
    private List<String> images;


    public static void actionInstance(FragmentActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SubmitArticleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initDataAndListener() {
        tv_title.setText("提交的文章");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
//        list = new ArrayList<>();
//        list.add(new SubmitArticleInfo(1, "title1", "content1", "2017-09-08", true));
//        list.add(new SubmitArticleInfo(2, "title2", "content1", "2017-09-07", false));
//        list.add(new SubmitArticleInfo(3, "title3", "content1", "2017-07-08", true));
        adapter = new SubmitArticleAdapter(null);
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                gotoArticleDetailActivity(adapter.getItem(i));
            }
        });

        Observable<SubmitArticleResponse> observable =
                RetrofitManager.getServices().getArticleSubmit(Constants.access_token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wapper);

//        String str = RetrofitManager.getServices().articleSubmit(Constants.access_token);
    }

    private void gotoArticleDetailActivity(SubmitArticleInfo submitArticleInfo) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.INFO_TAG, submitArticleInfo);
        startActivity(intent);
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }

    ObserverWapper wapper = new ObserverWapper<SubmitArticleResponse>() {
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onNext(SubmitArticleResponse response) {
            if(response == null || response.getData() == null || response.getReasult() != 1) return;
            SubmitArticleResponse.ArticleInfo info = response.getData();
            List<SubmitArticleResponse.ArticleInfo.Articles> articles = info.getArticle();
            adapter.notifyDataChanges(articles);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    };

    private class SubmitArticleAdapter extends BaseAdapter {

        private List<SubmitArticleResponse.ArticleInfo.Articles> list;

        SubmitArticleAdapter(List<SubmitArticleResponse.ArticleInfo.Articles> articles) {
            if (articles == null) articles = new ArrayList<SubmitArticleResponse.ArticleInfo.Articles>();
            this.list = articles;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public SubmitArticleResponse.ArticleInfo.Articles getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Viewholder viewholder;
            if (null == view) {
                view = View.inflate(getApplicationContext(), R.layout.activity_submit_article_item_layout, null);
                viewholder = new Viewholder();
                viewholder.tv_submit_type = view.findViewById(R.id.tv_submit_type);
                viewholder.tv_submit_title = view.findViewById(R.id.tv_submit_title);
                viewholder.tv_submit_pass_description = view.findViewById(R.id.tv_submit_pass_description);
                viewholder.tv_submit_time = view.findViewById(R.id.tv_submit_time);
                view.setTag(viewholder);
            } else {
                viewholder = (Viewholder) view.getTag();
            }
            SubmitArticleResponse.ArticleInfo.Articles info = getItem(i);

            Random random = new Random();
            int type = random.nextInt(3)%(3-1+1) + 1;

            boolean isPass = info.getArt_state().equals("1");
            switch (type) {
                case 1:
                    viewholder.tv_submit_type.setText("校园");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type1_shape);
                    break;
                case 2:
                    viewholder.tv_submit_type.setText("故事");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type2_shape);
                    break;
                case 3:
                    viewholder.tv_submit_type.setText("旅游");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type3_shape);
                    break;
            }
            viewholder.tv_submit_title.setText(info.getArt_titile());
            viewholder.tv_submit_pass_description.setText(isPass ? "已通过" : "未通过");
//            viewholder.tv_submit_time.setText("提交时间： " + info.submitTime);
            return view;
        }

        private void notifyDataChanges(List<SubmitArticleResponse.ArticleInfo.Articles> articles) {
            if (articles == null) articles = new ArrayList<SubmitArticleResponse.ArticleInfo.Articles>();
            this.list = articles;
            notifyDataSetChanged();
        }
    }

    public static class Viewholder {
        public TextView tv_submit_type;
        public TextView tv_submit_title;
        public TextView tv_submit_pass_description;
        public TextView tv_submit_time;

    }

    public static class SubmitArticleInfo implements Serializable{
        public int type;
        public String title;
        public String content;
        public String submitTime;

        public SubmitArticleInfo(int type, String title, String content, String submitTime, boolean isPass) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.submitTime = submitTime;
            this.isPass = isPass;
        }

        public boolean isPass;
    }
}
