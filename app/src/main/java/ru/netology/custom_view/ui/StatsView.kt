package ru.netology.custom_view.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ru.netology.custom_view.R
import ru.netology.custom_view.R.styleable
import ru.netology.custom_view.utils.Utils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)

    private var lineWidth = Utils.dp(context, 5F).toFloat()
    private var fontSize = Utils.dp(context, 40F).toFloat()
    private var colors = emptyList<Int>()
    private var sumValues: Float = 0F
    private var colorOfEmptySpace = ContextCompat.getColor(context, R.color.light_grey)
    private var valueAnimator: ValueAnimator? = null
    private var progress = 0F
    private var totalPercentage = 0F

    init {
        context.withStyledAttributes(attrs, styleable.StatsView) {
            lineWidth = getDimension(styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(styleable.StatsView_fontSize, fontSize)
            colors = listOf(
                getColor(
                    styleable.StatsView_color1,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color2,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color3,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color4,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color5,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color6,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color7,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color8,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color9,
                    randomColor()
                ),
                getColor(
                    styleable.StatsView_color10,
                    randomColor()
                )
            )
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    private var data: List<Float> = emptyList()


    private fun update() {
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 10_000
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        drawCircleBackground(canvas)
        drawPercentage(canvas)

        if (data.isEmpty() || progress == 0F) {
            return
        }
        var startFrom = INITIAL_ANGLE
        val maxAngle = 360 * progress + INITIAL_ANGLE

        for ((index, datum) in data.withIndex()) {

            if (startFrom > maxAngle) return

            val angle = 360 * (datum / sumValues)
            val sweepAngle = min(angle, maxAngle - startFrom)

            drawData(
                index = index,
                canvas = canvas,
                startFrom = startFrom,
                sweepAngle
            )
            startFrom += angle
        }
        drawIfFullCircle(canvas)
    }

    private fun drawPercentage(canvas: Canvas) {
        canvas.drawText(
            "%.2f%%".format(
                totalPercentage
            ),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint
        )
    }

    private fun drawIfFullCircle(canvas: Canvas) {
        if (totalPercentage == 100F && data.sum() != 0F) {
            paint.color = colors[0]
            canvas.drawArc(oval, -90F, 1F, false, paint)
        }
    }

    private fun drawCircleBackground(canvas: Canvas) {
        paint.color = colorOfEmptySpace
        canvas.drawCircle(center.x, center.y, radius, paint)
    }

    private fun drawData(
        index: Int,
        canvas: Canvas,
        startFrom: Float,
        sweepAngle: Float,
    ) {
        paint.color = colors.getOrNull(index) ?: randomColor()
        canvas.drawArc(oval, startFrom, sweepAngle, false, paint)
    }

    private companion object {
        private const val INITIAL_ANGLE = -90F
    }

    fun setData(data: List<Float>, sumValues: Float? = null) {
        if (sumValues == null) {
            this.sumValues = data.sum()
        } else {
            require(sumValues >= data.sum()) {
                "`sumValues` argument should be greater or equal then sum of all `data` values"
            }
            this.sumValues = sumValues
        }
        this.totalPercentage = (data.sum() / this.sumValues) * 100
        this.data = data
        update()
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}
