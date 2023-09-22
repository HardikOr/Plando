package com.example.plando.ui.details

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.plando.R
import com.example.plando.databinding.FragmentDetailsBinding
import com.example.plando.models.Coord
import com.example.plando.models.Event
import com.example.plando.models.MyDate
import com.example.plando.ui.base.BaseFragment
import com.example.plando.ui.map.MapFragment
import com.example.plando.viewmodel.ViewModelFactory
import java.util.Calendar
import javax.inject.Inject

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DetailsViewModel by navGraphViewModels(R.id.detail_nav_graph) {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun setBindingData() {
        binding.fields.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial data
        if (viewModel.editEvent == Event()) {
            viewModel.setEvent(args.editableEvent.copy())
        }

        // Map location result
        getNavigationResultOnceAndExec<Pair<String, Coord>>(MapFragment.locationResultKey) {
            viewModel.setLocation(it.first, it.second)
        }

        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
            { _, y, m, d ->
                // Bruv
                val date = MyDate(y - 1900, m, d)
                viewModel.setDate(date)
//                Log.d("TEST", "${date}\n${date.toDMYString()}\n")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.fields.buttonDatePicker.setOnClickListener {
            dialog.create()
            dialog.show()
        }

        binding.fields.buttonLocationPicker.setOnClickListener {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToMapFragment(
                    viewModel.editEvent.location
                )
            )
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.events_save) {
//                Log.d("TEST", "Final edit: ${viewModel.editEvent}")
                if (viewModel.editEvent.name != "" && viewModel.editDate.value!!.date != null) {
                    setNavigationResult(eventResultKey, viewModel.editEvent)
                    findNavController().navigateUp()
                }
            }
            true
        }
    }

    companion object {
        const val eventResultKey = "edit_event"
    }
}