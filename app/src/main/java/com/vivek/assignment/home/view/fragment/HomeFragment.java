package com.vivek.assignment.home.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vivek.assignment.R;
import com.vivek.assignment.databinding.HomeFragmentBinding;
import com.vivek.assignment.home.datamodel.model.DataModel;
import com.vivek.assignment.home.view.adapter.HomeDataAdapter;
import com.vivek.assignment.home.viewmodel.HomeFragmentViewModel;
import com.vivek.assignment.utils.ActivityUtil;
import com.vivek.assignment.utils.ServiceResponseModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HomeFragment extends Fragment implements Observer {

    private HomeFragmentBinding homeFragmentBinding;
    private HomeFragmentViewModel homeFragmentViewModel;
    private ArrayList<DataModel> dataModelList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        homeFragmentViewModel = new HomeFragmentViewModel();
        homeFragmentBinding.setHomeFragmentView(homeFragmentViewModel);
        homeFragmentViewModel.addObserver(this);
        return homeFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRateListCall();
    }

    private void initRateListCall() {
        startShimmerEffect();
        homeFragmentViewModel.getRateListData();
    }


    public void update(Observable observable, Object arg) {

        ServiceResponseModel serviceResponseModel = (ServiceResponseModel) arg;
        if (observable instanceof HomeFragmentViewModel && serviceResponseModel != null) {

            stopShimmerEffect();

            switch (serviceResponseModel.getStatus()){
                case SUCCESSFULLY_FETCHED_DATA:

                    dataModelList = (ArrayList<DataModel>) serviceResponseModel.getData();
                    if(dataModelList!= null && !dataModelList.isEmpty()) {
                        loadHeader(dataModelList.get(0).getTitle());
                        loadCurrencyRateList();
                    }
                    break;

               case FAILED_TO_FETCH_DATA:
                    showErrorMessage(serviceResponseModel.getData().toString());
                    break;

                case FAILED:
                    showErrorMessage(serviceResponseModel.getData().toString());
                    break;

                default:
                    break;
            }

        }
    }

    private void loadHeader(String title) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null)
        actionBar.setTitle(title);
    }


    private void loadCurrencyRateList() {
        HomeDataAdapter homeDataAdapter = new HomeDataAdapter(getActivity(), dataModelList.get(0).getRows());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homeFragmentBinding.countryDataRecyclerView.setLayoutManager(layoutManager);
        homeFragmentBinding.countryDataRecyclerView.setAdapter(homeDataAdapter);
    }

    public void stopShimmerEffect() {
        homeFragmentViewModel.setShowProgress(View.GONE);
        homeFragmentViewModel.setDataListRecyclerViewVisibility(View.VISIBLE);

        if (homeFragmentBinding.shimmerHome.isAnimationStarted()) {
            homeFragmentBinding.shimmerHome.stopShimmerAnimation();
        }
    }

    public void startShimmerEffect(){
        homeFragmentViewModel.setShowProgress(View.VISIBLE);
        homeFragmentViewModel.setDataListRecyclerViewVisibility(View.INVISIBLE);
        homeFragmentBinding.shimmerHome.startShimmerAnimation();
    }

    public void showErrorMessage(String message){
        ActivityUtil.setSnackBar(homeFragmentBinding.homeFragmentContainer, message);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(homeFragmentViewModel != null) {
            homeFragmentViewModel.reset();

        }
    }



}
