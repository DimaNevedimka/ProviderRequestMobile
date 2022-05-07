package ru.ssau.providerrequest.presenter.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build.VERSION_CODES
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ssau.providerrequest.data.entity.Data
import ru.ssau.providerrequest.databinding.ItemRequestBinding
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RequestItemAdapter :
    ListAdapter<Data, RequestItemAdapter.RequestItemViewHolder>(RequestDiffCallback()) {

    var onClick: ((Data) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder =
        RequestItemViewHolder(
            ItemRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @RequiresApi(VERSION_CODES.O)
    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RequestItemViewHolder(private val binding: ItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            binding.apply {
                address.text = item.home.address
                date.text = item.home.updated_at
                detail.text = item.comment
                name.text = item.client.name + " " + item.client.surname + " " + item.client.patronymic
                tariff.text = item.tariff.name
                number.text = item.id.toString()
                status.text = item.status
                when (item.status) {
                    "CREATED" -> {
                        root.setOnClickListener {
                            onClick?.invoke(item)
                        }
                    }
                    "PROGRESS" -> {
                        root.setOnClickListener {
                            onClick?.invoke(item)
                        }
                    }
                    "CANCELED" -> {
                        root.setCardBackgroundColor(Color.RED)
                    }
                    "COMPLETED" -> {
                        root.setCardBackgroundColor(Color.GREEN)
                    }
                    "POSTPONED" -> {
                        root.setCardBackgroundColor(Color.BLUE)
                    }

                }

            }
        }
    }
}