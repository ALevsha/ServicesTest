package com.alevsha.servicestest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.alevsha.servicestest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener{
            startService(MyService.myServiceIntent(this, 25))
        }
        binding.foregroundService.setOnClickListener{
            startForegroundService()
        }
    }

    private fun startForegroundService() {
        /**
         * Для создания уведомления:
         *      * создается NotificationManager
         *      * На устройствах с API выше 25 каждое уведомление должно быть прикреплено к определенному
         *      каналу NotificationChannel
         *      * через notificationManager создается канал уведомлений
         *      * создается уведомление с обязательным указанием заголовка, текста и иконки
         *      уведомления
         *      * через менеджер уведомлений оно выводится на экран
         * */

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        /**
         * проверка производится, если подразумевается использование на устройстве с API < 26
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT /* доп значение особенностей вывода уведомлений*/
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        /**
         * Если API < 26 CHANNEL_ID не надо добавлять в конструктор Notification
         * Чтобы не проводить проверку, используем класс NotificationCompat
         * */
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()


        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
    }
}