package com.vivek.assignment.home.datamodel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vivek.assignment.database.DataConverter;


@Entity(tableName = "home_data")
public class DataModel {


    public DataModel() {
    }

    @PrimaryKey(autoGenerate = true)
    @SerializedName("dataId")
    @Expose
    private Integer dataId;

    @SerializedName("title")
    @Expose
    private String title;

    @TypeConverters(DataConverter.class)
    @SerializedName("rows")
    @Expose
    private List<Rows> rows = null;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

}