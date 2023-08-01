package com.leovp.androidshowcase.ui.tabs.discovery.iters

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:42
 */
enum class MarkType {
    Hot,
    Vip,
    Special
}

// 超72%人播放>
// 超43%人收藏>
// 昨日万人播放>
// 十万评论>
// 十万红心>
// 百万红心>
// 小众佳作>
// ----------
// VIP
// Hi-Res
// SQ
// ----------
// 沉浸声
// 超清母带
// ----------
// Dolby

// sealed class MarkType(val label: String, val textColor: Color, private val borderColor: Color, val bgColor: Color) {
//     data object Hot : MarkType(label, mark_hot_text_color, mark_hot_border, mark_hot_bg)
//     data object Vip : MarkType("VIP", mark_vip_text_color, mark_vip_border, mark_vip_bg)
//     data object Special : MarkType(label, mark_special_text_color, mark_special_border, mark_special_bg)
// }