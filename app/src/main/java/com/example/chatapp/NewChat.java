package com.example.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapters.NewUserChatAdapter;

public class NewChat extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        recyclerView = findViewById(R.id.make_new_chat);
        recyclerView.setAdapter(new NewUserChatAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}