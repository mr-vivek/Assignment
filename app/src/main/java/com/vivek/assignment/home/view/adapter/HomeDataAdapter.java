package com.vivek.assignment.home.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.assignment.R;
import com.vivek.assignment.databinding.HomeDataListBinding;
import com.vivek.assignment.home.datamodel.model.Rows;
import com.vivek.assignment.home.viewmodel.HomeDataListAdapterViewModel;

import java.util.List;


public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.MyViewHolder> {

    private Context context;
    private List<Rows> dataModelArrayList;
    private HomeDataListBinding homeDataListBinding;


    public HomeDataAdapter(Context context, List<Rows> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public HomeDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        homeDataListBinding =  DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.home_data_list, viewGroup, false);


        return new HomeDataAdapter.MyViewHolder(homeDataListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.bindUser(dataModelArrayList.get(i));

        myViewHolder.itemView.setOnClickListener((View view) -> {

            //TODO

        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public HomeDataListBinding homeDataListBinding1;

        public MyViewHolder(@NonNull HomeDataListBinding homeDataListBinding) {
            super(homeDataListBinding.getRoot());
            this.homeDataListBinding1 = homeDataListBinding;
        }

        void bindUser(Rows dataModel) {

            if (homeDataListBinding1.getHomeDataListAdapterViewModel() == null) {
                HomeDataListAdapterViewModel homeDataListAdapterViewModel = new HomeDataListAdapterViewModel(dataModel);
                homeDataListBinding1.setHomeDataListAdapterViewModel(homeDataListAdapterViewModel);
            }else {
                homeDataListBinding1.getHomeDataListAdapterViewModel().setData(dataModel);
            }
        }
    }

}

