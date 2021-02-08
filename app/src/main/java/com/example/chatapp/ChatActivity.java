package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void newChat(View view) {
        Intent intent = new Intent(this,NewChat.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        firebaseAuth.signOut();
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}