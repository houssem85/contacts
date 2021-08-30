package com.streamwide.contacts.utils

interface ErrorFactory {
    fun createErrorMessage(e: Exception): String
}