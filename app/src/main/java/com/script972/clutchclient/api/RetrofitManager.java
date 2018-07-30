package com.script972.clutchclient.api;

import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.helpers.PrefHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static class LAZY_HOLDER {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return LAZY_HOLDER.INSTANCE;
    }

    public Retrofit apiRetrofit;
    private Retrofit oauthRetrofit;

    private RetrofitManager() {
        OkHttpClient.Builder okHttpBuilder = getOkHttpBuilder();
        okHttpBuilder.addInterceptor(chain -> {
            String accessToken = PrefHelper.getAccessToken(ClutchApplication.getApplication());

            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            if (accessToken!=null && !accessToken.isEmpty()) {
                requestBuilder.header(ApiClient.OAuth.Header.AUTH_TOKEN_KEY, ApiClient.OAuth.Header.buildAuthHeaderValue(accessToken));
            }
            requestBuilder.addHeader("Connection", "close");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
       // okHttpBuilder.authenticator(new TokenAuthenticator());
        OkHttpClient okHttpClient = okHttpBuilder.build();
        apiRetrofit = buildRetrofitInstance(ApiClient.ClutchClient.BASE_API_URL, okHttpClient);
       // oauthRetrofit = buildRetrofitInstance(ApiClient.ClutchClient.OAuth.BASE_URL, okHttpClient);
    }

    private OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        builder = NetworkLoggingSettings.configOkHttpClient(builder);
        return builder;
    }

    private Retrofit buildRetrofitInstance(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return body -> {
                if (body.contentLength() == 0) {
                    return null;
                }
                return delegate.convert(body);
            };
        }
    }

    public static class ToStringConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return value -> {
                if (String.class.equals(type)) {
                    return value.string();
                }
                return delegate.convert(value);
            };
        }
    }

}
