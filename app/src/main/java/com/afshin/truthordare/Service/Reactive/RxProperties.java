package com.afshin.truthordare.Service.Reactive;

import retrofit2.Response;

public class RxProperties {


    enum HttpErrorCode {
        Success
        ,BadRequest
        , Unauthorized
        , Forbidden
        , Not_Found
        , Internal_Server_Error
        , Bad_Gateway
        , Service_Unavailable
        , Gateway_Timeout
        , Unknown
    }


    public static  <T> HttpErrorCode VerifyHttpError(Response<T> response) {
        switch (response.code()) {

            case 200:
                return HttpErrorCode.Success;


            case 400:
                return HttpErrorCode.BadRequest;


            case 401:
                return HttpErrorCode.Unauthorized;


            case 404:

                return HttpErrorCode.Forbidden;


            case 500:

                return HttpErrorCode.Internal_Server_Error;


            case 502:

                return HttpErrorCode.Bad_Gateway;


            case 503:

                return HttpErrorCode.Service_Unavailable;


            case 504:

                return HttpErrorCode.Gateway_Timeout;


            default:
                return HttpErrorCode.Unknown;
        }

    }
}
