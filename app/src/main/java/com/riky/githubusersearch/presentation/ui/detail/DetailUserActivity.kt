package com.riky.githubusersearch.presentation.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.riky.githubusersearch.R
import com.riky.githubusersearch.databinding.ActivityDetailUserBinding
import com.riky.githubusersearch.domain.model.UserDetail
import com.riky.githubusersearch.external.extension.visible
import com.riky.githubusersearch.presentation.model.KeyValueItem
import com.riky.githubusersearch.presentation.ui.detail.adapter.KeyValueAdapter
import com.riky.githubusersearch.presentation.ui.detail.mapper.toKeyValueItems
import com.riky.githubusersearch.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity that displays detailed information about a GitHub user.
 *
 * This screen shows the user's avatar, username, and other details
 * as a list of key-value pairs in a RecyclerView.
 */
@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var adapter: KeyValueAdapter
    private val viewModel: DetailViewModel by viewModels()

    private var username: String? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        username?.let { outState.putString(EXTRA_USERNAME, it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets()
        setupSaveInstance(savedInstanceState)
        setupActionListener()
        setupRecyclerView()
        observeViewModel()
        restoreOrLoadUserDetail()
    }

    /**
     * Restores the username from a previously saved instance state, if available.
     *
     * This method should be called early in the activity lifecycle (e.g., in [onCreate])
     * to ensure that the username value is preserved across configuration changes
     * such as screen rotations.
     *
     * @param savedInstanceState The [Bundle] containing the saved state from a previous
     *                           instance of this activity, or null if this is the first creation.
     */
    private fun setupSaveInstance(savedInstanceState: Bundle?) {
        username = savedInstanceState?.getString(EXTRA_USERNAME)
    }

    /**
     * Decides whether to restore the username from saved state or load it from intent.
     * If username is invalid, shows an error and closes the activity.
     */
    private fun restoreOrLoadUserDetail() {
        if (username.isNullOrEmpty()) {
            // First time load, get from intent
            val nameFromIntent = intent.getStringExtra(EXTRA_USERNAME).orEmpty()
            if (nameFromIntent.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.error_username_is_missing),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
                return
            }
            username = nameFromIntent
        }
        getUserDetail(username!!)
    }

    /**
     * Requests user details from the ViewModel for the given username.
     */
    private fun getUserDetail(name: String) {
        showLoading(true)
        viewModel.loadUser(name)
    }

    /**
     * Sets up UI click listeners, including the back button action.
     */
    private fun setupActionListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Applies system window insets (status bar, navigation bar) to the root layout.
     *
     * Ensures the content is properly padded so it does not overlap with system UI elements.
     */
    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Initializes the RecyclerView with a [KeyValueAdapter] and a linear layout manager.
     */
    private fun setupRecyclerView() {
        adapter = KeyValueAdapter()
        binding.rvKeyValue.layoutManager = LinearLayoutManager(this)
        binding.rvKeyValue.adapter = adapter
    }

    /**
     * Observes LiveData from [DetailViewModel] for user details and updates the UI when data changes.
     */
    private fun observeViewModel() {
        viewModel.userDetail.observe(this) { detail ->
            onGetUserDetailResult(detail)
        }
    }

    /**
     * Updates the UI with the provided [UserDetail] object.
     *
     * Loads the user's avatar, sets the username in the title, and populates the key-value list.
     *
     * @param detail The [UserDetail] data model containing the user's information.
     */
    private fun onGetUserDetailResult(detail: UserDetail) {
        // Load user avatar with fallback placeholder and error icons
        Glide.with(this)
            .load(detail.avatarUrl)
            .placeholder(R.drawable.ic_account_circle)
            .error(R.drawable.ic_account_circle)
            .circleCrop()
            .into(binding.imgAvatar)

        // Set username as title
        binding.tvTitleUser.text = detail.username

        // Map UserDetail to a list of KeyValueItems and display them
        val items: List<KeyValueItem> = detail.toKeyValueItems()
        adapter.submitList(items)

        // Hide loading indicator after data is displayed
        showLoading(false)
    }

    /**
     * Shows or hides the loading indicator.
     *
     * @param isLoading True to display the loading indicator, false to hide it.
     */
    private fun showLoading(isLoading: Boolean) {
        binding.loading.visible(isLoading)
    }

    companion object {
        /** Intent extra key used for passing the username to [DetailUserActivity]. */
        const val EXTRA_USERNAME = "extra_username"
    }
}
