package com.sdk.remotejobs103.all

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdk.remotejobs103.R
import com.sdk.remotejobs103.adapter.JobAdapter
import com.sdk.remotejobs103.databinding.FragmentAllBinding
import kotlinx.coroutines.launch

class AllFragment : Fragment() {

    private lateinit var viewModel: AllViewModel
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val jobAdapter by lazy { JobAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AllViewModel::class.java]
        lifecycleScope.launch {
            viewModel.userIntent.send(AllIntent.OnGetAllJobs)
        }
        binding.rv.apply {
            adapter = jobAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.allState.observe(viewLifecycleOwner) {
            when (it) {
                is AllState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.rv.isVisible = false
                }
                is AllState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is AllState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.rv.isVisible = true
                    jobAdapter.submitList(it.jobs)
                }
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(AllIntent.OnGetAllJobs)
            }
        }
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}