package com.vivek.assignment.database;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vivek.assignment.application.AssignmentApp;
import com.vivek.assignment.home.datamodel.dao.DataDao;
import com.vivek.assignment.home.datamodel.model.DataModel;


@Database(entities = {DataModel.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final String DB_NAME = "assignment_database";


    public abstract DataDao currencyRateDao();

    public static AppDatabase getAppDatabase() {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(AssignmentApp.getContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
