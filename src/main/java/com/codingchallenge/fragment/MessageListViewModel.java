package com.codingchallenge.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.codingchallenge.provider.IMessageRepository;
import com.codingchallenge.provider.Message;
import com.codingchallenge.provider.SampleMessagesKt;

import java.util.List;

public class MessageListViewModel extends ViewModel {
    LiveData<List<Message>> mMessageMutableLiveData;
    IMessageRepository mMessageRepository;

    public MessageListViewModel(IMessageRepository messageRepository) {
        mMessageMutableLiveData = new MutableLiveData<>();
        mMessageRepository = messageRepository;
    }

    public LiveData<List<Message>> getmMessageMutableLiveData() {
        mMessageMutableLiveData = mMessageRepository.getMessagesLiveData();
        return mMessageMutableLiveData;
    }


    public void saveMessagesToRepository() {

        mMessageMutableLiveData = mMessageRepository.getMessagesLiveData();
        if (mMessageMutableLiveData.getValue() == null || mMessageMutableLiveData.getValue().size() == 0) {
            mMessageRepository.insertMessages(SampleMessagesKt.getPREPOPULATE_DATA());
        }
        mMessageMutableLiveData = mMessageRepository.getMessagesLiveData();

    }

    public void updateMessageToRepository(Message message) {
        mMessageRepository.updateMessage(message);
    }


}
