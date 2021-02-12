package com.example.chatapp.sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TableEntity {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "chat_tokens")
    String chatToken;

    public TableEntity(String chatToken) {
        this.chatToken = chatToken;
    }

}
