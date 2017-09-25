package com.music.maowo.net;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("zhuce")
    Observable<BaseResult<LoginAndRegisterResponse>> register(@Field("username") String username, @Field("password1") String password);
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResult<LoginAndRegisterResponse>> login(@Field("username") String username, @Field("password") String password);
}
