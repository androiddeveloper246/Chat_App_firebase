package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chatapp.adapters.MainChatAdapter;
import com.example.chatapp.sqlite.AppDatabase;
import com.example.chatapp.sqlite.TableEntity;
import com.example.chatapp.sqlite.UserDao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private List<String> chatTokenFromDatabase = new ArrayList<>();
    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        } else {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    chatTokenFromDatabase.clear();
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot s : snapshot.getChildren()) {

                            if (s.getKey().contains(firebaseAuth.getCurrentUser().getUid())) {
                                chatTokenFromDatabase.add(s.getKey());
                                recyclerView.getAdapter().notifyDataSetChanged();
                            } else {// no user inner tree
                            }
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "Tree have no children", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
            databaseReference.addValueEventListener(valueEventListener);

            recyclerView = findViewById(R.id.chat);
            recyclerView.setAdapter(new MainChatAdapter(chatTokenFromDatabase, this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    public void newChat(View view) {

        Intent intent = new Intent(this, NewChat.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        firebaseAuth.signOut();
//        Intent intent = new Intent(this,SignUpActivity.class);
//        startActivity(intent);
//        finish();

        Toast.makeText(this, "Temp Sign out closed", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}