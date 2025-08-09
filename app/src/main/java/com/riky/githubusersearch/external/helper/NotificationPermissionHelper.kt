package com.riky.githubusersearch.external.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.riky.githubusersearch.external.helper.NotificationPermissionHelper.isNotificationPermissionGranted
import com.riky.githubusersearch.external.helper.NotificationPermissionHelper.requestNotificationPermissionIfNeeded

/**
 * Utility helper for requesting and checking notification permissions (POST_NOTIFICATIONS)
 * on Android 13 (API 33) and above.
 *
 * Usage:
 * - Call [requestNotificationPermissionIfNeeded] from your Activity or Fragment to prompt the user for notification permission when required.
 * - Use [isNotificationPermissionGranted] to check if the app already has the permission.
 */
object NotificationPermissionHelper {

    /**
     * Requests the POST_NOTIFICATIONS permission if needed (Android 13+).
     *
     * @param activity The Activity context used to check and request the permission.
     * @param launcher The ActivityResultLauncher used to launch the permission request.
     *
     * Usage example (in Activity/Fragment):
     * ```
     * private val notificationPermissionLauncher = registerForActivityResult(
     *     ActivityResultContracts.RequestPermission()
     * ) { isGranted ->
     *     // Handle the permission result if needed
     * }
     *
     * NotificationPermissionHelper.requestNotificationPermissionIfNeeded(
     *     this,
     *     notificationPermissionLauncher
     * )
     * ```
     */
    fun requestNotificationPermissionIfNeeded(
        activity: Activity,
        launcher: ActivityResultLauncher<String>
    ) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Checks whether the POST_NOTIFICATIONS permission has been granted.
     *
     * @param activity The Activity context used to check the permission.
     * @return true if the permission is granted, or if the device runs below Android 13 (API 33); false otherwise.
     */
    fun isNotificationPermissionGranted(activity: Activity): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}