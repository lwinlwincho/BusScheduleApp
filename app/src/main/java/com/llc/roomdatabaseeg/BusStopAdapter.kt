package com.llc.roomdatabaseeg

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.llc.roomdatabaseeg.database.BusScheduleEntity
import com.llc.roomdatabaseeg.databinding.BusStopItemBinding

class BusStopAdapter(private val onItemClickListener: (BusScheduleEntity) -> Unit
) : ListAdapter<BusScheduleEntity, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        return BusStopViewHolder(
            BusStopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BusStopViewHolder(
        private var binding: BusStopItemBinding,
        private val onItemClickListener: (BusScheduleEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(busScheduleEntity: BusScheduleEntity) {
            binding.stopNameTextView.text = busScheduleEntity.busName
            binding.arrivalTimeTextView.text = busScheduleEntity.arrivalTime
            /*binding.arrivalTimeTextView.text = SimpleDateFormat(
                "h:mm a").format(Date(busScheduleEntity.arrivalTime.toLong() * 1000)
            )*/
            binding.root.setOnClickListener {
                onItemClickListener.invoke(busScheduleEntity)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BusScheduleEntity>() {
            override fun areItemsTheSame(
                oldItem: BusScheduleEntity,
                newItem: BusScheduleEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BusScheduleEntity,
                newItem: BusScheduleEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
