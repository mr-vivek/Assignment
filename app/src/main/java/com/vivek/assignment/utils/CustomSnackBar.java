package com.vivek.assignment.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.vivek.assignment.R;

class CustomSnackBar {

    static View showSnackBar(View relative, String msg) {

       Snackbar snackbar = Snackbar.make(relative, "", Snackbar.LENGTH_LONG);

        LayoutInflater inflater = (LayoutInflater) relative.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = inflater.inflate(R.layout.custom_snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        TextView tv_msg = snackView.findViewById(R.id.textMsg);
        tv_msg.setText(msg);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();

        snackBarView.addView(snackView, 0);
        snackbar.setDuration(2000);
        snackbar.show();

        return snackView;

    }

}
