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
fun main() = Window(title = "Contacts") {
    MaterialTheme {
/*
        Column {
            contacts.forEach { contact ->
                ContactItem(contact.name, contact.imageName)
            }
        }
        if you write it like this you wont be able to see other contacts whicj are not in the screen
*/

        LazyColumn {
            items(contacts) { contact ->
                ContactItem(contact.name, contact.imageName)
            }
        }
    }
    //item () is itorator of list
}

@ExperimentalUnitApi
@Composable
fun ContactItem(name: String, imageName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {}
            .padding(8.dp)

    ) {
        Image(
            bitmap = imageResource(imageName),
            contentDescription = "This image contains some flower.",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )
    }
}