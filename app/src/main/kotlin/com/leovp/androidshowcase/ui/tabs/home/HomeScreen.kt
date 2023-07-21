package com.leovp.androidshowcase.ui.tabs.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.ui.tabs.home.data.HomeLocalDataSource
import com.leovp.androidshowcase.ui.tabs.home.data.SimpleListItemModel
import com.leovp.androidshowcase.ui.tabs.home.iters.MarkType
import com.leovp.androidshowcase.ui.theme.mark_hot_bg
import com.leovp.androidshowcase.ui.theme.mark_hot_text_color
import com.leovp.androidshowcase.ui.theme.mark_special_border
import com.leovp.androidshowcase.ui.theme.mark_special_text_color
import com.leovp.androidshowcase.ui.theme.mark_vip_border
import com.leovp.androidshowcase.ui.theme.mark_vip_text_color

/**
 * Author: Michael Leo
 * Date: 2023/7/18 15:06
 */

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
        modifier = modifier.fillMaxSize(),
        state = rememberLazyListState()
    ) {
        items(HomeLocalDataSource.homeList) { data ->
            HomeScreenContentItems(data)
        }
    }
}

@Composable
fun HomeScreenContentItems(data: SimpleListItemModel) {
    val context = LocalContext.current
    ListItem(
        modifier = Modifier.clickable { context.toast("You clicked item ${data.title}") },
        headlineContent = {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val smallRounded = MaterialTheme.shapes.small
                val modifier = when (data.type) {
                    MarkType.Hot -> Modifier.background(color = mark_hot_bg, shape = smallRounded)
                    MarkType.Special -> Modifier.border(
                        width = 1.dp,
                        color = mark_special_border,
                        shape = smallRounded
                    )

                    MarkType.Vip -> Modifier.border(
                        width = 1.dp,
                        color = mark_vip_border,
                        shape = smallRounded
                    )
                }
                val textColor = when (data.type) {
                    MarkType.Hot -> mark_hot_text_color
                    MarkType.Special -> mark_special_text_color
                    MarkType.Vip -> mark_vip_text_color
                }
                Text(
                    modifier = modifier.padding(horizontal = 4.dp),
                    text = data.markText,
                    color = textColor,
                    fontSize = TextUnit(10.0f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = data.subTitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        trailingContent = {
            if (data.showTrailIcon) {
                Icon(
                    modifier = Modifier.alpha(0.6f),
                    imageVector = Icons.Outlined.SmartDisplay,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }
        },
        leadingContent = {
            ListItemImage(
                imageUrl = data.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
        }
    )
}

// @Composable
// fun HomeScreenContentItems(num: Int) {
//     val context = LocalContext.current
//     ConstraintLayout(
//         modifier = Modifier
//             .fillMaxWidth()
//             .clickable { context.toast("Click item $num") }
//             .padding(horizontal = 16.dp, vertical = 8.dp),
//     ) {
//         val (image, title, mark, subTitle) = createRefs()
//         createVerticalChain(title, mark, chainStyle = ChainStyle.Spread)
//
//         ListItemImage(
//             imageUrl = "https://picsum.photos/seed/picsum/250",
//             contentDescription = null,
//             modifier = Modifier
//                 .size(56.dp)
//                 .constrainAs(image) {
//                     top.linkTo(parent.top, margin = 0.dp)
//                     bottom.linkTo(parent.bottom, margin = 0.dp)
//                     start.linkTo(parent.start, margin = 0.dp)
//                 }
//         )
//         Text(
//             text = "Title - $num",
//             style = MaterialTheme.typography.titleMedium,
//             color = MaterialTheme.colorScheme.secondary,
//             modifier = Modifier.constrainAs(title) {
//                 start.linkTo(image.end, margin = 16.dp)
//                 end.linkTo(parent.end, margin = 0.dp)
//                 width = Dimension.fillToConstraints
//             },
//             maxLines = 1,
//             overflow = TextOverflow.Ellipsis
//         )
//         Text(
//             modifier = Modifier
//                 .background(color = subtitle_mark_bg, shape = RoundedCornerShape(4.dp))
//                 .padding(horizontal = 4.dp)
//                 .constrainAs(mark) {
//                     start.linkTo(title.start, margin = 0.dp)
//                 },
//             text = "超" + "44%" + "人收藏 >",
//             color = subtitle_mark_text,
//             fontSize = TextUnit(10.0f, TextUnitType.Sp),
//             fontWeight = FontWeight.Bold
//         )
//         Text(
//             text = "Sub title - $num",
//             style = MaterialTheme.typography.labelMedium,
//             color = androidx.compose.ui.graphics.Color.DarkGray,
//             modifier = Modifier.constrainAs(subTitle) {
//                 start.linkTo(mark.end, margin = 4.dp)
//                 end.linkTo(title.end, margin = 0.dp)
//                 top.linkTo(mark.top, margin = 0.dp)
//                 bottom.linkTo(mark.bottom, margin = 0.dp)
//                 width = Dimension.fillToConstraints
//             },
//             maxLines = 1,
//             overflow = TextOverflow.Ellipsis
//         )
//     }
// }

@Composable
fun ListItemImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        shadowElevation = elevation,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = ColorPainter(Color.LightGray),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}