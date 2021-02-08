package com.example.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapters.ChatsAdapter;
import com.example.chatapp.chatmodel.ChatModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {

    private final String CHAT_PATH = "chat_path";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(CHAT_PATH);
    RecyclerView recyclerView;
    List<String> chatData = new ArrayList<>();
    TextInputEditText chat;
    ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat = findViewById(R.id.chat_edit_text);
        send = findViewById(R.id.send_chat);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new ChatsAdapter(chatData));
    }

    public void sendChat(View view) {
        chatData.add(chat.getText().toString());
        databaseReference.push().setValue(new ChatModel(chat.getText().toString()));
        recyclerView.getAdapter().notifyDataSetChanged();
        chat.setText("");
    }
}