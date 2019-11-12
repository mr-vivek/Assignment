package com.vivek.assignment.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    public synchronized static void replaceFragment(Context context, Fragment fragment, int frameLayoutId, int animConstant) {

        try {

            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back

            transaction.replace(frameLayoutId, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
            //((AppCompatActivity) context).getSupportFragmentManager().executePendingTransactions();

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
