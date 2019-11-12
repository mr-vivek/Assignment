package com.vivek.assignment.home.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vivek.assignment.R;
import com.vivek.assignment.databinding.ActivityHomeBinding;
import com.vivek.assignment.home.view.fragment.HomeFragment;
import com.vivek.assignment.home.viewmodel.HomeActivityViewModel;
import com.vivek.assignment.utils.ActivityUtil;
import com.vivek.assignment.utils.FragmentUtil;

import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity implements Observer {

    private int backPress = 0;
    private String TAG = this.getClass().getSimpleName();
    public HomeActivityViewModel homeActivityViewModel;
    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeActivityViewModel = new HomeActivityViewModel();
        activityHomeBinding.setHomeAcivityView(homeActivityViewModel);
        homeActivityViewModel.addObserver(this);

        initData();
    }

    private void initData() {
        FragmentUtil.replaceFragment(this, new HomeFragment(), R.id.home_container, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismissApplication();
    }

    private void dismissApplication() {
        if (backPress == 0) {
            backPress++;
            ActivityUtil.setSnackBar(activityHomeBinding.homeContainer, getString(R.string.closing_msg));
        } else {
            finishAffinity();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
    }
}
