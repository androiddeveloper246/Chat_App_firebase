package com.example.chatapp;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.example.chatapp.adapters.ChatsAdapter;
import com.example.chatapp.chatmodel.ChatModel;
import com.example.chatapp.sqlite.AppDatabase;
import com.example.chatapp.sqlite.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessageCreator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    private String CHAT_PATH = "chats";
    private RecyclerView recyclerView;
    private List<String> chatData = new ArrayList<>();
    private List<Integer> flags = new ArrayList<>();
    private List<String> timeDate = new ArrayList<>();
    private  List<String> sender = new ArrayList<>();
    private List<Integer> isImageCategory = new ArrayList<>();
    private List<String> pathTostoreImage = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private DatabaseReference databaseReference;
    private TextInputEditText chat;
    private ImageButton send;
    private ValueEventListener listener;
    private final int IMAGEPICK_REQCODE = 246;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Token comes from main chat adapter and new user chat adapter
        CHAT_PATH = getIntent().getStringExtra("passToken");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(CHAT_PATH);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatData.clear();
                flags.clear();
                timeDate.clear();
                sender.clear();
                isImageCategory.clear();
                imageUrl.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);

                    pathTostoreImage.add(dataSnapshot.getKey());

                    if (!firebaseAuth.getCurrentUser().getUid().equals(chatModel.getSender())) {
                        DatabaseReference te = firebaseDatabase.getReference(CHAT_PATH).child(dataSnapshot.getKey()).child("readReceiptFlag");
                        te.setValue(2);
                    }

                    timeDate.add(chatModel.getTimeAndDate());
                    chatData.add(chatModel.getChat());
                    flags.add(chatModel.getReadReceiptFlag());
                    sender.add(chatModel.getSender());
                    isImageCategory.add(chatModel.getIsImageCategory());
                    imageUrl.add(chatModel.getImagePath());

                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        };
        databaseReference.addValueEventListener(listener);


        chat = findViewById(R.id.chat_edit_text);
        send = findViewById(R.id.send_chat);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChatsAdapter(this,chatData, flags, timeDate,sender,isImageCategory,imageUrl));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(listener);
    }

    public void sendChat(View view) {

        Calendar date = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateAndTime = df.format(date.getTime());

        String sender = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (chat.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Message First", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.push().setValue(new ChatModel(chat.getText().toString(), dateAndTime, 1, sender,null,1));
            chat.setText("");
        }
    }

    public void attachImage(View view) {
        // store link inside database and fetch image from link
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGEPICK_REQCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGEPICK_REQCODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            String tempReference;
            if (pathTostoreImage.size() == 0){
                 tempReference = "custom root reference";
            }else {
                tempReference = pathTostoreImage.get(pathTostoreImage.size()-1);
            }
            firebaseStorage.getReference(tempReference).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    Toast.makeText(ChatRoom.this, "Image Store Successfully", Toast.LENGTH_SHORT).show();

                    firebaseStorage.getReference(tempReference).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            // TODO : create new node end of chat tree for image
                            // TODO : store image path and value 2 for image category
                            imagePath = task.getResult().toString();

                            String sender = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            databaseReference.push().setValue(new ChatModel("Image", "Image", 1, sender,imagePath,2));
                        }
                    });
                }
            });
        }
    }
}