package com.codingchallenge.fragment;


import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.codingchallenge.provider.IMessageRepository;
import com.codingchallenge.provider.Message;
import com.codingchallenge.provider.SampleMessagesKt;
import com.codingchallenge.provider.mock.MockMessageRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

public class MessageListViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    IMessageRepository mMessageRepository;
    MessageListViewModel mViewModel;

    @Before
    public void before() {
        mMessageRepository = new MockMessageRepository();
        mViewModel = new MessageListViewModel(mMessageRepository);
    }

    @Test
    public void validateGetMutableLiveData() {
        assert ((mViewModel.getmMessageMutableLiveData().getValue().size() == SampleMessagesKt.getPREPOPULATE_DATA().size()));

    }

    @Test
    public void validateUpdateToRepository() {
        List<Message> list = mViewModel.getmMessageMutableLiveData().getValue();
        Message message = list.get(0);
        message.setRead(true);

        assert (mViewModel.getmMessageMutableLiveData().getValue().get(0).getRead());

    }


}
