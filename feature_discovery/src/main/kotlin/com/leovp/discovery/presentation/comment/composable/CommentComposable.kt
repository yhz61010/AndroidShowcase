package com.leovp.discovery.presentation.comment.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.leovp.discovery.domain.model.SongModel
import com.leovp.feature.base.ui.Screen

/**
 * Author: Michael Leo
 * Date: 2026/3/30 08:39
 */

private val imgSize = 40.dp
private val imgPadding = 8.dp

@Composable
fun SongInfoHeader(songInfo: Screen.Comment) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = "https://picsum.photos/40/40?random=0",
            contentDescription = "Avatar",
            modifier =
                Modifier
                    .size(imgSize)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(imgPadding))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = songInfo.songName,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = " - ${songInfo.artist}",
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Composable
fun FilterTabs() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                // .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "评论 (106486)",
            style = MaterialTheme.typography.titleMedium,
        )
        FilterChip(
            onClick = { },
            label = { Text("推荐") },
            selected = true,
        )
        FilterChip(
            onClick = { },
            label = { Text("最热") },
            selected = false,
        )
        FilterChip(
            onClick = { },
            label = { Text("最新") },
            selected = false,
        )
    }
}

@Composable
fun CommentItem(
    comment: SongModel.Comment,
    onLike: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 22.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                UserTitleRow(comment, onLike)
                Spacer(modifier = Modifier.height(4.dp))
                // Comment text
                Text(
                    modifier = Modifier.padding(start = imgSize + imgPadding),
                    text = comment.comment,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 20.sp,
                        ),
                    // color = Color.Black,
                )
                // Show more
                if (comment.replyCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowMoreReplies(comment)
                }
            }
        }
    }
}

@Composable
private fun UserTitleRow(
    comment: SongModel.Comment,
    onLike: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar
        AsyncImage(
            model = comment.userAvatar,
            contentDescription = "User Avatar",
            modifier =
                Modifier
                    .size(imgSize)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(imgPadding))
        // User information
        Column {
            UserNameAndMark(comment)
            Spacer(Modifier.height(2.dp))
            DateTimeLocation(comment)
        }
        Spacer(modifier = Modifier.weight(1f))
        // Action Row (Like button)
        LikeActionRow(comment, onLike)
    }
}

@Composable
private fun DateTimeLocation(comment: SongModel.Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = comment.timeAgo,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = comment.location,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LikeActionRow(
    comment: SongModel.Comment,
    onLike: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = comment.likeCount.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = onLike,
            modifier = Modifier.size(20.dp),
        ) {
            Icon(
                Icons.Default.ThumbUp,
                contentDescription = "Like",
                tint = if (comment.isLiked) Color.Red else Color.Gray,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun ShowMoreReplies(comment: SongModel.Comment) {
    Row(
        modifier =
            Modifier
                .padding(start = imgSize + imgPadding)
                .clickable(onClick = {}),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier.width(32.dp),
            thickness = 1.dp,
            color = Color.LightGray,
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "展开${comment.replyCount}条回复",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF2A7E87),
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "ArrowForward",
            tint = Color(0xFF2A7E87),
        )
    }
}

@Composable
private fun UserNameAndMark(comment: SongModel.Comment) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = comment.userName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (comment.isVip) {
                Spacer(modifier = Modifier.width(4.dp))
                Card(
                    shape = RoundedCornerShape(2.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = Color.Red,
                        ),
                    modifier = Modifier.height(16.dp),
                ) {
                    Text(
                        text = "VIP",
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier =
                            Modifier.padding(
                                horizontal = 4.dp,
                                vertical = 1.dp,
                            ),
                    )
                }
            }
        }
    }
}
