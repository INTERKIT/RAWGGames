package com.truetask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.truetask.R
import com.truetask.games.ui.list.ListFragment
import com.truetask.utils.popBackStack
import com.truetask.utils.replace

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        if (savedInstanceState != null) return

        replace(ListFragment.create())
    }

    override fun onBackPressed() {
        popBackStack()
    }
}