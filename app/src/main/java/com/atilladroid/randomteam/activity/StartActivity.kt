package com.atilladroid.randomteam.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.atilladroid.randomteam.AppRater
import com.atilladroid.randomteam.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        bt_start.setOnClickListener {
            startActivity(Intent(this, PlayerTeamActivity::class.java))
        }
        bt_rules.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        val r = AppRater()
        r.app_launched(this)
    }
}
