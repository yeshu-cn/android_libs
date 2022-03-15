package `fun`.yeshu.libs.chart.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RadarChart(
    chartData: RadarChartData,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val paint = android.graphics.Paint().apply {
            this.color = chartData.pointTextColor
            this.textSize = chartData.pointTextSize
        }
        // 标签文字最长7个字
        val maxTextWidth = paint.measureText("中华人名共和国")
        // 如果宽大于高，则用高度的一般作为图的半径大小
        val radius = if (canvasWidth > canvasHeight) {
            (canvasHeight - maxTextWidth * 2) / 2f
        } else {
            (canvasWidth - maxTextWidth * 2) / 2f
        }

        val cirX = canvasWidth / 2
        val cirY = canvasHeight / 2
        val angle = 360f / chartData.pointList.size
        // -90 是为了让index 0 的point处于正上方
        val pointAngles = chartData.pointList.mapIndexed { index, _ -> index * angle - 90 }
        // 默认就5个区间吧
        for (i in 5 downTo 1) {
            // 间隔的颜色
            val color = if (i % 2 != 0) chartData.firstBackground else chartData.secondBackground
            drawPolygon(
                drawScope = this,
                pointAngles = pointAngles,
                cirX = cirX,
                cirY = cirY,
                color = color,
                radius = (radius / 5f) * i
            )

            drawPolygonOutline(
                drawScope = this,
                pointAngles = pointAngles,
                cirX = cirX,
                cirY = cirY,
                radius = (radius / 5f) * i,
                lineWidth = chartData.lineWidth,
                lineColor = chartData.lineColor
            )
        }

        val pointXYList = pointAngles.map {
            ChartUtils.calcArcEndPointXY(cirX, cirY, radius, it)
        }

        pointXYList.forEachIndexed { index, point ->
            // draw point line
            drawLine(
                start = Offset(x = cirX, y = cirY),
                end = Offset(x = point.x, y = point.y),
                color = chartData.lineColor,
                strokeWidth = 2f
            )

            // draw point text
            val text = chartData.pointList[index].name
            val textWidth = paint.measureText(text)
            val textHeight = paint.fontMetrics.descent - paint.fontMetrics.ascent
            val textX = when {
                cirX - point.x == 0f -> {
                    point.x - textWidth / 2f
                }
                point.x > cirX -> {
                    point.x + chartData.textMargin
                }
                else -> {
                    point.x - textWidth - chartData.textMargin
                }
            }

            val textY = when (radius) {
                cirY - point.y -> {
                    point.y - chartData.textMargin
                }
                point.y - cirY -> {
                    point.y + chartData.textMargin + textHeight
                }
                else -> {
                    point.y + textHeight / 2f
                }
            }
            drawContext.canvas.nativeCanvas.drawText(
                chartData.pointList[index].name,
                textX,
                textY,
                paint
            )
        }

        drawArea(
            this,
            chartData.pointList,
            pointAngles,
            cirX = cirX,
            cirY = cirY,
            color = chartData.areaColor,
            radius = radius
        )
    }
}

fun drawPolygonOutline(
    drawScope: DrawScope,
    pointAngles: List<Float>,
    cirX: Float,
    cirY: Float,
    radius: Float,
    lineWidth: Float,
    lineColor: Color
) {
    val pointXYList = pointAngles.map {
        ChartUtils.calcArcEndPointXY(cirX, cirY, radius, it)
    }
    val path = Path()
    pointXYList.forEachIndexed { index, pointF ->
        if (0 == index) {
            path.moveTo(pointF.x, pointF.y)
        } else {
            path.lineTo(pointF.x, pointF.y)
        }
    }

    path.close()
    drawScope.drawPath(path = path, color = lineColor, style = Stroke(width = lineWidth))
}

fun drawPolygon(
    drawScope: DrawScope,
    pointAngles: List<Float>,
    color: Color,
    cirX: Float,
    cirY: Float,
    radius: Float
) {
    val pointXYList = pointAngles.map {
        ChartUtils.calcArcEndPointXY(cirX, cirY, radius, it)
    }
    val path = Path()
    pointXYList.forEachIndexed { index, pointF ->
        if (0 == index) {
            path.moveTo(pointF.x, pointF.y)
        } else {
            path.lineTo(pointF.x, pointF.y)
        }
    }

    path.close()
    drawScope.drawPath(path = path, color = color, style = Fill)
}

fun drawArea(
    drawScope: DrawScope,
    points: List<Point>,
    pointAngles: List<Float>,
    color: Color,
    cirX: Float,
    cirY: Float,
    radius: Float
) {

    val pointXYList = pointAngles.mapIndexed { index, angle ->
        ChartUtils.calcArcEndPointXY(cirX, cirY, points[index].value * radius, angle)
    }

    val path = Path()
    pointXYList.forEachIndexed { index, pointF ->
        if (0 == index) {
            path.moveTo(pointF.x, pointF.y)
        } else {
            path.lineTo(pointF.x, pointF.y)
        }
    }

    path.close()
    drawScope.drawPath(path = path, color = color, style = Fill)
}


@Preview
@Composable
fun PreviewRadarChart() {
    val chartData = RadarChartData(
        pointTextSize = 20f,
        pointTextColor = android.graphics.Color.BLACK,
        areaColor = Color(0x880263ff),
        pointList = listOf(
            Point("Android", 0.9f),
            Point("Flutter", 0.8f),
            Point("Java", 0.8f),
            Point("Kotlin", 0.8f),
            Point("Python", 0.7f),
            Point("PHP", 0.8f),
        )
    )
    RadarChart(chartData, modifier = Modifier.fillMaxSize())
}