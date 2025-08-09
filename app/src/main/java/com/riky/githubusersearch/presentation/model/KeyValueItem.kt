package com.riky.githubusersearch.presentation.model

/**
 * Represents a simple key-value pair for displaying information in the UI.
 *
 * @property key The label or name describing the value.
 * @property value The associated value, which can be text or formatted content.
 */
data class KeyValueItem(
    val key: String,
    val value: String
)