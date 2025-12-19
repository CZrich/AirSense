package com.plataformas.airsense.ui.dashboard


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plataformas.airsense.databinding.ItemPollutantBinding

class PollutantAdapter :
    ListAdapter<PollutantItem, PollutantAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(
        val binding: ItemPollutantBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPollutantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        with(holder.binding) {
            tvPollutantLabel.text = item.label
            tvPollutantValue.text =
                item.value?.let { "${it} ${item.unit}" } ?: "N/A"
            tvPollutantStatus.text =
                item.value?.let { getStatus(item.label, it) } ?: "—"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PollutantItem>() {
        override fun areItemsTheSame(
            oldItem: PollutantItem,
            newItem: PollutantItem
        ) = oldItem.label == newItem.label

        override fun areContentsTheSame(
            oldItem: PollutantItem,
            newItem: PollutantItem
        ) = oldItem == newItem
    }
}

/* 🔧 helper simple (luego se mejora) */
private fun getStatus(label: String, value: Double): String {
    return when (label) {
        "PM2.5" -> if (value <= 50) "Bueno" else "Alto"
        "PM10" -> if (value <= 100) "Normal" else "Alto"
        else -> "—"
    }
}