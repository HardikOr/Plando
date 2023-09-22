package com.example.plando.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plando.R
import com.example.plando.databinding.FragmentMapBinding
import com.example.plando.models.Coord
import com.example.plando.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
    private val args: MapFragmentArgs by navArgs()
    private val viewModel by viewModels<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            binding.root.updatePadding(
                bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            insets
        }

        val mapFragment = binding.mapFragment.getFragment<SupportMapFragment>()
        mapFragment.getMapAsync(this)

        binding.buttonAddLocation.setOnClickListener {
            setNavigationResult(locationResultKey, viewModel.fetchedAddress.value)
            findNavController().navigateUp()
        }

        viewModel.fetchedAddress.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.footer.visibility = View.VISIBLE
                binding.locationAddress.text = it.first
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        args.coord?.apply {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lt, lg), 15F))
            Geocoder(requireContext()).getFromLocation(lt, lg, 1)?.apply {
                viewModel.setAddressAsync(
                    this.first().getAddressLine(0), Coord(lt, lg)
                )
            }
        }
        map.setOnMapClickListener {
            viewModel.fetchGeocoderData {
                // getFromLocation is blocking; No check for 33 API stuff
                Geocoder(requireContext()).getFromLocation(it.latitude, it.longitude, 1)?.apply {
                    viewModel.setAddressAsync(
                        this.first().getAddressLine(0), Coord(it.latitude, it.longitude)
                    )
                }
            }
        }
    }

    companion object {
        const val locationResultKey = "map_location"
    }
}