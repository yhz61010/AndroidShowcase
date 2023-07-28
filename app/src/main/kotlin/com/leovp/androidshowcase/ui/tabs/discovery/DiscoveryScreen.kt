package com.leovp.androidshowcase.ui.tabs.discovery

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.framework.FakeDI
import com.leovp.androidshowcase.ui.tabs.discovery.data.CarouselItemModel
import com.leovp.androidshowcase.ui.tabs.discovery.data.EverydayItemModel
import com.leovp.androidshowcase.ui.tabs.discovery.data.SimpleListItemModel
import com.leovp.androidshowcase.ui.tabs.discovery.iters.MarkType
import com.leovp.androidshowcase.ui.theme.mark_hot_bg
import com.leovp.androidshowcase.ui.theme.mark_hot_text_color
import com.leovp.androidshowcase.ui.theme.mark_special_border
import com.leovp.androidshowcase.ui.theme.mark_special_text_color
import com.leovp.androidshowcase.ui.theme.mark_vip_border
import com.leovp.androidshowcase.ui.theme.mark_vip_text_color
import com.leovp.androidshowcase.util.floorMod
import com.leovp.androidshowcase.util.viewModelProviderFactoryOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2023/7/18 15:06
 */

private const val TAG = "Discovery"

@Composable
fun DiscoveryScreen(
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    viewModel: DiscoveryVM = viewModel(factory = viewModelProviderFactoryOf { DiscoveryVM(FakeDI.discoveryRepository) })
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
        modifier = modifier.fillMaxSize(),
        state = scrollState
    ) {
        item {
            CarouselHeader(uiState.carouselRecommends) { item ->
                ctx.toast("Carousel recommend item: $item")
            }
        }
        item {
            EverydayRecommendsHeaderItem()
            EverydayRecommendsItem(uiState.everydayRecommends) { item ->
                ctx.toast("Everyday recommend item: $item")
            }
        }
        item {
            DiscoveryScreenContentHeadItem()
        }
        items(uiState.personalRecommends) { data ->
            DiscoveryScreenContentItems(data)
        }
    }
}

@Composable
fun DiscoveryScreenContentHeadItem() {
    Row(
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "华语精选",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Black
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.requiredSize(24.dp),
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.Gray,
                contentDescription = null
            )
        }
    }
}

@Composable
fun EverydayRecommendsHeaderItem() {
    Row(
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "每日推荐",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = "8939187人在听",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            modifier = Modifier.requiredSize(24.dp),
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.Gray,
                contentDescription = null
            )
        }
    }
}

@Composable
fun EverydayRecommendsItem(list: List<EverydayItemModel>, onItemClick: (EverydayItemModel) -> Unit) {
    val pagerState = rememberLazyListState()
    val cardWidth = 120.dp

    LazyRow(
        contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 8.dp),
        modifier = Modifier.fillMaxWidth(),
        state = pagerState
    ) {
        itemsIndexed(list) { idx, data ->
            Column(
                modifier = Modifier.padding(end = if (idx == list.lastIndex) 0.dp else 10.dp),
            ) {
                Card(
                    modifier = Modifier
                        .clickable { onItemClick(data) }
                        // .padding(horizontal = 16.dp, vertical = 0.dp)
                        .size(cardWidth),
                    shape = MaterialTheme.shapes.large
                ) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data.thumbnail)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            placeholder = ColorPainter(Color.LightGray),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                        Row(
                            modifier = Modifier.padding(start = 8.dp, top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            data.icon?.let { icon ->
                                Icon(
                                    modifier = Modifier
                                        .padding(end = 2.dp)
                                        .size(16.dp),
                                    imageVector = icon,
                                    tint = Color.White,
                                    contentDescription = null,
                                )
                            }
                            Text(
                                text = data.type,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Black
                                )
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .requiredWidth(cardWidth)
                        .padding(top = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    text = data.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselHeader(list: List<CarouselItemModel>, onItemClick: (CarouselItemModel) -> Unit) {
    val pageCount = list.size
    val pagerState = rememberPagerState(0)

    val pageCountIndex by remember { derivedStateOf { pagerState.currentPage.floorMod(list.size) } }
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(key1 = currentTime) {
        launch {
            delay(3000L)
            val target = if (pagerState.currentPage < pageCount - 1) pagerState.currentPage + 1 else 0

            pagerState.animateScrollToPage(
                page = target,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            )

            currentTime = System.currentTimeMillis()
        }
    }

    Box {
        HorizontalPager(
            contentPadding = PaddingValues(bottom = 8.dp),
            beyondBoundsPageCount = 1,
            pageCount = pageCount,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Card(
                modifier = Modifier
                    .clickable { onItemClick(list[page]) }
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(list[page].thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = ColorPainter(Color.LightGray),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp)
                        .width(if (index == pageCountIndex) 12.dp else 4.dp)
                        .height(4.dp)
                        .clip(if (index == pageCountIndex) RoundedCornerShape(2.dp) else CircleShape)
                        .background(
                            color = if (index == pageCountIndex) Color.White else Color.LightGray,
                            // shape = if (index == pageCountIndex) RoundedCornerShape(2.dp) else CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun DiscoveryScreenContentItems(data: SimpleListItemModel) {
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
fun PreviewDiscoveryScreen() {
    DiscoveryScreen(scrollState = rememberLazyListState())
}