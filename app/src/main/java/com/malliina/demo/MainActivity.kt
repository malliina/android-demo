package com.malliina.demo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.malliina.demo.ui.theme.DemoAppTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
  private val viewModel: DemoViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else NoLogging()
    Timber.plant(tree)
    Timber.i("onCreate")
    setContent {
      DemoAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          Conversation(viewModel.pager)
        }
      }
    }
  }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
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
    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
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

@Composable
fun Conversation(pager: Pager<LimitOffset, Message>) {
  val p = pager
  val lazyMessages = p.flow.collectAsLazyPagingItems()

  LazyColumn {
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
        MessageCard(msg)
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

@Preview(showBackground = true, name = "Default Mode")
@Composable
fun DefaultPreview() {
  DemoAppTheme {
    Conversation(SampleData.pager)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false, name = "Dark Mode")
@Composable
fun DarkModePreview() {
  DemoAppTheme {
    Conversation(SampleData.pager)
  }
}