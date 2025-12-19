package com.plataformas.airsense.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import com.plataformas.airsense.R
import com.plataformas.airsense.databinding.FragmentDashboardBinding


class DashboardFragment :Fragment(R.layout.fragment_dashboard) {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        observeViewModel()
        viewModel.loadAirQuality()
    }

    private fun observeViewModel() {
        viewModel.aqi.observe(viewLifecycleOwner) { aqi ->
            binding.tvAqiValue.text = aqi.toString()
            applyAqiStyle(aqi)
        }


        viewModel.pm25.observe(viewLifecycleOwner) { pm25 ->
            binding.tvPm25Value.text =
                pm25?.let { "$it µg/m³" } ?: "N/A"
        }

        viewModel.city.observe(viewLifecycleOwner) { city ->
            binding.tvCity.text = city
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility =
                if (loading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.tvError.text = error ?: ""
            binding.tvError.visibility =
                if (error != null) View.VISIBLE else View.GONE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyAqiStyle(aqi: Int) {
        val (textColor, bgColor, label) = when (aqi) {
            in 0..50 -> Triple(
                R.color.aqi_good,
                R.color.aqi_bg_good,
                "Bueno"
            )
            in 51..100 -> Triple(
                R.color.aqi_moderate,
                R.color.aqi_bg_moderate,
                "Moderado"
            )
            in 101..150 -> Triple(
                R.color.aqi_unhealthy_sg,
                R.color.aqi_bg_unhealthy_sg,
                "Dañino (grupos sensibles)"
            )
            in 151..200 -> Triple(
                R.color.aqi_unhealthy,
                R.color.aqi_bg_unhealthy,
                "Dañino"
            )
            in 201..300 -> Triple(
                R.color.aqi_very_unhealthy,
                R.color.aqi_bg_very_unhealthy,
                "Muy dañino"
            )
            else -> Triple(
                R.color.aqi_hazardous,
                R.color.aqi_bg_hazardous,
                "Peligroso"
            )
        }

        binding.tvAqiValue.setTextColor(requireContext().getColor(textColor))
        binding.tvAqiStatus.setTextColor(requireContext().getColor(textColor))
        binding.dashboardRoot.setBackgroundColor(requireContext().getColor(bgColor))
        binding.tvAqiStatus.text = label
    }



}