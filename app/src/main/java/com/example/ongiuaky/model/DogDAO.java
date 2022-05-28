package com.example.ongiuaky.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface DogDAO {
    @Query("SELECT *FROM Dog")
    List<DogBreed> getAll();
    @Insert
    void insert(DogBreed...dog);
//    @Query("UPDATE Dog SET name=:name , email=:email, mobile=:mobile,avt=:avt WHERE id=:id")
//    void update(int id,String name, String email, String mobile, String avt );
}
