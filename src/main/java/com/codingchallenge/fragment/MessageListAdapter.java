package com.codingchallenge.fragment;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codingchallenge.R;
import com.codingchallenge.provider.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    List<Message> mMessageList;


    public MessageListAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }


    public void setMessageList(List<Message> messageList) {
        mMessageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_cell, parent, false);
        MessageViewHolder vh = new MessageViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        if (mMessageList != null && mMessageList.size() > position) {
            holder.populate(mMessageList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList != null ? mMessageList.size() : 0;
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.subject)
        public TextView tvSubject;

        @BindView(R.id.list_background)
        public RelativeLayout listBackground;

        @BindView(R.id.list_foreground)
        public RelativeLayout listForeground;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        public void populate(Message message) {
            if (message.getRead()) {
                this.tvSubject.setTypeface(Typeface.DEFAULT);
            } else {
                this.tvSubject.setTypeface(Typeface.DEFAULT_BOLD);
            }
            this.tvSubject.setText(message.getSubject());

        }
    }
}
