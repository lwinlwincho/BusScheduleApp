package com.llc.roomdatabaseeg.bus_name_schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.llc.roomdatabaseeg.BusStopAdapter
import com.llc.roomdatabaseeg.bus_full_schedule_list.BusScheduleListEvent
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import com.llc.roomdatabaseeg.databinding.FragmentStopScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusNameFragment : Fragment() {

    private var _binding: FragmentStopScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusNameViewModel by viewModels()

    private val args: BusNameFragmentArgs by navArgs()

  /*  private val appDatabase by lazy {
       // BusRoomDatabase.getDatabase(requireContext())
    }*/

    private val busStopAdapter: BusStopAdapter by lazy {
        BusStopAdapter({})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=busStopAdapter
        }

        viewModel.getByBusName(args.stopName)
        viewModel.busNameEvent.observe(viewLifecycleOwner){
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
    }
}