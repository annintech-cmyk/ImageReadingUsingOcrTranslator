package com.post.postappliedml

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform