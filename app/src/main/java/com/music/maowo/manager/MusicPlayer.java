package com.music.maowo.manager;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.music.maowo.bean.MusicInfo;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Limuyang on 2016/9/6.
 */

public class MusicPlayer implements OnCompletionListener {
    private static MusicPlayer player = new MusicPlayer();

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private boolean isNowPlaying;
    private MusicInfo mCurrentInfo;

    public static MusicPlayer getPlayer() {
        return player;
    }

    public static void setPlayer(MusicPlayer player) {
        MusicPlayer.player = player;
    }

    private MusicPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
    }

    public MusicPlayer setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public void play(MusicInfo detail) {
        try {
            mCurrentInfo = detail;
            mMediaPlayer.reset();
            AssetFileDescriptor assetFileDescriptor = mContext.getAssets().openFd(detail.localurl);
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            mMediaPlayer.setDataSource(fileDescriptor, assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    isNowPlaying = true;
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mMediaPlayer.pause();
        isNowPlaying = false;
    }

    public void resume() {
        mMediaPlayer.start();
        isNowPlaying = true;
    }


    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play(mCurrentInfo);
    }

    private void release() {
        isNowPlaying  = false;
        mMediaPlayer.release();
        mMediaPlayer = null;
        mContext = null;
    }

    public boolean isNowPlaying() {
        return isNowPlaying;
    }

}