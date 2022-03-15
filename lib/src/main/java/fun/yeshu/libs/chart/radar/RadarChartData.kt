package `fun`.yeshu.libs.chart.radar

import androidx.compose.ui.graphics.Color

data class RadarChartData(
    // 文字大小
    val pointTextSize: Float = 30f,
    // 文字颜色
    val pointTextColor: Int = android.graphics.Color.BLACK,
    // 文字和图的间距
    val textMargin: Int = 20,

    // 点列表
    val pointList: List<Point> = emptyList(),
    // 线宽度
    val lineWidth: Float = 2f,
    // 线颜色
    val lineColor: Color = Color(0xffc0c0c0),
    // 中间值区域度颜色
    val areaColor: Color = Color(0x880263ff),

    // 图的两个间隔颜色
    val firstBackground: Color = Color(0xffe6e6e6),
    val secondBackground: Color = Color(0xfff5f5f5)
)
