package com.plataformas.airsense.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel


import com.plataformas.airsense.R
import com.plataformas.airsense.databinding.FragmentDashboardBinding
import androidx.recyclerview.widget.GridLayoutManager


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by activityViewModels()
    private val pollutantAdapter = PollutantAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        setupRecycler()
        observeViewModel()

        // Verificamos si la ciudad está vacía Y si no se está cargando nada
        // Esto evita que "Arica" sobreescriba tu búsqueda de "Quito" o "Lima"
        if (viewModel.city.value == null && viewModel.isLoading.value != true) {
            viewModel.loadAirQuality("here")
        }
    }

    private fun setupRecycler() {
        binding.rvPollutants.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = pollutantAdapter
        }
    }

    private fun observeViewModel() {

        viewModel.aqi.observe(viewLifecycleOwner) { aqi ->
            binding.tvAqiValue.text = aqi.toString()
            binding.aqiProgressBar.progress = aqi


        }

        viewModel.aiPrediction.observe(viewLifecycleOwner) { message ->
            binding.tvAiMessage.text = message
        }

        viewModel.city.observe(viewLifecycleOwner) {
            binding.tvCity.text = it
        }

        viewModel.iaqi.observe(viewLifecycleOwner) { iaqi ->
            pollutantAdapter.submitList(
                listOf(
                    PollutantItem("PM2.5", iaqi.pm25, "µg/m³"),
                    PollutantItem("PM10", iaqi.pm10, "µg/m³"),
                    PollutantItem("O₃", iaqi.o3, "µg/m³"),
                    PollutantItem("NO₂", iaqi.no2, "µg/m³")
                )
            )
        }

   viewModel.isLoading.observe(viewLifecycleOwner) {
        binding.progressBar.visibility =
            if (it) View.VISIBLE else View.GONE
   }

 viewModel.error.observe(viewLifecycleOwner) {
         binding.tvError.visibility =
        if (it != null) View.VISIBLE else View.GONE
            binding.tvError.text = it ?: ""
         }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyAqiStyle(aqi: Int) {
        // (igual al que ya tienes, no lo repito)
    }
}
