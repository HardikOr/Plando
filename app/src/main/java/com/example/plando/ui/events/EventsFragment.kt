package com.example.plando.ui.events

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plando.R
import com.example.plando.Utils
import com.example.plando.databinding.FragmentEventsBinding
import com.example.plando.databinding.RvEventsItemBinding
import com.example.plando.di.IconsUrlBaseString
import com.example.plando.models.Event
import com.example.plando.models.MyDate
import com.example.plando.ui.base.BaseFragment
import com.example.plando.ui.base.BaseSingleTypeRecyclerAdapter
import com.example.plando.ui.details.DetailsFragment
import com.example.plando.viewmodel.ViewModelFactory
import com.google.android.material.checkbox.MaterialCheckBox
import com.squareup.picasso.Picasso
import java.util.Calendar
import javax.inject.Inject

class EventsFragment : BaseFragment<FragmentEventsBinding>(R.layout.fragment_events) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventsViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var picasso: Picasso

    @Inject
    @IconsUrlBaseString
    lateinit var iconsBaseUrl: String

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Edit Event Result
        getNavigationResultOnceAndExec<Event>(DetailsFragment.eventResultKey) {
            viewModel.insertEvent(it)
        }

        val today = Calendar.getInstance()
        val day = MyDate(today.time)
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EventsAdapter(day)

            ItemTouchHelper(EventsTouchCallback(this@EventsFragment) {
                viewModel.deleteEvent(it)
            }).attachToRecyclerView(this)
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.events_weather_refresh -> {
                    viewModel.refreshWeather()
                }
            }
            true
        }

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(
                EventsFragmentDirections.actionListFragmentToDetailsFragment(Event())
            )
        }

        viewModel.events.observe(viewLifecycleOwner) {
            (binding.rvList.adapter as EventsAdapter).setData(it)
        }
    }

    inner class EventsAdapter(val today: MyDate) :
        BaseSingleTypeRecyclerAdapter<Event, RvEventsItemBinding>(RvEventsItemBinding::inflate) {

        override fun bind(binding: RvEventsItemBinding, item: Event, id: Int) {
            binding.apply {
                name.text = item.name
                description.setTextAndVisibility(item.description)

                date.text = item.date.toDMYString()

                icon.setViewVisibility(item.weatherIcon != null)
                item.weatherIcon?.let {
                    Utils.loadImageOrEmpty(icon, picasso, iconsBaseUrl, it)
                }

                val location = when {
                    item.locationAlias.isNotEmpty() -> item.locationAlias
                    item.locationString.isNotEmpty() -> item.locationString
                    else -> ""
                }
                locationString.setTextAndVisibility(
                    String.format(getString(R.string.location_template), location),
                    location.isNotEmpty()
                )

                // Plus today
                if (item.date.toSeconds() <= today.toSeconds() + 86400) {
                    attendedBlock.visibility = View.VISIBLE
                    rvCheckboxAttended.isChecked = item.hasAttended
                    rvCheckboxAttended.setOnClickListener {
                        it as MaterialCheckBox
                        item.hasAttended = it.isChecked
                        viewModel.insertEvent(item)
                        notifyItemChanged(id)
                    }

                    dataBlock.background = R.drawable.card_background.getDrawable()?.apply {
                        setTint(R.color.gray.getColor())
                    }

                    rvRoot.background.setTint(
                        if (item.hasAttended) {
                            R.color.good.getColor()
                        } else {
                            R.color.bad.getColor()
                        }
                    )
                } else {
                    attendedBlock.visibility = View.GONE
                }

                root.setOnClickListener {
                    findNavController().navigate(
                        EventsFragmentDirections.actionListFragmentToDetailsFragment(item)
                    )
                }
            }
        }
    }

    fun TextView.setTextAndVisibility(text: String, isVisible: Boolean? = null) {
        val isViewVisible = isVisible ?: text.isNotEmpty()

        this.text = text
        this.setViewVisibility(isViewVisible)
    }
}