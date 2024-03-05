package com.example.fatedice

import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fatedice.ui.theme.FateDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FateDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RollableGrid()
                }
            }
        }
    }
}


@Preview
@Composable
fun RollableGrid(){
    var imageList by remember { mutableStateOf(generateRandomImages()) }
    var sum by remember { mutableStateOf(0)}
    fun updateSum(){
           val newImageList = generateRandomImages()
            var newSum = 0
            for (imageId in newImageList){
                when (imageId) {
                    R.drawable.plussign -> newSum++
                    R.drawable.minussign -> newSum--
                }
            }
        sum = newSum
        imageList = newImageList
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(16.dp),
            contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            GridWithImages(images = imageList)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Your rolled  $sum", color = Color.White)
            Button(onClick ={updateSum()},
                modifier = Modifier
                    .padding(100.dp)
                    .size(width = 200.dp, height = 60.dp)
            ){
                Text("Roll")
            }
        }
    }

}

@Composable
fun GridWithImages(images : List<Int>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        items(images.size) {index ->
            GridItem(imageId = images[index])
        }
    }
}

@Composable
fun GridItem(imageId : Int){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
    ){
        Image(
            painter = painterResource(id = imageId) ,
            contentDescription =null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

fun generateRandomImages(): List<Int>{
    val randomImageIndices = List(4){
        when((0..2).random()){
            0 -> R.drawable.blanksign
            1 -> R.drawable.plussign
            else -> R.drawable.minussign
        }
    }
    return randomImageIndices
}
