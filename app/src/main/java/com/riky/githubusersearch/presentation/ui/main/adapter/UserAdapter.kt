package com.riky.githubusersearch.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riky.githubusersearch.R
import com.riky.githubusersearch.databinding.ItemUserBinding
import com.riky.githubusersearch.domain.model.User

/**
 * Adapter for displaying a list of GitHub [User] items in a RecyclerView.
 *
 * @param onClick A lambda function to handle item click events.
 */
class UserAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(DIFF) {

    companion object {
        /**
         * DiffUtil implementation to optimize RecyclerView updates.
         */
        val DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    /**
     * ViewHolder class for individual user items.
     *
     * @param binding View binding for user item layout.
     * @param onClick Click listener to handle item clicks.
     */
    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onClick: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds user data to the views.
         *
         * @param user The [User] data to display.
         */
        fun onBind(user: User) {
            binding.txtUsername.text = user.username

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .circleCrop()
                .into(binding.imgAvatar)

            binding.root.setOnClickListener { onClick(user) }
        }
    }
}
