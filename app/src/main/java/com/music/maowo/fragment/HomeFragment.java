package com.music.maowo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.activity.HotAndNewArticleActivity;
import com.music.maowo.adapter.HomeFragmentAdapter;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.LoginAndRegisterResponse;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.other.GlideLoader;
import com.music.maowo.view.CustomGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class HomeFragment extends Fragment implements OnBannerListener, View.OnClickListener {
    @BindView(R.id.gv_content)
    CustomGridView gv_content;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rl_sort)
    RelativeLayout rl_sort;
    @BindView(R.id.rl_article)
    RelativeLayout rl_article;

    private List<String> images;
    private HomeFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Constants.screenWidth, Constants.screenWidth / 2);
        banner.setLayoutParams(params);

        rl_sort.setOnClickListener(this);
        rl_article.setOnClickListener(this);

        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        banner.setOnBannerListener(this);
        // TODO 从服务端获取数据
        banner.setImages(images).setImageLoader(new GlideLoader()).start();


        List<TopicSummaryInfo> list = new ArrayList<>();
        list.add(new TopicSummaryInfo("nusicUrl", images.get(0), "title1", "description1"));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(1), "title2", "description2"));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(2), "title3", "description3"));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(3), "title4", "description4"));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(4), "title5", "description5"));
        adapter = new HomeFragmentAdapter(list, getContext());
        gv_content.setAdapter(adapter);
    }

    @Override
    public void OnBannerClick(int position) {
        Observable<BaseResult<LoginAndRegisterResponse>> observable =
                RetrofitManager.getServices().login("chenlong5", "1234556");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<BaseResult<LoginAndRegisterResponse>>() {
                    @Override
                    public void onNext(BaseResult<LoginAndRegisterResponse> loginAndRegisterResponseBaseResult) {
                        Toast.makeText(getContext(), "result = " + loginAndRegisterResponseBaseResult.data.token, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == rl_sort) {
            Intent intent = new Intent(getActivity(), HotAndNewArticleActivity.class);
            intent.putExtra(HotAndNewArticleActivity.TYPE_SELECTED, HotAndNewArticleActivity.TYPE_NEW);
            startActivity(intent);
        } else if (view == rl_article) {
            Intent intent = new Intent(getActivity(), HotAndNewArticleActivity.class);
            intent.putExtra(HotAndNewArticleActivity.TYPE_SELECTED, HotAndNewArticleActivity.TYPE_HOT);
            startActivity(intent);
        }
    }
}
