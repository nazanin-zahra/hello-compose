import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@ExperimentalUnitApi
fun main() = Window(title = "Hello Compose Application") {
    MaterialTheme (colors = MaterialTheme.colors.copy(primary = Color.Green)){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header()
            Text("This is JetPack Compose!")
            Image(
                bitmap = imageResource("flower.jpg"),
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Row {
                Button(onClick = {}) {
                    Text("Run")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {},modifier = Modifier.padding(start = 8.dp)) {
                    Text("End")
                }
            }

            Spacer(Modifier.height(8.dp))

            Checkbox(
                checked = true,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    uncheckedColor = Color.Blue
                )
            )
        }
    }
}

@ExperimentalUnitApi
@Composable
private fun Header() {
    Text(
        "Hello World!",
        fontSize = TextUnit(25f, TextUnitType.Sp),
        fontWeight = FontWeight.Bold,
        color = Color.Red,
      //  textAlign = TextAlign.Center,
        modifier = Modifier.width(IntrinsicSize.Max)
    )
}