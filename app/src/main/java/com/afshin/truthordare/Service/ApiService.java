package com.afshin.truthordare.Service;

import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Pojo.Questions;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/Question/GetAll")
    Single<Response<BaseResponse<Questions>>> getAllQuestions
            (
            );


    @GET("api/Dare/GetAll")
    Single<Response<BaseResponse<Dares>>> getAllDares
            (
            );
}
