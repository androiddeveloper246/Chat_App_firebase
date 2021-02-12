package com.example.chatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.chatapp.ChatActivity;
import com.example.chatapp.ChatRoom;
import com.example.chatapp.R;
import com.example.chatapp.sqlite.AppDatabase;
import com.example.chatapp.sqlite.TableEntity;
import com.example.chatapp.sqlite.UserDao;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NewUserChatAdapter extends RecyclerView.Adapter<NewUserChatAdapter.MyViewHolder> {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private List<String> allUserTokens;
    private Context context;
    private String chatTokens, chatTokens1;
    private boolean tokenAvailable = false, tokenAvailable1 = false;

    public NewUserChatAdapter(Context context, List<String> allUserTokens) {
        this.context = context;
        this.allUserTokens = allUserTokens;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_chat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getNewUser().setText(allUserTokens.get(position));
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot s : snapshot.getChildren()) {

                            if (s.getKey().equals(chatTokens)) {
                                tokenAvailable = true;
                            } else if (s.getKey().equals(chatTokens1)) {
                                tokenAvailable1 = true;
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                chatTokens = firebaseAuth.getCurrentUser().getUid() + allUserTokens.get(position);
                chatTokens1 = allUserTokens.get(position) + firebaseAuth.getCurrentUser().getUid();

                if (tokenAvailable) {
                    Intent intent = new Intent(context, ChatRoom.class);
                    intent.putExtra("passToken", chatTokens);
                    context.startActivity(intent);
                    tokenAvailable = false;

                } else if (tokenAvailable1) {
                    Intent intent = new Intent(context, ChatRoom.class);
                    intent.putExtra("passToken", chatTokens1);
                    context.startActivity(intent);
                    tokenAvailable1 = false;
                } else {
                    // when no user inside database
                    Intent intent = new Intent(context, ChatRoom.class);
                    intent.putExtra("passToken", chatTokens);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return allUserTokens.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView newUser;
        private final CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            newUser = itemView.findViewById(R.id.new_user_chat);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public TextView getNewUser() {
            return newUser;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

}
