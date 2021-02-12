package com.example.chatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatRoom;
import com.example.chatapp.R;

import java.util.List;

public class MainChatAdapter extends RecyclerView.Adapter<MainChatAdapter.myViewHolder> {

    private List<String> chatToken;
    private Context context;

    public MainChatAdapter(List<String> chat,Context context){
        this.chatToken = chat;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_main_chat,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.getUserChat().setText(chatToken.get(position));
        holder.getMyCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtra("passToken",chatToken.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatToken.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        private final TextView userChat;
        private final CardView myCardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userChat = itemView.findViewById(R.id.main_chat);
            myCardView = itemView.findViewById(R.id.my_card_view);
        }

        public TextView getUserChat(){return userChat;}
        public CardView getMyCardView(){return myCardView;}
    }

}
