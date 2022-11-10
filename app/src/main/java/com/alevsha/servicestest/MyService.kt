package com.alevsha.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*


class MyService: Service() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        log("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")

        scope.launch {
            for (i in 0 until 100){
                delay(1000)
                log("Time $i")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        log("onDestroy")
        scope.cancel()
        super.onDestroy()
    }

    private fun log(message: String){
        Log.d("SERVICE_TAG", "My Service $message")
    }

    companion object{

        fun myServiceIntent(context: Context): Intent{
            return Intent(context, MyService::class.java)
        }

    }
}