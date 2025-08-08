package com.riky.githubusersearch.external.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object SystemHelper {

    /**
     * Hides the soft keyboard from the screen.
     *
     * @param activity The activity from which the keyboard should be hidden.
     */
    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus ?: View(activity)
        hideKeyboard(view)
    }

    /**
     * Hides the soft keyboard from the given view.
     *
     * @param view The currently focused view or any view within the window.
     */
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
