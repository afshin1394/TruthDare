package com.afshin.truthordare.Dagger.Modules;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.MySSLSocketFactory;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {






}
