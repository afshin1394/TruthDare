package com.afshin.truthordare.Service;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;

public  class GrpcChannel {

    private static ManagedChannel managedChannelInstance;



    public static  ManagedChannel channel(){

            if (managedChannelInstance == null) {
                return managedChannelInstance = OkHttpChannelBuilder
                        .forAddress("192.168.80.181", 5000)
                        .usePlaintext()
                        .build();
            } else {
                return managedChannelInstance;
            }


    }




}
