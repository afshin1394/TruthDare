package com.afshin.truthordare.Dagger.Modules;


import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.MySSLSocketFactory;
import com.afshin.truthordare.Utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        return   new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .sslSocketFactory(getUnsafeOkHttpClient(), (X509TrustManager) MySSLSocketFactory.trustAllCerts[0])
                .build();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
           return new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
                 .client(okHttpClient)
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
    }

    @Provides
    @Singleton
    public ApiService  provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }


    private static javax.net.ssl.SSLSocketFactory getUnsafeOkHttpClient() {
        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, MySSLSocketFactory.trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
