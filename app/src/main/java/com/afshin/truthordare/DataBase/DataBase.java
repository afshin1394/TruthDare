package com.afshin.truthordare.DataBase;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.DataBase.Dao.BottleDao;
import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
import com.afshin.truthordare.DataBase.Entity.BottleEntity;
import com.afshin.truthordare.DataBase.Entity.ChallengerEntity;
import com.afshin.truthordare.Utils.Constants;

@Database(entities = {BottleEntity.class, ChallengerEntity.class}, version = 3, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static DataBase Instance;

    public abstract BottleDao bottleDao();
    public abstract ChallengerDao challengerDao();



    public static synchronized DataBase getInstance(Context context) {
        if (Instance == null) {

            Instance = Room.databaseBuilder(context, DataBase.class, Constants.DATA_BASE)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }





}
