package com.post.postappliedml.translate

interface OcrTranslator {
    suspend fun recognizeText(imageData: ByteArray): String
    suspend fun translateText(text: String): String
}
