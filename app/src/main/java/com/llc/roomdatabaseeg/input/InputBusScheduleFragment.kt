package com.llc.roomdatabaseeg.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.llc.roomdatabaseeg.database.schedule.AppDatabase
import com.llc.roomdatabaseeg.databinding.FragmentInputBusScheduleBinding

class InputBusScheduleFragment : Fragment() {
    private var _binding: FragmentInputBusScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InputBusScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBusScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())

        binding.btnOk.setOnClickListener {

            viewModel.addBusSchedule(
                appDatabase = appDatabase,
                busName = binding.editBusName.text.toString(),
                time = binding.editTime.text.toString()
            )
        }

        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is InputBusScheduleEvent.Success -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
                is InputBusScheduleEvent.Failure -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}