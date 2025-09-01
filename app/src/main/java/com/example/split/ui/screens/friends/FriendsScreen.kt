package com.example.split.ui.screens.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.navigation.Expenses
import com.example.split.ui.components.Debt
import com.example.split.ui.screens.friends.FriendsViewModel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    viewModel: FriendsViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    setTopBar: (TopBarState?) -> Unit
) {
    when (viewModel.currentUiState) {
        UiState.HOME -> {
            setTopBar(TopBarState(title = "Friends", actionIcon = IconWrapper(Icons.Default.PersonAdd, contentDescription = "Add a Friend", {viewModel.currentUiState = UiState.ADD})))
            Column  (modifier = modifier.padding(horizontal = 10.dp)) {
                ListItem(
                    modifier = modifier.clickable(onClick = {navigate(Expenses.route)}),
                    headlineContent = { Text("Paula") },
                    trailingContent = { Debt(amount = "10€", owes = true) },
                )
                ListItem(
                    headlineContent = { Text("Paul") },
                    trailingContent = { Debt(amount = "10€", owes = true) }
                )
                ListItem(
                    headlineContent = { Text("Christoph") },
                    trailingContent = { Debt(amount = "10€", owes = false) }
                )
            }
            Button(onClick = {viewModel.DEBUG_AddUser()}) { Text("Add User")}
        }
        UiState.ADD -> {
            setTopBar(TopBarState(title = "Add a Friend"))
            AddFriend(onAdd = { viewModel.addFriend(it) })
        }
    }

}

@Composable
fun AddFriend(modifier: Modifier = Modifier, onAdd: (String) -> Unit) {
    var code = rememberTextFieldState()

    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .weight(1f),
//                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Enter your friend's code here",
                textAlign = TextAlign.Center
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 5.dp),
                label = { Text("Enter a Friend Code") },
                state = code,
                placeholder = { Text("Friend Code") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            Button(modifier = Modifier.fillMaxWidth(0.7f), onClick = {onAdd.invoke(code.text.toString())}) {
                Text("Confirm")
            }
        }
        Row (modifier = Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically){
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(modifier = Modifier.padding(horizontal = 30.dp), text = "Or")
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .weight(1f),
//                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Share this code with your friend",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = "XI2OL",
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 26.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}