package `fun`.yeshu.libs.demo

import `fun`.yeshu.libs.chart.radar.Point
import `fun`.yeshu.libs.chart.radar.RadarChart
import `fun`.yeshu.libs.chart.radar.RadarChartData
import `fun`.yeshu.libs.demo.ui.theme.Android_libsTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_libsTheme {
                // A surface container using the 'background' color from the theme
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        color = MaterialTheme.colors.background
    ) {
        val chartData = RadarChartData(
            pointList = listOf(
                Point("Android", 0.9f),
                Point("Flutter", 0.8f),
                Point("Java", 0.8f),
                Point("Kotlin", 0.8f),
                Point("Python", 0.7f),
                Point("PHP", 0.8f),
            )
        )
        RadarChart(chartData = chartData)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Android_libsTheme{
        Content()
    }
}