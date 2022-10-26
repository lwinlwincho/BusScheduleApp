package com.llc.roomdatabaseeg

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
import com.llc.roomdatabaseeg.database.schedule.AppDatabase
import com.llc.roomdatabaseeg.databinding.FragmentFullScheduleBinding
import com.llc.roomdatabaseeg.viewmodel.BusScheduleListEvent
import com.llc.roomdatabaseeg.viewmodel.BusScheduleViewModel

class FullScheduleFragment : Fragment() {

    private var _binding: FragmentFullScheduleBinding? = null
    val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BusScheduleViewModel by viewModels()

    // create database
    private val appDatabase by lazy {
        AppDatabase.getDatabase(requireContext())
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

        val busStopAdapter = BusStopAdapter {
            val action =
                FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.busName
                )
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = busStopAdapter

        viewModel.getAllBusSchedule(appDatabase)
        viewModel.busScheduleListEvent.observe(viewLifecycleOwner) {
            when (it) {
                is BusScheduleListEvent.Loading -> {}
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