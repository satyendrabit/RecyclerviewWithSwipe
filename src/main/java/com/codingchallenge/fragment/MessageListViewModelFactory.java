package com.codingchallenge.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.codingchallenge.provider.MessageRepository;

public class MessageListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MessageRepository mMessageRepository;


    public MessageListViewModelFactory(MessageRepository messageRepository) {
        mMessageRepository = messageRepository;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MessageListViewModel(mMessageRepository);
    }
}