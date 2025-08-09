package com.riky.githubusersearch.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riky.githubusersearch.databinding.ItemKeyValueBinding
import com.riky.githubusersearch.external.extension.visible
import com.riky.githubusersearch.presentation.model.KeyValueItem

/**
 * Adapter for displaying a list of [KeyValueItem] objects in a RecyclerView.
 *
 * This adapter uses [ListAdapter] with [DiffUtil] to efficiently update the list
 * when the data set changes. Each item is displayed in a two-column layout
 * (key on the left, value on the right).
 */
class KeyValueAdapter : ListAdapter<KeyValueItem, KeyValueAdapter.VH>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<KeyValueItem>() {
        override fun areItemsTheSame(o: KeyValueItem, n: KeyValueItem) = o.key == n.key
        override fun areContentsTheSame(o: KeyValueItem, n: KeyValueItem) = o == n
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemKeyValueBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position > 0)
    }

    /**
     * ViewHolder that binds a [KeyValueItem] to the layout.
     *
     * @param binding ViewBinding for the key-value row.
     */
    inner class VH(private val binding: ItemKeyValueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeyValueItem, isVisibleTopDivider: Boolean) {
            binding.dividerTop.visible(isVisibleTopDivider)
            binding.tvKey.text = item.key
            binding.tvValue.text = item.value
        }
    }
}
