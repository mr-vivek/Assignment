package com.vivek.assignment.home.datamodel.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vivek.assignment.home.datamodel.model.DataModel;

import java.util.List;

@Dao
public interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHomeData(DataModel... dataModels);

    @Query("SELECT * FROM home_data")
    List<DataModel> getAllHomeData();

    @Query("SELECT count(dataId) FROM home_data WHERE dataId = :dataId")
    int countHomeData(int dataId);

    @Query("SELECT dataId FROM home_data WHERE dataId =:dataId")
    int getPrimaryKey(int dataId);

    @Update
    int updateHomeData(DataModel dataModel);

}
