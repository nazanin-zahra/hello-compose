import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import java.util.*

class Call(
    val createdAt: Date,
    val phoneNumber: String,
    val type: Type,
    val imageResource: String,
    val name: String
)

enum class Type {
    In,
    Out
}

val callLog = listOf<Call>(
    Call(Date(2021, 11, 9), "09121231231", Type.In, "venom.jpg", "farhad"),
    Call(Date(2021, 11, 9), "09363456789", Type.Out, "venom.jpg", "nazanin"),
    Call(Date(2021, 11, 10), "09112345678", Type.Out, "flower.jpg", "feri"),
    Call(Date(2021, 11, 10), "09112345678", Type.Out, "flower.jpg", "ati"),
    Call(Date(2021, 11, 10), "09112345678", Type.Out, "venom.jpg", "poyan"),
    Call(Date(2021, 11, 11), "09112345678", Type.Out, "flower.jpg", "atiyeh"),
    Call(Date(2021, 11, 11), "09121231231", Type.In, "flower.jpg", "sara"),
    Call(Date(2021, 11, 11), "09363456789", Type.Out, "venom.jpg", "hany"),
    Call(Date(2021, 11, 12), "09112345678", Type.Out, "flower.jpg", "max"),
    Call(Date(2021, 11, 12), "09121231231", Type.In, "venom.jpg", "jony"),
    Call(Date(2021, 11, 12), "09363456789", Type.Out, "flower.jpg", "jeimen"),
    Call(Date(2021, 11, 12), "09112345678", Type.Out, "venom.jpg", "kok"),
)

@ExperimentalUnitApi
fun main() = Window(title = "calls") {
    MaterialTheme {
        LazyColumn {
            items(callLog) { call: Call ->
                CallRow(
                    createdAt = call.createdAt.toString().substring(0, 10),
                    name = call.name,
                    type = call.type,
                    imageResource = call.imageResource,
                    phoneNumber = call.phoneNumber
                )
            }
        }
    }


}

@ExperimentalUnitApi
@Composable
fun CallRow(createdAt: String, phoneNumber: String, type: Type, imageResource: String, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {}
    ) {
        Image(
            bitmap = imageResource(imageResource),
            contentDescription = null,
            modifier = Modifier

                .size(70.dp)
                .padding(12.dp)
                .clip(CircleShape)
        )

        Column {
            Text(
                "$name",
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold,
            )
            Row {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "$phoneNumber   ",
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                )
                Text(
                    "$createdAt",
                    fontWeight = FontWeight.Thin,
                    fontSize = TextUnit(12f, TextUnitType.Sp)
                )
            }
        }
    }
}