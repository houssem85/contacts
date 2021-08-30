package com.streamwide.contacts.utils

import javax.inject.Inject

class DefaultErrorFactory @Inject constructor() : ErrorFactory {
    override fun createErrorMessage(e: Exception) = "Error: ${e.message}"
}