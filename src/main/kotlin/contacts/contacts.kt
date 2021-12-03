package contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import java.sql.DriverManager
import java.sql.ResultSet

data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val imageName: String,
    val email: String? = null
)

@ExperimentalUnitApi
fun main() = application {
    val connection = DriverManager.getConnection("jdbc:sqlite:src/main/kotlin/contacts/MyContacts.db")
    val statement = connection.createStatement()
    val result = statement.executeQuery("SELECT * FROM ContactTable")

    Window(
        title = "Contacts",
        onCloseRequest = { exitApplication() }
    ) {
        MaterialTheme {
            Row {
                val contacts = remember {
                    val list = mutableStateListOf<Contact>()

                    while (result.next()) {
                        val id = result.getInt("ContactId")
                        val name = result.getString("ContactName")
                        val phone = result.getString("Phone")
                        val email = result.getString("Email")
                        val avatar = result.getString("Avatar")

                        list.add(
                            Contact(
                                id = id,
                                name = name,
                                phoneNumber = phone,
                                imageName = avatar,
                                email = email
                            )
                        )
                    }

                    list
                }

                val nameState = remember { mutableStateOf("") }
                val phoneState = remember { mutableStateOf("") }
                val phoneHasErrorState = remember { mutableStateOf(false) }
                val clickedIndexState = remember { mutableStateOf<Int?>(null) }
                val nameErrorState = remember { mutableStateOf<String?>(null) }
                val emailState = remember { mutableStateOf<String?>(null) }
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
                                nameErrorState.value = null
                                emailState.value = contact.email
                            },
                            onDeleteClick = {
                                statement.executeUpdate("DELETE FROM ContactTable WHERE ContactId='${contact.id}'")
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
                    Column {
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
                            singleLine = true,
                            isError = nameErrorState.value != null
                        )
                        if (nameErrorState.value != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = nameErrorState.value!!,
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }

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
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = emailState.value ?: "",
                            onValueChange = {
                                emailState.value = it
                            },
                            placeholder = { Text("Enter email") },
                            label = {
                                Text("Email")

                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { emailState.value = "" },
                                    enabled = emailState.value.isNullOrBlank().not()
                                ) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)

                                }
                            }
                        )

                    }


                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        enabled = clickedIndexState.value != null,
                        onClick = {
                            if (nameState.value == "")
                                nameErrorState.value = "name is empty"
                            else if (nameState.value.length < 3)
                                nameErrorState.value = "the characters are not enough"
                            else
                                nameErrorState.value = null

                            phoneHasErrorState.value = phoneState.value == ""

                            if (clickedIndexState.value != null
                                && nameErrorState.value == null
                                && phoneHasErrorState.value.not()
                            ) {
                                val clickedItem = contacts[clickedIndexState.value!!]
                                val newItem = clickedItem.copy(
                                    name = nameState.value,
                                    phoneNumber = phoneState.value,
                                    email = emailState.value
                                )
                                contacts[clickedIndexState.value!!] = newItem
                                statement.executeUpdate(
                                    "UPDATE ContactTable SET " +
                                        "ContactName='${newItem.name}', " +
                                        "Email='${newItem.email}', " +
                                        "Phone='${newItem.phoneNumber}' " +
                                        "WHERE ContactId='${clickedItem.id}'"
                                )
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