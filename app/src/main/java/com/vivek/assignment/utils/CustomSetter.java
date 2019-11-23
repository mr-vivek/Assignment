package com.vivek.assignment.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vivek.assignment.R;
import com.vivek.assignment.application.AssignmentApp;

public class CustomSetter {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);

        Glide.with(AssignmentApp.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl).into(imageView);
    }

}