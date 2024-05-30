package com.example.gharonkikahani.presentation.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gharonkikahani.session.Session
import com.example.gharonkikahani.ui.theme.poppinsFontFamily
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun RecipeScreen(
    session: Session
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(
            title = "Results",
            modifier = Modifier.padding(top = 10.dp)
        )
        if (session.geminiAIUiState?.isSuccessful == true) {
            ElevatedCard(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {

                session.geminiAIUiState.response?.let {
                    MarkdownText(
                        markdown = it,
                        truncateOnTextOverflow = true,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterHorizontally
                        )

                    )

                }
            }
        }



    }

}







@Composable
fun Title(title: String, modifier: Modifier) {
    Text(
        text = title,
        modifier = modifier,
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}