package com.riky.githubusersearch.presentation.ui.detail.mapper

import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.presentation.model.KeyValueItem

/**
 * Returns a dash ("-") if the string is null or blank.
 */
private fun String?.orDash() = if (this.isNullOrBlank()) "-" else this

/**
 * Maps a [UserDetail] domain model to a list of [KeyValueItem]s for display in the UI.
 *
 * @receiver The [UserDetail] object to map from.
 * @return A list of [KeyValueItem] containing user information such as ID, username, and bio.
 */
fun UserDetail.toKeyValueItems(): List<KeyValueItem> = buildList {
    add(KeyValueItem("ID", id.toString()))
    add(KeyValueItem("Username", username.orDash()))
    add(KeyValueItem("Bio", bio.orDash()))
}