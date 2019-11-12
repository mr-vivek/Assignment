package com.vivek.assignment.network;

import com.vivek.assignment.home.datamodel.model.DataModel;
import com.vivek.assignment.utils.AppConstant;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    @GET(URLConstant.COUNTRY_DATA)
    Single<Response<DataModel>> getCountryDataList();



}