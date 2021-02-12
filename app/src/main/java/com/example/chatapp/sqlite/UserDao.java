package com.example.chatapp.sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT chat_tokens FROM tableentity")
    List<String> getAllChatTokens();

    @Insert
    void insertTokens(TableEntity userTokens);


}
