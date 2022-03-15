package `fun`.yeshu.libs.chart.radar

import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin

object ChartUtils {
    // 根据半径，弧度，圆心点，计算各个点的坐标
    fun calcArcEndPointXY(cirX: Float, cirY: Float, radius: Float, cirAngle: Float): PointF {
        val posX: Float
        val posY: Float
        //将角度转换为弧度
        var arcAngle = (Math.PI * cirAngle / 180.0).toFloat()
        if (cirAngle < 90) {
            posX = cirX + cos(arcAngle.toDouble()).toFloat() * radius
            posY = cirY + sin(arcAngle.toDouble()).toFloat() * radius
        } else if (cirAngle == 90f) {
            posX = cirX
            posY = cirY + radius
        } else if (cirAngle > 90 && cirAngle < 180) {
            arcAngle = (Math.PI * (180 - cirAngle) / 180.0).toFloat()
            posX = cirX - cos(arcAngle.toDouble()).toFloat() * radius
            posY = cirY + sin(arcAngle.toDouble()).toFloat() * radius
        } else if (cirAngle == 180f) {
            posX = cirX - radius
            posY = cirY
        } else if (cirAngle > 180 && cirAngle < 270) {
            arcAngle = (Math.PI * (cirAngle - 180) / 180.0).toFloat()
            posX = cirX - cos(arcAngle.toDouble()).toFloat() * radius
            posY = cirY - sin(arcAngle.toDouble()).toFloat() * radius
        } else if (cirAngle == 270f) {
            posX = cirX
            posY = cirY - radius
        } else {
            arcAngle = (Math.PI * (360 - cirAngle) / 180.0).toFloat()
            posX = cirX + cos(arcAngle.toDouble()).toFloat() * radius
            posY = cirY - sin(arcAngle.toDouble()).toFloat() * radius
        }
        return PointF(posX, posY)
    }
}