package com.vivek.assignment.home.viewmodel;
import android.view.View;

import androidx.databinding.ObservableInt;

import com.vivek.assignment.R;
import com.vivek.assignment.application.AssignmentApp;
import com.vivek.assignment.database.DBHolder;
import com.vivek.assignment.home.datamodel.model.DataModel;
import com.vivek.assignment.home.datamodel.network.HomeServiceHandler;
import com.vivek.assignment.network.ServiceCallbackWithModel;
import com.vivek.assignment.utils.ActivityUtil;
import com.vivek.assignment.utils.ServiceResponseModel;
import com.vivek.assignment.utils.Status;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.vivek.assignment.utils.AppConstant.OFFLINE;
import static com.vivek.assignment.utils.AppConstant.ONLINE;

public class HomeFragmentViewModel extends Observable {
    
    private ObservableInt showProgress = new ObservableInt(View.GONE);
    private ObservableInt dataListRecyclerViewVisibility = new ObservableInt(View.VISIBLE);

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;

    public ObservableInt getDataListRecyclerViewVisibility() {
        return dataListRecyclerViewVisibility;
    }

    public void setDataListRecyclerViewVisibility(int dataListRecyclerViewVisibility) {
        this.dataListRecyclerViewVisibility.set(dataListRecyclerViewVisibility);
    }

    public ObservableInt getShowProgress() {
        return showProgress;
    }

    public void setShowProgress(int showProgress) {
        this.showProgress.set(showProgress);
    }

    public void getRateListData() {

            disposable = ActivityUtil.hasInternetConnection().subscribe(hasInternet -> {

                if (hasInternet) {
                    // API call to fetch data when online
                    getHomeDataServiceCall();

                } else {
                    // DB call to fetch data when offline
                    getHomeDataFromDb(OFFLINE);

                    // Update UI for negative response to update UI
                    notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.internet_check));
                   }
            });
        }

    private void notifyUIWhenFailure(String message) {
        setChanged();
        notifyObservers(new ServiceResponseModel(Status.FAILED, message));

    }

    private void getHomeDataServiceCall() {

        HomeServiceHandler.getDataList(compositeDisposable, responseData -> {
            if (responseData instanceof DataModel) {
                dataInsertToDB(((DataModel) responseData));
            }
        }, new ServiceCallbackWithModel() {
            @Override
            public void onSuccess(Response<?> responseData) {
                if (responseData != null) {

                    switch (responseData.code()) {

                        case 200:

                            getHomeDataFromDb(ONLINE);

                            break;

                        case 400:

                            getHomeDataFromDb(OFFLINE);

                            notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.bad_request));

                            break;

                        case 401:

                            getHomeDataFromDb(OFFLINE);

                            notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.auth_failure));

                            break;

                        default:

                            getHomeDataFromDb(OFFLINE);

                            notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.no_response_api));

                            break;
                    }
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                getHomeDataFromDb(OFFLINE);

                notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.on_failure));
            }
        });
    }

  
    private void dataInsertToDB(DataModel dataModel) {

        if(dataModel != null){

            List<DataModel> dataModelsFromDb = DBHolder.getDB().countryNewsDao().getAllHomeData();

            if(dataModelsFromDb!= null && dataModelsFromDb.size() > 0){
                dataModel.setDataId(dataModelsFromDb.get(0).getDataId());
                DBHolder.getDB().countryNewsDao().updateHomeData(dataModel);
            } else {
                dataModel.setDataId(1);
                DBHolder.getDB().countryNewsDao().insertHomeData(dataModel);
            }
        }
    }

    private void getHomeDataFromDb(int onlineStatus) {

        Single<List<DataModel>> single = Single.create(emitter -> {

            try {
                List<DataModel> rateModelList = DBHolder.getDB().countryNewsDao().getAllHomeData();


                if (rateModelList != null && !rateModelList.isEmpty()) {

                    emitter.onSuccess(rateModelList);


                } else {
                    emitter.onError(new NullPointerException());
                }

            } catch (Exception e) {

                emitter.onError(e);
            }
        });

        compositeDisposable.add(single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DataModel>>() {

                    @Override
                    public void onSuccess(List<DataModel> result) {
                        switch (onlineStatus) {
                            case ONLINE:
                                getOnlineData(result);
                                break;

                            case OFFLINE:
                                getOfflineData(result);
                                break;

                                default:
                                    break;
                        }
                    }
                    @Override
                    public void onError(Throwable error) {
                        notifyUIWhenFailure(AssignmentApp.getContext().getString(R.string.on_failure_while_fathcing_db_data));
                    }
                }));
    }


    private void getOfflineData(List<DataModel> result) {
        if (result != null && !result.isEmpty()) {
            setChanged();
            notifyObservers(new ServiceResponseModel(Status.SUCCESSFULLY_FETCHED_DATA, result));
        }
    }

    private void getOnlineData(List<DataModel> result) {
        if (result != null && !result.isEmpty()) {
            setChanged();
            notifyObservers(new ServiceResponseModel(Status.SUCCESSFULLY_FETCHED_DATA, result));
        }
    }


    


    public void reset() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        compositeDisposable = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
