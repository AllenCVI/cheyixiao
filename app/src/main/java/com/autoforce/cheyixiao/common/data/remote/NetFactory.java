package com.autoforce.cheyixiao.common.data.remote;

import com.autoforce.cheyixiao.common.utils.Network;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xlh
 * @date 2018/9/22.
 */
public class NetFactory {

    private static final Retrofit RETROFIT = new MRetrofit().getInstance();

    public static Retrofit getRetrofit() {
        return RETROFIT;
    }

    private static class MRetrofit {

        //todo okhttpclient抽取出来
//        private OkHttpClient okHttpClient =
//                new OkHttpClient.Builder()
//                        .readTimeout(10, TimeUnit.SECONDS)
//                        .writeTimeout(10, TimeUnit.SECONDS)
//                        .connectTimeout(10, TimeUnit.SECONDS)
//                        .addInterceptor(new ParamsInterceptor())
//                        .addInterceptor(new LoggerInterceptor())
//                        .build();

        Retrofit getInstance() {
            return new Retrofit.Builder()
                    .baseUrl(Network.BASE_URL)
                    .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(OkHttpClientProvider.client())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

}
