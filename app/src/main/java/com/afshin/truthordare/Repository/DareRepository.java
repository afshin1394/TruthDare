package com.afshin.truthordare.Repository;

import com.afshin.truthordare.Service.GrpcChannel;
import com.saphamrah.protos.CaregoryIDRequest;
import com.saphamrah.protos.CategoryIDRequest;
import com.saphamrah.protos.DareGrpc;
import com.saphamrah.protos.DareModel;
import com.saphamrah.protos.DareRequest;
import com.saphamrah.protos.DareResponse;
import com.saphamrah.protos.Question;
import com.saphamrah.protos.QuestionRequest;
import com.saphamrah.protos.QuestionaireGrpc;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public class DareRepository {

    private static DareRepository dareRepository;

    public static DareRepository Instance(){
        if (dareRepository == null){
            dareRepository = new DareRepository();
        }
        return dareRepository;
    }




    public  List<DareModel>  getDares(StreamObserver<DareResponse> streamObserver) {
        ManagedChannel managedChannel = GrpcChannel.channel();
        DareGrpc.DareBlockingStub dareBlockingStub = DareGrpc.newBlockingStub(managedChannel);
        DareRequest dareRequest = DareRequest.newBuilder().build();
        return dareBlockingStub.getAllDares(dareRequest);
    }

    public  void   getDareByCategory(int type,StreamObserver<DareResponse> streamObserver){
        ManagedChannel managedChannel = GrpcChannel.channel();
        DareGrpc.DareStub dareStub = DareGrpc.newStub(managedChannel);
        CaregoryIDRequest caregoryIDRequest = CaregoryIDRequest.newBuilder().setCategoryIDRequest(type).build();
        dareStub.getDaresByCategoryId(caregoryIDRequest,streamObserver);
    }

}
