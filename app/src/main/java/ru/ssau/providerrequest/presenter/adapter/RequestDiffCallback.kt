package ru.ssau.providerrequest.presenter.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ssau.providerrequest.data.entity.Data

class RequestDiffCallback: DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}