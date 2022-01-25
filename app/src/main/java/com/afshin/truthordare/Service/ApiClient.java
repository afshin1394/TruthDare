package com.afshin.truthordare.Service;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ApiClient {

    public static final String BaseUrl = "https://192.168.80.181:5001/";


    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new LoggingInterceptor())
            .sslSocketFactory(getUnsafeOkHttpClient(), (X509TrustManager) MySSLSocketFactory.trustAllCerts[0])
            .hostnameVerifier((hostname, session) -> true)
            .build();


    private static Retrofit.Builder builder = new Retrofit
            .Builder()
            .baseUrl(BaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .client(client).build();

        return retrofit.create(serviceClass);
    }


    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Log.i("LoggingInterceptor", "inside intercept callback");
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers());


            Log.i("HTTP_Request", "request" + "\n" + requestLog + "  chain time out = " + chain.readTimeoutMillis() + " connect " + chain.connectTimeoutMillis() + " write = " + chain
                    .writeTimeoutMillis());
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());
            Log.i("HTTP_Response", "********1********");
            String bodyString = response.body().string();
            Log.i("HTTP_Response", "********2********");

            Log.i("HTTP_Response", " chain.connection() = " + chain.connection());
            Log.i("HTTP_Response", "********3********");

            Log.i("HTTP_Response", " request.headers() = " + request.headers());
            Log.i("HTTP_Response", "********4********");


            Log.i("HTTP_Response", "response only" + "\n" + bodyString);
            Log.i("HTTP_Response", "********5********");

            Log.i("HTTP_Response", "response" + "\n" + responseLog + "\n" + bodyString);
            Log.i("HTTP_Response", "********6********");
            Response response1 = response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            Log.i("HTTP_Response", "********7********");

            return response1;


        }



        public static String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }

        }

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
