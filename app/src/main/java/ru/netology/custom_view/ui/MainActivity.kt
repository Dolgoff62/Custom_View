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
        view.data = listOf(
                20F,
                20F,
                20F,
                15F
        )


//        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.view_animation))
    }
}