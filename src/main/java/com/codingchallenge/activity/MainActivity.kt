package com.codingchallenge.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.codingchallenge.R

class MainActivity : AppCompatActivity() {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.main_activity_label)
        // supportFragmentManager.beginTransaction().add(R.id.messagelist, MessageListFragment.newInstance(),"SS").commit()

    }
}
