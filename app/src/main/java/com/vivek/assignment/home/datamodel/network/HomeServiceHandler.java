package com.vivek.assignment.home.datamodel.network;

import com.vivek.assignment.application.AssignmentApp;
import com.vivek.assignment.home.datamodel.model.DataModel;
import com.vivek.assignment.network.MappingCallback;
import com.vivek.assignment.network.ServiceCallbackWithModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class HomeServiceHandler {

    private HomeServiceHandler() {

    }

    public static void getDataList(CompositeDisposable compositedisposable,
                                   final MappingCallback mappingCallback, final ServiceCallbackWithModel serviceCallback) {

        Disposable loginDisposable = ((AssignmentApp) AssignmentApp.getContext()).getNetComponent()
                .getCountryDataList()
                .subscribeOn(((AssignmentApp) AssignmentApp.getContext()).subscribeScheduler())
                .map(response -> {
                    mappingCallback.onMappedResult(response.body());
                    return response;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<DataModel>>() {
                    @Override
                    public void onSuccess(Response<DataModel> stringResponse) {
                        serviceCallback.onSuccess(stringResponse);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        serviceCallback.onFailed(throwable);
                    }
        });
        compositedisposable.add(loginDisposable);
    }

}
