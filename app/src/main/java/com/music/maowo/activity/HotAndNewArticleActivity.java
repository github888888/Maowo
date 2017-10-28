package com.music.maowo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.adapter.HotAndNewArticelAdapter;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.SetListResponse;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-10-8 0008.
 */

@Layout(R.layout.activity_hot_and_new_layotu)
public class HotAndNewArticleActivity extends BaseActivity {
    public static final String TYPE_SELECTED = "type_selected";
    public static final int TYPE_HOT = 1; // 热门文章
    public static final int TYPE_NEW = 2; // 最新文章

    @BindView(R.id.tv_tttle)
    TextView tv_tttle;
    @BindView(R.id.tv_indicator)
    TextView tv_indicator;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private int currentType;
    private HotAndNewArticelAdapter adapter;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        Intent intent = getIntent();
        currentType = intent.getIntExtra(TYPE_SELECTED, TYPE_HOT);
        tv_tttle.setText(currentType == TYPE_HOT ? "热门排行榜" : "最新文章");
        tv_indicator.setText("文章分类");

        gotoRequestData();
    }

    private void gotoRequestData() {
        Observable<SetListResponse> observable = RetrofitManager.getServices().getSetArticleList(1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<SetListResponse>(){
                    @Override
                    public void onNext(SetListResponse o) {
                        if (o == null || o.getData() == null) return;
                        if (o.getData().getFile() == null || o.getData().getFile().size() == 0) return;
                        adapter = new HotAndNewArticelAdapter(o.getData().getFile(), HotAndNewArticleActivity.this);
                        lv_content.setAdapter(adapter);
                    }
                });
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }
}
