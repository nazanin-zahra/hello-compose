import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
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

val callLog = mutableStateListOf(
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

fun main() = application {
    Window(title = "calls",
        onCloseRequest = {}) {
        MaterialTheme {
            LazyColumn {
                items(callLog) { call: Call ->
                    CallRow(call)
                }
            }
        }
    }
}

@Composable
fun CallRow(call: Call) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(call.imageResource),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .padding(12.dp)
                .clip(CircleShape)
        )

        Column {
            Text(
                call.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Row {
                Text(
                    call.phoneNumber,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    call.createdAt.toString().substring(0, 10),
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        Button(
            onClick = {
                callLog.remove(call)
            },
            content = {
                Text("Delete")
            })
    }
}