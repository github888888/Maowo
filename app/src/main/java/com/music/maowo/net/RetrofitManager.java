package com.music.maowo.net;

import com.music.maowo.Constants;

import java.io.IOException;
import java.lang.invoke.ConstantCallSite;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public class RetrofitManager {
    public static final String BASE_URL = "http://123.59.214.241:8000/";
    private static RetrofitManager sInstance;
    private Retrofit retrofit;
    private ApiService service;
    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static ApiService getServices() {
        if (null == sInstance) {
            synchronized (RetrofitManager.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance.service;
    }
    private static final OkHttpClient sClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("token", Constants.access_token + "")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            })
            .build();

}
