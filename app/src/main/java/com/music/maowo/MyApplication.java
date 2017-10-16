package com.music.maowo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.mob.MobApplication;

import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public class MyApplication extends MobApplication {

    private static Application mContext;
    private static MyApplication instance;
    private static final Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Constants.screenHeight = dm.heightPixels;
        Constants.screenWidth  = dm.widthPixels;
    }

    public MyApplication() {
        this.instance = this;
    }

    public static void init(Application context) {
        if (context == null) throw new RuntimeException("Context can not be null!");
        mContext = context;
        initInstance().onCreate();
    }

    private static MyApplication initInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    public static Application getContext() {
        return mContext;
    }

    /**
     * 判断当前应用程序是否处于后台
     * @param context
     */
    public static boolean isRunInBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static int getVersionBuild() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取versoin_name
     * @return 当前应用的版本号
     */
    public static String getVersionName() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Toast消息
     *
     * @param message
     */
    public static void toast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Activity activity, int msgRes) {
        Toast.makeText(activity, msgRes, Toast.LENGTH_SHORT).show();
    }

    /**
     * 执行工作线程
     *
     * @param runnable
     */
    public static void execBackground(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 执行主线程
     *
     * @param runnable
     */
    public static void execMainThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    /**
     * 执行主线程
     *
     * @param runnable
     * @param delayMillis
     */
    public static void execMainThread(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }
}
