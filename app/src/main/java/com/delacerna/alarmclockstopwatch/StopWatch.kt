package com.delacerna.alarmclockstopwatch


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.*
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class StopWatch : AppCompatActivity() {

    private lateinit var mBtnStart: ImageView
    private lateinit var mBtnPause: ImageView
    private lateinit var mBtnReset: ImageView
    private lateinit var mBtnLap: ImageView
    private lateinit var mPlay: TextView
    private lateinit var mPause: TextView
    private lateinit var mReset: TextView
    private lateinit var mLap: TextView
    private lateinit var btnStopWatch: TextView
    private lateinit var btnAlarmClock: TextView
    private lateinit var mTimeContainer: LinearLayout
    lateinit var inflater: LayoutInflater
    lateinit var txtTimeView: TextView
    lateinit var addView: View
    lateinit var mHandler: Handler
    lateinit var mTimeView: TextView
    var startTime: Long = 0L
    var timeBuff: Long = 0L
    var updateTime: Long = 0L
    var milliSecondTime: Long = 0L
    var sec: Int = 0
    var min: Int = 0
    var millisec: Int = 0
    private val runnable = object : Runnable {
        override fun run() {
            milliSecondTime = SystemClock.uptimeMillis() - startTime
            updateTime = timeBuff + milliSecondTime
            sec = (updateTime / 1000).toInt()
            min = sec / 60
            sec %= 60
            millisec = (updateTime % 1000).toInt()
            mTimeView.text = ("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%2d", millisec))
            mHandler.postDelayed(this, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)
        findViews()

        btnStopWatch.setTextColor(Color.GRAY)
        btnAlarmClock.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mHandler = Handler()
        setDisableButtonORsetColor()
        buttonOnClicks()

    }

    private fun buttonOnClicks() {
        mBtnStart.setOnClickListener {
            startTime()
        }
        mBtnPause.setOnClickListener {
            pauseTime()
        }
        mBtnReset.setOnClickListener {
            resetTime()
        }
        mBtnLap.setOnClickListener {
            inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            addView = inflater.inflate(R.layout.time_list_layout, null)
            txtTimeView = addView.findViewById(R.id.textContainer)
            txtTimeView.text = mTimeView.text.toString()
            mTimeContainer.addView(addView)
        }
    }

    private fun resetTime() {
        milliSecondTime = 0L
        startTime = 0L
        timeBuff = 0L
        updateTime = 0L
        sec = 0
        min = 0
        millisec = 0
        mTimeView.text = "00:00:00"
        mTimeContainer.removeAllViews()
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mReset.setTextColor(Color.GRAY)
    }

    private fun pauseTime() {
        timeBuff += milliSecondTime
        mHandler.removeCallbacks(runnable)
        mBtnPause.visibility = View.INVISIBLE
        mBtnStart.visibility = View.VISIBLE
        mBtnReset.isEnabled = true
        mBtnReset.setColorFilter(Color.WHITE)
        mReset.setTextColor(Color.WHITE)
        mBtnLap.isEnabled = false
        mBtnLap.setColorFilter(Color.GRAY)
        mLap.setTextColor(Color.GRAY)
        mPause.visibility = View.INVISIBLE
        mPlay.visibility = View.VISIBLE
    }

    private fun startTime() {
        startTime = SystemClock.uptimeMillis()
        mHandler.postDelayed(runnable, 0)
        mBtnStart.visibility = View.INVISIBLE
        mBtnPause.visibility = View.VISIBLE
        mBtnLap.isEnabled = true
        mBtnLap.setColorFilter(Color.WHITE)
        mLap.setTextColor(Color.WHITE)
        mPause.visibility = View.VISIBLE
        mPause.visibility = View.VISIBLE
        mPlay.visibility = View.INVISIBLE
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mReset.setTextColor(Color.GRAY)
    }

    private fun setDisableButtonORsetColor() {
        mBtnReset.isEnabled = false
        mBtnReset.setColorFilter(Color.GRAY)
        mReset.setTextColor(Color.GRAY)
        mBtnLap.isEnabled = false
        mBtnLap.setColorFilter(Color.GRAY)
        mLap.setTextColor(Color.GRAY)
    }

    private fun findViews() {
        mBtnStart = findViewById(R.id.btnStart)
        mBtnPause = findViewById(R.id.btnPause)
        mBtnReset = findViewById(R.id.btnReset)
        mBtnLap = findViewById(R.id.btnLap)
        mTimeView = findViewById(R.id.tvTimer)
        btnAlarmClock = findViewById(R.id.toolbar_AlarmClock)
        btnStopWatch = findViewById(R.id.toolbar_StopWatch)
        mPlay = findViewById(R.id.txtPlay)
        mPause = findViewById(R.id.txtPause)
        mReset = findViewById(R.id.txtReset)
        mLap = findViewById(R.id.txtLap)
        mTimeContainer = findViewById(R.id.tvTimeContainer)
    }
}
