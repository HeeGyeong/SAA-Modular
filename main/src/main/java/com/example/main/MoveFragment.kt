package com.example.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.main.databinding.FragmentMoveBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoveFragment : Fragment() {

    private var _binding: FragmentMoveBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoveBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            MainFragment.getInstance().moveSearchFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.addData()
        Log.d("viewModelData", "MoveFragment Data ? ${viewModel.checkData}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
