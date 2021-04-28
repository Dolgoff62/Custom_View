package ru.netology.custom_view.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.netology.custom_view.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<StatsView>(R.id.stats).let {
            it.data = listOf(
                20F,
                20F,
                20F,
                15F
            )
            it.sumValues = it.data.sum()
            it.colorOfEmptySpace = ContextCompat.getColor(this, R.color.light_grey)
        }
    }
}