package com.riky.githubusersearch.presentation.ui.main

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.riky.githubusersearch.R
import com.riky.githubusersearch.databinding.ActivityMainBinding
import com.riky.githubusersearch.domain.model.User
import com.riky.githubusersearch.external.extension.visible
import com.riky.githubusersearch.external.helper.SystemHelper
import com.riky.githubusersearch.presentation.ui.main.adapter.UserAdapter
import com.riky.githubusersearch.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main screen for searching GitHub users.
 * This activity uses ViewModel to handle user search logic and display results in a RecyclerView.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets()
        setupWelcomeMessage()
        setupRecyclerView()
        setupSearchInput()
        observeViewModel()
    }

    /**
     * Displays a welcome message on the message page with GitHub icon and instructions.
     */
    private fun setupWelcomeMessage() {
        showMessagePage(
            icon = R.drawable.ic_github,
            title = getString(R.string.welcome_title),
            desc = getString(R.string.welcome_desc),
        )
    }

    /**
     * Applies system window insets (status bar, navigation bar) to the root layout.
     */
    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    /**
     * Sets up the RecyclerView and its adapter to display a list of GitHub users.
     */
    private fun setupRecyclerView() {
        adapter = UserAdapter { user ->
            Toast.makeText(this, "Selected: ${user.username}", Toast.LENGTH_SHORT).show()
        }

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    /**
     * Sets up search input field and button listeners for submitting queries.
     * Handles both button click and keyboard search action.
     */
    private fun setupSearchInput() = with(binding) {
        btnSearch.setOnClickListener {
            SystemHelper.hideKeyboard(this@MainActivity)
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty()) viewModel.search(query)
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                btnSearch.performClick()
                true
            } else false
        }
    }

    /**
     * Observes the ViewModel for user list and loading state changes.
     * Updates the UI accordingly.
     */
    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            onGetUsersResult(users)
        }

        viewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    /**
     * Handles the result from the user search.
     * Shows a message if no users are found, otherwise submits the user list to the adapter.
     *
     * @param users List of User returned from the search query.
     */
    private fun onGetUsersResult(users: List<User>) {
        if (users.isEmpty()) {
            showMessagePage(
                icon = R.drawable.ic_github,
                title = getString(R.string.no_users_found),
                desc = getString(R.string.no_users_found_desc),
            )
            return
        }
        adapter.submitList(users)
    }

    /**
     * Shows or hides the loading indicator.
     * Hides the message page when loading is active.
     *
     * @param isLoading True to show loading, false to hide.
     */
    private fun showLoading(isLoading: Boolean) {
        binding.loading.visible(isLoading)
        if (isLoading) {
            binding.itemMessage.root.visible(false)
        }
    }

    /**
     * Displays a message page with icon, title, and description.
     * Also hides the loading indicator if visible.
     *
     * @param icon Resource ID for the icon to display.
     * @param title The title message.
     * @param desc The description message.
     */
    private fun showMessagePage(@DrawableRes icon: Int, title: String, desc: String) {
        binding.itemMessage.icon.setImageResource(icon)
        binding.itemMessage.title.text = title
        binding.itemMessage.desc.text = desc
        binding.itemMessage.root.visible(true)
        binding.loading.visible(false)
    }
}