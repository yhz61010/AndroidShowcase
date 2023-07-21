package com.leovp.androidshowcase.ui.tabs.home.iters

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:42
 */
enum class MarkType {
    Hot,
    Vip,
    Special
}

// sealed class MarkType(val label: String, val textColor: Color, private val borderColor: Color, val bgColor: Color) {
//     object Hot : MarkType(label, mark_hot_text_color, mark_hot_border, mark_hot_bg)
//     object Vip : MarkType("VIP", mark_vip_text_color, mark_vip_border, mark_vip_bg)
//     object Special : MarkType(label, mark_special_text_color, mark_special_border, mark_special_bg)
// }