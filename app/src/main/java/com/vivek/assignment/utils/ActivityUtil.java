package com.vivek.assignment.utils;


import android.view.View;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivityUtil {

    private ActivityUtil(){

    }
    private static View snackView;

    public static void setSnackBar(View rootLayout, String snackTitle) {
        snackView = CustomSnackBar.showSnackBar(rootLayout, snackTitle);
        snackView.setVisibility(View.VISIBLE);
    }

    //this method given that u have slow internet connection
    public static Single<Boolean> hasInternetConnection() {
        return Single.fromCallable(() -> {
            try (Socket socket = new Socket()) {
                int timeoutMs = 1500;
                SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                socket.connect(socketAddress, timeoutMs);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
