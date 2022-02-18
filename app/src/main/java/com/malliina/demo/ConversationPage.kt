package com.malliina.demo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun ConversationPage(flow: Flow<PagingData<Message>>, navController: NavHostController, onError: () -> Unit) {
  // A surface container using the 'background' color from the theme
  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
    Column {
      Button(onClick = { navController.navigate(Nav.Second) }, Modifier.fillMaxWidth()) {
        Text("Go")
      }
      Conversation(flow, navController, onError)
    }
  }
}

data class Message(val author: String, val body: String)

@Composable
fun Conversation(flow: Flow<PagingData<Message>>, navController: NavHostController, onError: () -> Unit) {
  val lazyMessages = flow.collectAsLazyPagingItems()
  LazyColumn {
    if (lazyMessages.loadState.append is LoadState.Error) {
      Timber.i("Erroring...")
      onError()
    }
    if (lazyMessages.loadState.refresh == LoadState.Loading) {
      item {
        Column(
          modifier = Modifier.fillParentMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          CircularProgressIndicator()
        }
      }
    }
    items(lazyMessages) { message ->
      message?.let { msg ->
        MessageCard(msg) {
          navController.navigate(Nav.Message)
        }
      }
    }
    if (lazyMessages.loadState.append == LoadState.Loading) {
      item {
        CircularProgressIndicator(
          modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
        )
      }
    }
  }
}

@Composable
fun MessageCard(msg: Message, onClick: () -> Unit) {
  Row(modifier = Modifier.padding(all = 8.dp)) {
    Image(
      painter = painterResource(R.drawable.kopp_small),
      contentDescription = "Kopp",
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp))
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
      if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
    )
    Column(modifier = Modifier.clickable { onClick() }) {
      Text(
        text = msg.author,
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.subtitle2
      )
      Spacer(modifier = Modifier.height(4.dp))
      Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = surfaceColor,
        modifier = Modifier
          .animateContentSize()
          .padding(1.dp)
      ) {
        Text(
          text = msg.body,
          modifier = Modifier.padding(all = 4.dp),
          maxLines = if (isExpanded) Int.MAX_VALUE else 1,
          style = MaterialTheme.typography.body2
        )
      }
    }
  }
}
