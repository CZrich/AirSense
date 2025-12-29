package com.plataformas.airsense.ui.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plataformas.airsense.data.model.ForecastItem
import com.plataformas.airsense.databinding.ItemForecastBinding

class ForecastAdapter : ListAdapter<ForecastItem, ForecastAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastItem) {
            binding.tvDate.text = item.day
            binding.tvAvgAqi.text = "Promedio: ${item.avg} AQI"
            binding.tvRange.text = "Mín: ${item.min} / Máx: ${item.max}"

            // Lógica de color según escala AQI estándar
            val color = when {
                item.avg <= 50 -> "#4CAF50" // Bueno (Verde)
                item.avg <= 100 -> "#FFEB3B" // Moderado (Amarillo)
                item.avg <= 150 -> "#FF9800" // Dañino grupos sensibles (Naranja)
                else -> "#F44336" // Dañino (Rojo)
            }
            binding.indicatorColor.setBackgroundColor(Color.parseColor(color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
        override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem) =
            oldItem.day == newItem.day

        override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem) =
            oldItem == newItem
    }
}