package com.codingchallenge.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingchallenge.R;
import com.codingchallenge.provider.Message;
import com.codingchallenge.provider.MessageDatabase;
import com.codingchallenge.provider.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class MessageListFragment extends Fragment implements com.codingchallenge.fragment.MessageItemTouchHelperCallback.RecyclerItemTouchHelperListener {

    public final static String MY_SHARED_PREF = "MY_SHARED_PREF";
    public final static String MESSAGE_STORED_TO_DB = "MESSAGE_STORED_TO_DB";
    @BindView(R.id.list)
    RecyclerView mRecyclerViewList;
    List<Message> mMessageList;
    com.codingchallenge.fragment.MessageListAdapter mMessageListAdapter;
    private MessageListViewModel mViewModel;

    public static MessageListFragment newInstance() {
        return new MessageListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_list, container, false);
        mMessageList = new ArrayList<>();
        mMessageListAdapter = new com.codingchallenge.fragment.MessageListAdapter(mMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation

        ButterKnife.bind(this, view);
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
        mRecyclerViewList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewList.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));

        mRecyclerViewList.setAdapter(mMessageListAdapter);
        initializeRecyclerviewTouchListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, new com.codingchallenge.fragment.MessageListViewModelFactory
                (new MessageRepository(Executors.newSingleThreadExecutor(),
                        MessageDatabase.Companion.getInstance(getActivity()
                                .getApplicationContext()).messageDao()))).get(MessageListViewModel.class);

        /** Store the data from SampleMessages,for first time in Local DB,
         * Ignore if it is already stored. */
        if (!isDataIsAlreadySavedInDB()) {
            mViewModel.saveMessagesToRepository();
            updateSToredDBStatusInSharedPref();
        }
    }

    @Override
    public void onStart() {
        getLiveData();
        super.onStart();
    }

    public void initializeRecyclerviewTouchListener() {
        new ItemTouchHelper(new com.codingchallenge.fragment.MessageItemTouchHelperCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT, this))
                .attachToRecyclerView(mRecyclerViewList);
    }


    /**
     * OnSwiped,
     *
     * @param viewHolder
     * @param direction
     * @param position
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        // on swipe get the swiped item, set the value to true, and update the db.
        // Do not take any action if  read is already true.
        if (mMessageList != null && mMessageList.size() > position) {
            Message message = mMessageList.get(position);
            if (!message.getRead()) {
                message.setRead(true);
                mViewModel.updateMessageToRepository(message);
            }
            mMessageListAdapter.notifyDataSetChanged();
        }


    }

    public void getLiveData() {
        mViewModel.getmMessageMutableLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messageList) {
                mMessageList = messageList;
                mMessageListAdapter.setMessageList(mMessageList);
                mMessageListAdapter.notifyDataSetChanged();
            }
        });
    }


    public boolean isDataIsAlreadySavedInDB() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getBoolean(MESSAGE_STORED_TO_DB, false);
    }

    public void updateSToredDBStatusInSharedPref() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_SHARED_PREF, MODE_PRIVATE).edit();
        editor.putBoolean(MESSAGE_STORED_TO_DB, true);
        editor.apply();
    }
}
