package com.delacerna.alarmclockstopwatch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.NotificationCompat

/**
 * Created by Harold on 2/5/2018.
 */
class AlarmService : Service() {
    companion object {
        lateinit var mRing: Ringtone
    }

    var mId: Int = 0
    var running: Boolean = false
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var mtxtState: String = intent!!.getStringExtra("extra")!!
        when (mtxtState) {
            "on" -> mId = 1
            "off" -> mId = 0
        }
        if (!this.running && mId == 1) {
            startAlarm()
            playNotification()
            this.running = true
            this.mId = 0
        } else if (this.running && mId == 0) {
            mRing.stop()
            this.running = false
            this.mId = 0
        } else if (!this.running && mId == 0) {
            this.running = false
            this.mId = 0
        } else if (this.running && mId == 1) {
            this.running = true
            this.mId = 1
        } else {

        }
        return START_NOT_STICKY
    }

    private fun playNotification() {
        var mMainActivity = Intent(this, MainActivity::class.java)
        var mPendingIntent = PendingIntent.getActivity(this, 0, mMainActivity, 0)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notifyManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var mNotification = NotificationCompat.Builder(this)
                .setContentTitle("Alarm Off")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setContentText("Click Me")
                .setContentIntent(mPendingIntent)
                .setAutoCancel(true)
                .build()
        notifyManager.notify(0, mNotification)

    }

    private fun startAlarm() {
        var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mRing = RingtoneManager.getRingtone(baseContext, alarmUri)
        mRing.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.running = false
    }
}