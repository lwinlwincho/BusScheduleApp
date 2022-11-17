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
import com.llc.roomdatabaseeg.BusStopAdapter
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import com.llc.roomdatabaseeg.databinding.FragmentFullScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScheduleFragment : Fragment() {

    private var _binding: FragmentFullScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusScheduleViewModel by viewModels()

    // create database
   /* private val appDatabase by lazy {
        BusRoomDatabase.getDatabase(requireContext())
    }*/

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

        viewModel.getAllBusSchedule()
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