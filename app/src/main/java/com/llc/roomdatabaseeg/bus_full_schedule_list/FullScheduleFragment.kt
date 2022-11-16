package com.llc.roomdatabaseeg.bus_full_schedule_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.llc.roomdatabaseeg.BusStopAdapter
import com.llc.roomdatabaseeg.database.AppDatabase
import com.llc.roomdatabaseeg.databinding.FragmentFullScheduleBinding

class FullScheduleFragment : Fragment() {

    private var _binding: FragmentFullScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusScheduleViewModel by viewModels()

    // create database
    private val appDatabase by lazy {
        AppDatabase.getDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val busStopAdapter = BusStopAdapter {
            val action =
                FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.busName
                )
            view.findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=busStopAdapter

        }

        viewModel.getAllBusSchedule(appDatabase)
        viewModel.busScheduleListEvent.observe(viewLifecycleOwner) {
            when (it) {
                is BusScheduleListEvent.Success -> {
                    busStopAdapter.submitList(it.busList)
                }
                is BusScheduleListEvent.Failure -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action =
                FullScheduleFragmentDirections.actionFullScheduleFragmentToInputBusScheduleFragment()
            findNavController().navigate(action)
        }
    }
}