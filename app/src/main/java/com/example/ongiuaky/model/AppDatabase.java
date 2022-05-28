package com.example.ongiuaky.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DogBreed.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DogDAO contactDAO();
    public static AppDatabase instance;
    public  static AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "Dogapp").build();
        }
        return instance;
    }
}
