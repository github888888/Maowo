package com.music.maowo.activity;

import android.view.View;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.MusicInfo;
import com.music.maowo.manager.MusicPlayer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-8-25 0025.
 */
@Layout(R.layout.activity_read_layout)
public class ReadActivity extends BaseActivity {
    @BindView(R.id.tv_play)
    TextView tv_play;

    private MusicInfo musicInfo;

    @Override
    protected void initDataAndListener() {
        musicInfo = new MusicInfo();
        musicInfo.localurl = "zhang_nosay.mp3";

        MusicPlayer.getPlayer().setContext(this).play(musicInfo);
        tv_play.setText("pause");
    }

    @OnClick({R.id.tv_play})
    public void onClick(View view) {
        if (tv_play == view) {
            if (MusicPlayer.getPlayer().isNowPlaying()) {
                tv_play.setText("continue");
                MusicPlayer.getPlayer().pause();
            } else {
                tv_play.setText("pause");
                MusicPlayer.getPlayer().resume();
            }
        }
    }
}
