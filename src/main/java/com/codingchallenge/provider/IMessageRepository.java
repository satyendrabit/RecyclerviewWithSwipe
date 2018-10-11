package com.codingchallenge.provider;

import android.arch.lifecycle.LiveData;

import java.util.List;

public interface IMessageRepository {

    LiveData<List<Message>> getMessagesLiveData();

    void updateMessage(final Message message);


    void insertMessages(final List<Message> messageList);
}


