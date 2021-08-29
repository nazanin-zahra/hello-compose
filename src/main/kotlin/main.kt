import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.unit.ExperimentalUnitApi

@ExperimentalUnitApi
fun main() = Window {
    MaterialTheme {
        Text("This is JetPack Compose!")
    }
}