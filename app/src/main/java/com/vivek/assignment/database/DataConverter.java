package com.vivek.assignment.database;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vivek.assignment.home.datamodel.model.Rows;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromBranch(List<Rows> branchTimes) {
        if (branchTimes == null || branchTimes.isEmpty()) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rows>>() {
        }.getType();
        return gson.toJson(branchTimes, type);
    }

    @TypeConverter
    public List<Rows> toBranch(String branchTimes) {
        if (branchTimes == null || TextUtils.isEmpty(branchTimes)) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rows>>() {
        }.getType();
        return gson.fromJson(branchTimes, type);
    }
}
