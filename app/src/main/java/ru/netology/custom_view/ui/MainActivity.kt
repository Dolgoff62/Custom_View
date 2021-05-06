package ru.netology.custom_view.ui

import android.os.Bundle
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import ru.netology.custom_view.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<StatsView>(R.id.stats)

        view.setData(
            data = listOf(
                25F,
                25F,
                25F,
                25F
            ),
            sumValues = 200F
        )
    }
}
