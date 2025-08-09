package com.riky.githubusersearch.presentation.nav

import android.content.Context
import android.content.Intent
import com.riky.githubusersearch.presentation.ui.detail.DetailUserActivity

object ActivityNavigation {

    fun navigateToDetail(context: Context, username: String) {
        val intent = Intent(context, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        }
        context.startActivity(intent)
    }
}