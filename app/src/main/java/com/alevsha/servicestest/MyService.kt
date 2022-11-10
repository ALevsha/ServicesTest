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

        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0
        scope.launch {
            for (i in start until start + 100){
                delay(1000)
                log("Time $i")
            }
        }

        /**
         * Метод onStartCommand возвращает значение Int, которое оповещает систему, что нужно
         * сделать с сервисом, если процесс приложения будет завершен
         *  START_STICKY = 1 - перезапуск сервиса
         *  START_NOT_STICKY = 2 - завершение сервиса вместе с приложением
         *  START_REDELIVER_INTENT = 3 - перезапуск с повторной передачей прошлого интента
         * */
        return START_NOT_STICKY
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

        private const val EXTRA_START = "EXTRA_START"

        fun myServiceIntent(context: Context, start: Int): Intent{
            return Intent(context, MyService::class.java)
                .putExtra(EXTRA_START, start)
        }

    }
}