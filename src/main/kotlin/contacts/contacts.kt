package contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

data class Contact(val name: String, val phoneNumber: String, val imageName: String)

@ExperimentalUnitApi
fun main() = application {
    Window(
        title = "Contacts",
        onCloseRequest = { exitApplication() }
    ) {
        MaterialTheme {
            Row {
                val contacts = remember {
                    mutableStateListOf(
                        Contact("Nazi", "+1123456", "venom.jpg"),
                        Contact("Feri", "+8765431123456", "flower.jpg"),
                        Contact("Ati", "+2309487238", "venom.jpg"),
                        Contact("Mamad", "+348973287", "venom.jpg"),
                        Contact("BTa", "+50968453", "flower.jpg"),
                        Contact("Feri2", "+938743242", "flower.jpg"),
                        Contact("Feri3", "+34823798379", "flower.jpg"),
                        Contact("Feri4", "+3489327", "flower.jpg"),
                        Contact("Feri5", "+96785842", "flower.jpg"),
                        Contact("Ati2", "+32498732", "venom.jpg"),
                        Contact("Mamad2", "+12342432", "venom.jpg"),
                        Contact("BTa2", "+021437826", "flower.jpg"),
                    )
                }

                val nameState = remember { mutableStateOf("") }
                val phoneState = remember { mutableStateOf("") }
                val phoneHasErrorState = remember { mutableStateOf(false) }
                val clickedIndexState = remember { mutableStateOf<Int?>(null) }

                LazyColumn(
                    modifier = Modifier
                        .weight(0.4f)
                        .background(color = Color.LightGray)
                        .fillMaxHeight()
                ) {
                    itemsIndexed(contacts) { index, contact ->
                        val thisItemIsSelected: Boolean = clickedIndexState.value == index
                        ContactItem(
                            contact = contact,
                            isSelected = thisItemIsSelected,
                            onContactClick = {
                                nameState.value = contact.name
                                phoneState.value = contact.phoneNumber
                                clickedIndexState.value = contacts.indexOf(contact)
                                phoneHasErrorState.value = false
                            },
                            onDeleteClick = {
                                contacts.remove(contact)
                            })
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(36.dp))

                    if (clickedIndexState.value == null) {
                        Image(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )
                    } else {
                        Image(
                            painter = painterResource(contacts[clickedIndexState.value!!].imageName),
                            contentDescription = "big avatar of selected user",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }

                    OutlinedTextField(
                        value = nameState.value,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20)
                                nameState.value = newValue
                        },
                        label = {
                            Text("Name")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                nameState.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        singleLine = true
                    )

                    Column {
                        OutlinedTextField(
                            value = phoneState.value,
                            onValueChange = { enteredValue ->
                                val newValue = enteredValue.filter { it.isDigit() || it == '+' }
                                phoneState.value = newValue
                            },
                            label = {
                                Text("PhoneNumber")
                            },
                            trailingIcon = {
                                IconButton(onClick = { phoneState.value = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null
                                    )
                                }
                            },
                            singleLine = true,
                            isError = phoneHasErrorState.value,
                            placeholder = { Text("Enter phone") }
                        )
                        if (phoneHasErrorState.value) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Phone is empty.",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        enabled = clickedIndexState.value != null,
                        onClick = {
                            if (phoneState.value == "")
                                phoneHasErrorState.value = true
                            else if (clickedIndexState.value != null) {
                                val clickedItem = contacts[clickedIndexState.value!!]
                                val newItem = clickedItem.copy(
                                    name = nameState.value,
                                    phoneNumber = phoneState.value
                                )
                                contacts[clickedIndexState.value!!] = newItem
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, isSelected: Boolean, onContactClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = if (isSelected)
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .clickable { onContactClick() }
                .padding(8.dp)
                .fillMaxWidth()
        else
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onContactClick() }
                .padding(8.dp)
                .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(contact.imageName),
            contentDescription = "This image contains some flower.",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = contact.phoneNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        IconButton(onClick = {
            onDeleteClick()
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}