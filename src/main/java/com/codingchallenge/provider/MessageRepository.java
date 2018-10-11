package com.codingchallenge.provider;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MessageRepository implements IMessageRepository {
    ExecutorService mExecutor;
    MessageDao mMessageDao;

    public MessageRepository(ExecutorService executor, MessageDao messageDao) {
        mExecutor = executor;
        mMessageDao = messageDao;
    }

    @Override
    public LiveData<List<Message>> getMessagesLiveData() {

        return mMessageDao.getMessageList();

    }

    @Override
    public void updateMessage(final Message message) {
        mExecutor.execute(() -> {
            mMessageDao.update(message);
        });
    }

    @Override
    public void insertMessages(final List<Message> messageList) {
        mExecutor.execute(() -> {
            mMessageDao.insertData(messageList);
        });
    }
}
