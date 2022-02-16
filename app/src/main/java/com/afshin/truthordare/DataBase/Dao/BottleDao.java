package com.afshin.truthordare.DataBase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.afshin.truthordare.DataBase.Entity.BottleEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
@Dao
public interface BottleDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertAllOrders(List<BottleEntity> order);


    @Query("SELECT * FROM Bottle")
    List<BottleEntity> getAll();

    @Query("Delete From Bottle")
    int deleteAll();





}
