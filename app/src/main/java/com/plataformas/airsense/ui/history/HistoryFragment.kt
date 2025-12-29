package com.plataformas.airsense.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plataformas.airsense.R
import com.plataformas.airsense.databinding.FragmentHistoryBinding
import com.plataformas.airsense.ui.dashboard.DashboardViewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    // Usamos el mismo ViewModel compartido para obtener los datos de la búsqueda
    private val viewModel: DashboardViewModel by activityViewModels()
    private val forecastAdapter = ForecastAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)

        setupRecyclerView()
        observeForecast()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = forecastAdapter
        }
    }

    private fun observeForecast() {
        // Observamos la lista de pronóstico (PM25 diario) que viene en el JSON
        viewModel.forecast.observe(viewLifecycleOwner) { forecastList ->
            if (forecastList.isNullOrEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                forecastAdapter.submitList(emptyList())
            } else {
                binding.tvEmptyState.visibility = View.GONE
                forecastAdapter.submitList(forecastList)
            }
        }

        // También podemos mostrar el nombre de la ciudad actual en el historial
        viewModel.city.observe(viewLifecycleOwner) { cityName ->
            binding.tvHistoryTitle.text = "Pronóstico para: $cityName"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}