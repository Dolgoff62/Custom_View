package ru.netology.custom_view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.custom_view.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StatsView>(R.id.stats).data = listOf(
                0.25F,
                0.25F,
                0.25F,
                0.25F
        )
    }
}