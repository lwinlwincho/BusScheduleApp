package com.llc.roomdatabaseeg

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity
import com.llc.roomdatabaseeg.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.*

class BusStopAdapter(
    private val onItemClicked: (BusScheduleEntity) -> Unit
) : ListAdapter<BusScheduleEntity, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BusScheduleEntity>() {
            override fun areItemsTheSame(oldItem: BusScheduleEntity, newItem: BusScheduleEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BusScheduleEntity, newItem: BusScheduleEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BusStopViewHolder(
        private var binding: BusStopItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(busScheduleEntity: BusScheduleEntity) {
            binding.stopNameTextView.text = busScheduleEntity.busName
            binding.arrivalTimeTextView.text = SimpleDateFormat(
                "h:mm a").format(Date(busScheduleEntity.arrivalTime.toLong() * 1000)
            )
        }
    }
}
