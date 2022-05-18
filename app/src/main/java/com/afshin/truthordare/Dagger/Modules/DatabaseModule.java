package com.afshin.truthordare.Dagger.Modules;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

import com.afshin.truthordare.DataBase.Dao.BottleDao;
import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
import com.afshin.truthordare.DataBase.DataBase;
import com.afshin.truthordare.Utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public DataBase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                DataBase.class,
                Constants.DATA_BASE)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }


    @Provides
    public BottleDao provideBottleDao(DataBase dataBase){
          return dataBase.bottleDao();
    }
    @Provides
    public ChallengerDao provideChallengerDao(DataBase dataBase){
        return dataBase.challengerDao();
    }
}
