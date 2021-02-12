package com.example.chatapp.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TableEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

        public abstract UserDao userDao();
}
