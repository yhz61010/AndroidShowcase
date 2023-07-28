package com.leovp.androidshowcase.util.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.ui.theme.AppTheme
import com.leovp.androidshowcase.ui.theme.discovery_search_bar_end_color
import com.leovp.androidshowcase.ui.theme.discovery_search_bar_start_color

/**
 * Author: Michael Leo
 * Date: 2023/7/26 13:59
 */

private val iconSize = 19.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    backgroundColor: Color ? = MaterialTheme.colorScheme.surface,
    backgroundBrush: Brush? = null,
    onClick: () -> Unit,
    onActionClick: () -> Unit
) {
    val containerHorizontalPadding = 16.dp
    Surface(
        shape = CircleShape,
        border = border,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(46.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        var rowModifier = Modifier
            .fillMaxWidth()
        // .wrapContentSize()
        backgroundColor?.let {
            rowModifier = rowModifier.background(color = it, shape = CircleShape)
        }
        backgroundBrush?.let {
            rowModifier = rowModifier.background(brush = it, shape = CircleShape)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            Spacer(Modifier.width(containerHorizontalPadding))
            Icon(
                modifier = Modifier.requiredSize(iconSize),
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint
            )
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
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
            Spacer(Modifier.width(containerHorizontalPadding))
        }
    }
}

internal val defaultLinearGradient: Brush
    get() = Brush.linearGradient(
        listOf(
            discovery_search_bar_start_color,
            discovery_search_bar_end_color,
        ),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY),
        tileMode = TileMode.Clamp
    )

@Preview("Searchbar")
@Preview("Searchbar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearchBar() {
    AppTheme {
        SearchBar(
            border = BorderStroke(
                width = 0.5.dp,
                brush = defaultLinearGradient
            ),
            backgroundBrush = defaultLinearGradient,
            onClick = {},
            onActionClick = {}
        )
    }
}
