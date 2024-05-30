package com.example.gharonkikahani.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gharonkikahani.viewmodel.GeminiAIViewModel1
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

@Composable
fun PhotoBottomSheetContent(
    bitmaps: List<Bitmap>,
    geminiAIViewModel: GeminiAIViewModel1,
    navigate: () -> Unit,
    modifier: Modifier,
) {

    val context = LocalContext.current

    val uiState by geminiAIViewModel.uiState.collectAsStateWithLifecycle()
    val bitmapState by geminiAIViewModel.bitmapState.collectAsStateWithLifecycle()
//    val scope = rememberCoroutineScope()
    LaunchedEffect(uiState) {
        if (uiState.isLoading == false) {

            geminiAIViewModel.saveSession()

            //go to result screen
            navigate.invoke()
        }
    }


    if (bitmaps.isEmpty()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("There are no photos yet")
        }
    } else {

        //recomposition

        if (uiState.isLoading == true) { //if isLoading true, the progress indicator should pop up on the sheet
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier.fillMaxSize())
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp,
                contentPadding = PaddingValues(16.dp),
                modifier = modifier
            ) {
                items(bitmaps) { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                if (bitmapState.bitmapUri == null) {
                                    geminiAIViewModel.initializeLoading()
                                    println("Size of Bitmap: ${bitmap.allocationByteCount}")

                                    geminiAIViewModel.promptImg(getResizedBitmap(bitmap))
                                    val path = saveImage(bitmap, context)
                                    geminiAIViewModel.getSelectedBitmap(path)
                                }
                            }
                    )
                }
            }


        }
    }
}

private fun getResizedBitmap(image: Bitmap): Bitmap {
    val maxSize = 1000
    var width = image.width
    var height = image.height
    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(image, width, height, true)
}

private fun saveImage(bitmap: Bitmap, context: Context): String {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 35, bytes)
    return MediaStore.Images.Media.insertImage(
        context.contentResolver,
        bitmap,
        "SelectedImage-${LocalDateTime.now()}",
        null
    )
}


