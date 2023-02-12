package com.example.thirtydays

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.thirtydays.data.Activity
import com.example.thirtydays.model.ActivityModel
import com.example.thirtydays.ui.theme.*

val CARD_SIZE: Dp = 124.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }
}

@Composable
fun MainLayout() {
    val activities: List<Activity> = ActivityModel.load()
    ThirtyDaysTheme(darkTheme = isSystemInDarkTheme()) {
// A surface container using the 'background' color from the theme
        Scaffold(
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colors.background
            ) {
                Box(
                    modifier = Modifier
                        .background(if (!isSystemInDarkTheme()) purplePastel else purpleDark)
                        .fillMaxSize()
                ){
                    Text(
                        text = "30 Days of Cooking",
                        style = MaterialTheme.typography.h2,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(x = 16.dp, y = 32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.drawkit_vector_illustration_food___culture__4_),
                        contentDescription = "Background banner image.",
                        modifier = Modifier
                            .size(304.dp)
                            .align(Alignment.Center)
                            .offset(y = (-124).dp)
                        ,
                        alignment = Alignment.CenterEnd
                    )
                    CardItemsList(activities)
                }
//                CardItemsList(activities)
            }
        }
    }
}

@Composable
fun TopBar(name: String, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
//        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold))
    }
}

//fun shakeKeyFrame(duration: Int = 8): Float {
//    for (i in 1..duration){
//        when(i%3){
//            0 -> 4f
//            1 -> -4f
//            else -> 0f
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(activity: Activity, modifier: Modifier = Modifier){
    val imageSize: Dp = 124.dp

    var doubleTapped: Boolean by remember { mutableStateOf(false) }

    val surfaceColor by animateColorAsState(
        if(doubleTapped) ( if(!isSystemInDarkTheme()) Teal300 else Teal300Dark ) else MaterialTheme.colors.surface,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    val onSurfaceColor by animateColorAsState(
        if(doubleTapped) Color.White else MaterialTheme.colors.onSurface
    )

    val context = LocalContext.current

    val gradient: Brush =  Brush.horizontalGradient(
        colors = listOf(Color.Transparent, surfaceColor.copy(alpha = 0.7f)),
        startX =  imageSize.value,
        endX = imageSize.value / 3,
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CARD_SIZE)
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(context, "Double tap to change status!", Toast.LENGTH_LONG)
                        .show()
                },
                onDoubleClick = {
                    doubleTapped = !doubleTapped
                }
            )
        ,
        backgroundColor =  surfaceColor,
        contentColor = onSurfaceColor,
        elevation = 2.dp
    ) {
        Row(
            Modifier
                .padding(start = 24.dp, top = 16.dp, bottom = 16.dp, end = 24.dp)
                .fillMaxSize()
                ,
        ) {
            Column() {
                Text(text = "123123123", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = activity.name, style = MaterialTheme.typography.h3)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = if(doubleTapped) "finished" else "on progress",
                    style = MaterialTheme.typography.body2.copy(
                        color = if(doubleTapped) Color.Green else Color.Gray
                    ))
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                Modifier
                    .size(imageSize)
                    .align(Alignment.CenterVertically)
            ){
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10))
                        ,
                    painter = painterResource(id = activity.imageId),
                    contentDescription = activity.name
                )
                Box(modifier = Modifier
                    .matchParentSize()
                    .background(gradient))
            }
        }
    }
}

@Composable
fun CardItemsList(activities: List<Activity>, modifier: Modifier = Modifier){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item{
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 2)
                ,
            )
        }

        item {
            SublistCard(activities = activities)
        }
    }
}

@Composable
fun SublistCard(activities: List<Activity>, modifier: Modifier = Modifier){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
//    val screenHeight = (CARD_SIZE + 16.dp) * activities.size

    LazyColumn(
        modifier = modifier
            .clip(
                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .fillMaxWidth()
            .height(screenHeight)
            .background(MaterialTheme.colors.background)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 30.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(activities){
            item: Activity ->  CardItem(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainLayout()
}