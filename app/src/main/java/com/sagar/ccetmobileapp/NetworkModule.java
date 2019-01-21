package com.sagar.ccetmobileapp;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sagar.ccetmobileapp.network.repository.CCETRepository;
import com.sagar.ccetmobileapp.services.TokenService;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAGAR MAHOBIA on 01-Jul-18. at 12:24
 */
@Module(includes = ApplicationModule.class)
class NetworkModule {
    private static final String CCETEndPoint = "http://192.168.43.76:8888/";

    @Provides
    @ApplicationScope
    Interceptor provideInterceptor(TokenService tokenService) {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            boolean protectedUrl = originalHttpUrl.encodedPath().startsWith("/api/v1/protected/");
            if (!protectedUrl) {
                return chain.proceed(original);
            }

            Request.Builder url = original.newBuilder()
                    .url(originalHttpUrl);

            if (tokenService.hasToken()) {
                String token = tokenService.getToken();
                url.addHeader("Authorization", "Bearer " + token);
            }
            Request request = url.build();
            return chain.proceed(request);
        };
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Interceptor interceptor, HttpLoggingInterceptor httpLoggingInterceptor) {

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)//todo remove
                .build();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient client) {

        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(CCETEndPoint)
                .client(client)
                .build();
    }


    @Provides
    @ApplicationScope
    Picasso providePicasso(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(context, Integer.MAX_VALUE))
                .build();
        picasso.setIndicatorsEnabled(true);//todo remove
        picasso.setLoggingEnabled(true);//todo remove
        return picasso;
    }

    @Provides
    @ApplicationScope
    CCETRepository ccetRepository(Retrofit retrofit) {
        return retrofit.create(CCETRepository.class);
    }
}
