package com.meiyue.meimei.http;

import com.meiyue.meimei.app.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Sunflower on 2015/11/4.
 */
public class Api {
    /**
     * 服务器地址
     */
    // 请求公共部分
    private static final String BASE_URL = "http://my.cnceshi.com";

    // 消息头
    private static final String HEADER_X_HB_Client_Type = "X-HB-Client-Type";
    private static final String FROM_ANDROID = "ayb-android";
    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML= "x-www-form-urlencoded;charset=utf-8";

    private static ApiService service;
    private static Retrofit retrofit;

    public static ApiService getService() {
        if (service == null) {
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    /**
     * 拦截器  给所有的请求添加消息头
     */
    private static Interceptor mInterceptor = new Interceptor(){
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_X_HB_Client_Type, FROM_ANDROID)
                    .build();
            return chain.proceed(request);
        }
    };
    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            // log拦截器  打印所有的log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 请求的缓存
            File cacheFile = new File(MyApplication.Companion.getInstance().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(mInterceptor)

//                    .cache(null)
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对 Observable<T> 做统一的处理，处理了线程调度、分割返回结果等操作组合了起来
     * @param responseObservable
     * @param <T>
     * @return
     */
    protected <T > Observable<T> applySchedulers(Observable<T> responseObservable) {
        return responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<T, Observable<T>>() {
                    @Override
                    public Observable<T> call(T tResponse) {
                        return flatResponse(tResponse);
                    }
                })
                ;
    }

    /**
     * 对网络接口返回的Response进行分割操作 对于json 解析错误以及返回的 响应实体为空的情况
     * @param response
     * @return
     */
    public < T > Observable<T> flatResponse(final T response) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response != null) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response);
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new APIException(-1,"自定义异常类型", "解析json错误或者服务器返回空的json"));
                    }
                    return;
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }

    /**
     *
     */
    public static class APIException extends Exception {
        public String data;
        public String msg;
        public int status;

        public APIException(int status, String msg, String data) {
            this.data = msg;
            this.msg = data;
            this.status = status;
        }

        @Override
        public String getMessage() {
            return msg;
        }

    }


}
