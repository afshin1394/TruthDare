package com.afshin.truthordare.DataBase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.afshin.truthordare.DataBase.Entity.BottleEntity;
import com.afshin.truthordare.DataBase.Entity.ChallengerEntity;

import java.util.List;
@Dao
public interface ChallengerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertAllCahllengers(List<ChallengerEntity> challengers);


    @Query("Update Challenger Set image =:imaga where id = :id")
    void updateImage(String imaga, int id);

    @Query("SELECT * FROM Challenger")
    List<ChallengerEntity> getAll();

    @Query("Delete From Challenger")
    int deleteAll();
}
