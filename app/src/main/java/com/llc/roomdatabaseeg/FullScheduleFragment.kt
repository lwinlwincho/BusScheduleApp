package com.llc.roomdatabaseeg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.llc.roomdatabaseeg.database.schedule.BusScheduleApplication
import com.llc.roomdatabaseeg.databinding.FragmentFullScheduleBinding
import com.llc.roomdatabaseeg.viewmodel.BusScheduleViewModel
import com.llc.roomdatabaseeg.viewmodel.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class FullScheduleFragment : Fragment() {

    private var _binding: FragmentFullScheduleBinding? = null
    val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val busStopAdapter = BusStopAdapter({
            val action =
                FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        })
        recyclerView.adapter=busStopAdapter

        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }
    }
}