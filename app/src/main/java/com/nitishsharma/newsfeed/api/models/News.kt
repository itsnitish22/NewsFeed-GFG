package com.nitishsharma.newsfeed.api.models

data class News(
    val items: ArrayList<Item>
)

data class Item(
    val title: String,
    val pubDate: String,
    val thumbnail: String,
    val enclosure: Enclosure
)

data class Enclosure(
    val link: String
)