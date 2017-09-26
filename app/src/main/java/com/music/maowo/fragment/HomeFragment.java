package com.music.maowo.fragment;

import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.adapter.HomeFragmentAdapter;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.LoginAndRegisterResponse;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.other.GlideLoader;
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
    @BindView(R.id.lv_content)
    ListView lv_content;

    private View header;
    private Banner banner;
    private RelativeLayout rl_sort;
    private RelativeLayout rl_article;

    private List<String> images;
    private HomeFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        header = inflater.inflate(R.layout.fragment_home_sort_and_article_layout, null);
        banner = header.findViewById(R.id.banner);
        rl_sort = header.findViewById(R.id.rl_sort);
        rl_article = header.findViewById(R.id.rl_article);
        rl_sort.setOnClickListener(this);
        rl_article.setOnClickListener(this);
        init();
        return view;
    }

    private void init() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Constants.screenWidth, Constants.screenWidth / 2);
        banner.setLayoutParams(params);

        lv_content.addHeaderView(header);

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
        lv_content.setAdapter(adapter);
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
                });
    }

    @Override
    public void onClick(View view) {
        if (view == rl_sort) {
            Toast.makeText(getContext(), "rl_sort", Toast.LENGTH_LONG).show();
        } else if (view == rl_article) {
            Toast.makeText(getContext(), "rl_article", Toast.LENGTH_LONG).show();
        }
    }
}
