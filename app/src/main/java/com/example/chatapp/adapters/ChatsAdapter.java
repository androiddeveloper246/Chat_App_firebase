package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.myViewHolder> {

    List<String> chatData;
    public static boolean send = true;

    public ChatsAdapter(List<String> chatData){
        this.chatData = chatData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (send){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_send,parent,false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_receive,parent,false);
        }
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.getChatView().setText(chatData.get(position));
    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView chatView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            chatView = itemView.findViewById(R.id.chat_view);
        }

        public TextView getChatView() {
            return chatView;
        }
    }
}
