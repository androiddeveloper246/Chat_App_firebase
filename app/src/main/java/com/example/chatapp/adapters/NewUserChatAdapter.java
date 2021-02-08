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

public class NewUserChatAdapter extends RecyclerView.Adapter<NewUserChatAdapter.MyViewHolder> {

    Context context;
    public NewUserChatAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_chat_item,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatRoom.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView newUser;
        CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        newUser = itemView.findViewById(R.id.new_user_chat);
        cardView = itemView.findViewById(R.id.card_view);
    }

    public TextView getNewUser(){
        return newUser;
    }

    public CardView getCardView(){return cardView;}
}

}
