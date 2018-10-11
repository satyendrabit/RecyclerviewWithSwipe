package com.codingchallenge.provider.mock;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.codingchallenge.provider.IMessageRepository;
import com.codingchallenge.provider.Message;
import com.codingchallenge.provider.SampleMessagesKt;

import java.util.List;

public class MockMessageRepository implements IMessageRepository {

    LiveData<List<Message>> mMessageList;

    public MockMessageRepository() {
        mMessageList = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Message>> getMessagesLiveData() {
        ((MutableLiveData<List<Message>>) mMessageList).setValue(SampleMessagesKt.getPREPOPULATE_DATA());
        return mMessageList;
    }

    @Override
    public void updateMessage(Message message) {

    }

    @Override
    public void insertMessages(List<Message> messageList) {

        ((MutableLiveData<List<Message>>) mMessageList).setValue(messageList);

    }
}
