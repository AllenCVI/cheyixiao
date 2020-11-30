package com.autoforce.cheyixiao.common.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult;
import com.autoforce.cheyixiao.common.data.remote.util.Utils;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import com.autoforce.cheyixiao.common.utils.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by xialihao on 2018/12/3.
 */
public class OkHttpClientProvider {

    private static final String PARAMS_TOKEN = "Cyx_token";
    private static final String PARAMS_SALER = "Cyx_saler";
    private static final String PARAMS_REF = "Cyx_ref";
    public static final String IS_MOBILE = "1";
    private static final String VERSION = "origin_version";

    public static OkHttpClient client() {
        return Holder.okHttpClient;
    }

    public static OkHttpClient myClient(){
        return MyHolder.myOkHttpClient;
    }

    private static class MyHolder {
        private static OkHttpClient myOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private static class Holder {
        private static OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(new ParamsInterceptor())
                        .addInterceptor(new LoggerInterceptor())
                        .build();
    }

    /**
     * 服务器传参需要，需在每个url接口添加token等相关信息
     */
    private static class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            String token = LocalRepository.getInstance().getToken();
            HttpUrl.Builder builder = request.url().newBuilder();

            if (!TextUtils.isEmpty(token)) {
                builder.addQueryParameter(PARAMS_TOKEN, token)
                        .addQueryParameter(PARAMS_SALER, LocalRepository.getInstance().getSalerId())
                        .addQueryParameter(PARAMS_REF, IS_MOBILE);
            }

            HttpUrl httpUrl = builder
                    .addQueryParameter(VERSION, StringUtils.formatVersionName())
                    .build();
            request = request.newBuilder().url(httpUrl).build();

            return chain.proceed(request);
        }
    }


    private static class LoggerInterceptor implements Interceptor {

        private final JsonParser parser = new JsonParser();

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            String log = "";

            Response response = chain.proceed(request);

            if (request.method().equals("POST") && request.body().contentType() != null && request.body().contentType().type() != null
                    && !request.body()
                    .contentType()
                    .type()
                    .equals("multipart") && !request.url().toString().contains("sms/user/img")) {
                try {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    String body = buffer.readUtf8();
                    body = Utils.paramsToJson(body);
                    log = body;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String srt2 = new String(response.body().bytes(), "UTF-8");

            if (request.method().equals("POST") && request.body() != null && request.body().contentType() != null
                    && !request.body()
                    .contentType()
                    .toString()
                    .equals("image/jpeg")) {
                Logger.e("{\"url\":\""
                        + request.url().toString()
                        + "\",\"request\":"
                        + log
                        + ",\"response\":"
                        + srt2
                        + "}");
            } else {
                Logger.e("{\"url\":\"" + request.url().toString() + "\",\"response\":" + srt2 + "}");
            }


            JsonElement rootElement = parser.parse(srt2);
            if (!rootElement.isJsonObject()) {
                throw new JsonParseException("Root is not JsonObject");
            }

            SimpleResult simpleResult = GsonProvider.gson().fromJson(srt2, SimpleResult.class);
            if (simpleResult.isError()) {
                throw new ResponseException(simpleResult);
            }


            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), srt2.getBytes("UTF-8")))
                    .build();
        }
    }

}
