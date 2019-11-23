package com.vivek.assignment.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    public synchronized static void replaceFragment(Context context, Fragment fragment, int frameLayoutId, int animConstant) {

        try {
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized static void removeFragment(Context context, Fragment fragment,int animConstant) {

        try {
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
            ((AppCompatActivity) context).getSupportFragmentManager().executePendingTransactions();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
