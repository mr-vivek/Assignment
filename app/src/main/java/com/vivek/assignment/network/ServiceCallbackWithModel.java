package com.vivek.assignment.network;

import retrofit2.Response;

public interface ServiceCallbackWithModel {
    void onSuccess(Response<? extends Object> responseData);
    void onFailed(Throwable throwable);
}
