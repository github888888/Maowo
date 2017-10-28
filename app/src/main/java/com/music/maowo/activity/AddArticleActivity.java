package com.music.maowo.activity;

import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.Utils.ToastUtils;
import com.music.maowo.anno.Layout;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-10-28 0028.
 */
@Layout(R.layout.activity_add_article_layout)
public class AddArticleActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @Override
    protected void initDataAndListener() {
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        } else if (view == tv_submit) {
            gotoUploadFile();
        }
    }

    private void gotoUploadFile() {
        String title = et_title.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.show("标题为空");
            return;
        }
        String content = et_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show("内容为空");
            return;
        }
        int type = new Random().nextInt(3);
        String music = "推荐音乐";
        Observable<BaseResult> observable = RetrofitManager.getServices().submitArticle(title,content,type + "", music, 9);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<BaseResult>() {
                    @Override
                    public void onNext(BaseResult response) {
                        ToastUtils.show("上传成功");
                        finish();
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
}
