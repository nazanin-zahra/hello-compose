package contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

data class Contact(val name: String, val imageName: String)

val contacts = listOf(
    Contact("Nazi", "venom.jpg"),
    Contact("Feri", "flower.jpg"),
    Contact("Ati", "venom.jpg"),
    Contact("Mamad", "venom.jpg"),
    Contact("BTa", "flower.jpg"),
    Contact("Feri2", "flower.jpg"),
    Contact("Feri3", "flower.jpg"),
    Contact("Feri4", "flower.jpg"),
    Contact("Feri5", "flower.jpg"),
    Contact("Ati2", "venom.jpg"),
    Contact("Mamad2", "venom.jpg"),
    Contact("BTa2", "flower.jpg"),
)

@ExperimentalUnitApi
fun main() = application {
    Window(
        title = "Contacts",
        onCloseRequest = { exitApplication() }
    ) {
        MaterialTheme {
            Row {
                LazyColumn(
                    modifier = Modifier
                        .weight(0.4f)
                        .background(color = Color.LightGray)
                ) {
                    items(contacts) { contact ->
                        ContactItem(contact.name, contact.imageName)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                ) {

                }
            }
        }
    }
}

@Composable
fun ContactItem(name: String, imageName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {}
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        Image(
            painter = painterResource(imageName),
            contentDescription = "This image contains some flower.",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold

        )
    }
}