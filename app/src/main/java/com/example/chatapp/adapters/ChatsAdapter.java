package com.example.chatapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.myViewHolder> {

    private List<String> chatData;
    private List<Integer> flags;
    private List<String> timeDate;
    private List<Integer> isImageCategory;
    private List<String> imageUrl;
    private List<String> sender;
    Context context;

    public ChatsAdapter(Context context,List<String> chatData, List<Integer> flags, List<String> timeDate, List<String> sender, List<Integer> isImageCategory,List<String> imageUrl) {
        this.chatData = chatData;
        this.flags = flags;
        this.timeDate = timeDate;
        this.sender = sender;
        this.isImageCategory = isImageCategory;
        this.imageUrl = imageUrl;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view1;
        view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_send, parent, false);
        return new myViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        if (isImageCategory.get(position).equals(1)) {

            holder.getChatView().setText(chatData.get(position));
            holder.getDateView().setText(timeDate.get(position));

            if (flags.get(position) == 1) {
                holder.getFlag().setColorFilter(Color.WHITE);
            } else if (flags.get(position) == 2) {
                holder.getFlag().setColorFilter(Color.GREEN);
            } else {
                // later implement when sqlite added when user online
                holder.getFlag().setColorFilter(Color.BLACK);
            }

        } else {

            String tempImageUrl = imageUrl.get(position);

            Glide.with(context).load(tempImageUrl).into(holder.getSendImage());

            holder.getSendImage().setVisibility(View.VISIBLE);
            holder.getDateView().setVisibility(View.INVISIBLE);
            holder.getChatView().setVisibility(View.INVISIBLE);
            holder.getSendImage().setBackgroundColor(Color.TRANSPARENT);
          //  holder.getSendImage().setImageResource(R.drawable.ic_baseline_send_24);
        }
    }

    @Override
    public int getItemCount() {

        return chatData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        private final TextView chatView, dateView;
        private final ImageView flag;
        private final ImageView sendImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            chatView = itemView.findViewById(R.id.chat_view);
            flag = itemView.findViewById(R.id.flag);
            dateView = itemView.findViewById(R.id.date_view);
            sendImage = itemView.findViewById(R.id.send_image);
        }

        public TextView getChatView() {
            return chatView;
        }

        public ImageView getFlag() {
            return flag;
        }

        public TextView getDateView() {
            return dateView;
        }

        public ImageView getSendImage() {
            return sendImage;
        }
    }
}
