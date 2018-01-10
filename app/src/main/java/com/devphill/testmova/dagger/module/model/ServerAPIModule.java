package com.devphill.testmova.dagger.module.model;

import com.devphill.testmova.internet.ServerAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServerAPIModule {

    @Provides
    @Singleton
    Interceptor provideInterceptor() {
        Interceptor interceptor = chain -> {
            Request request = chain.request().newBuilder().addHeader("Api-Key", "ys5antywsqh3bd2jam3b3t7p").build();
            return chain.proceed(request);
        };
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(ServerAPI.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ServerAPI provideServerAPI(Retrofit retrofit) {
        return retrofit.create(ServerAPI.class);
    }
}
