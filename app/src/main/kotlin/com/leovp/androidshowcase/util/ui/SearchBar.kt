package com.leovp.androidshowcase.util.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.ui.theme.AppTheme

/**
 * Author: Michael Leo
 * Date: 2023/7/26 13:59
 */
@Composable
fun SearchBar(
    onClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = modifier
            .height(48.dp)
            .padding(horizontal = 0.dp, vertical = 8.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        SearchHint(modifier, onActionClick)
    }
}

private val iconSize = 19.dp

@Composable
private fun SearchHint(modifier: Modifier = Modifier, onActionClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        // .wrapContentSize()
    ) {
        Icon(
            modifier = Modifier.requiredSize(iconSize),
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = modifier.weight(1f),
            text = "Wellerman Nathan Evans",
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurfaceVariant else Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = onActionClick,
            modifier = Modifier.requiredSize(iconSize),
        ) {
            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        }
    }
}

@Preview("Searchbar")
@Preview("Searchbar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearchBar() {
    AppTheme {
        SearchBar(onClick = {}, onActionClick = {})
    }
}
