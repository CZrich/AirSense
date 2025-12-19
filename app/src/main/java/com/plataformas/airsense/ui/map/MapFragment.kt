package com.plataformas.airsense.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.android.gms.maps.model.UrlTileProvider
import com.plataformas.airsense.R
import com.plataformas.airsense.ui.dashboard.DashboardViewModel
import java.net.URL

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val viewModel: DashboardViewModel by activityViewModels()
    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.map_container, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.uiSettings.isZoomControlsEnabled = false

        // Añadir Capa de Calidad del Aire (Tiles)
        val apiKey = "5f8188b565e2d7b257fda014d07c88586ef6ab87"
        val tileProvider = object : UrlTileProvider(256, 256) {
            override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
                return try {
                    URL("https://tiles.waqi.info/tiles/usepa-aqi/$zoom/$x/$y.png?token=$apiKey")
                } catch (e: Exception) { null }
            }
        }
        map.addTileOverlay(TileOverlayOptions().tileProvider(tileProvider))

        // Observar la ubicación para mover la cámara
        viewModel.location.observe(viewLifecycleOwner) { geo ->
            val pos = LatLng(geo[0], geo[1])
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 10f))
        }
    }
}