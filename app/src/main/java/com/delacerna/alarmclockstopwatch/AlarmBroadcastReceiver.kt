package com.delacerna.alarmclockstopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Harold on 2/5/2018.
 */
class AlarmBroadcastReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        var textResult: String = intent!!.getStringExtra("extra")

        var mService = Intent(context,AlarmService::class.java)
        mService.putExtra("extra",textResult)
        context!!.startService(mService)
    }


}