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
    add(KeyValueItem("Name", name.orDash()))
    add(KeyValueItem("Company", company.orDash()))
    add(KeyValueItem("Blog", blog.orDash()))
    add(KeyValueItem("Location", location.orDash()))
    add(KeyValueItem("Email", email.orDash()))
    add(KeyValueItem("Twitter", twitterUsername.orDash()))
    add(KeyValueItem("Public Repos", publicRepos?.toString().orDash()))
    add(KeyValueItem("Public Gists", publicGists?.toString().orDash()))
    add(KeyValueItem("Followers", followers?.toString().orDash()))
    add(KeyValueItem("Following", following?.toString().orDash()))
    add(KeyValueItem("GitHub URL", htmlUrl.orDash()))
    add(KeyValueItem("Created At", createdAt.orDash()))
    add(KeyValueItem("Updated At", updatedAt.orDash()))
}