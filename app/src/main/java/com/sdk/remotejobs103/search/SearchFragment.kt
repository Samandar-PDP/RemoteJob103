package com.sdk.remotejobs103.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdk.remotejobs103.R
import com.sdk.remotejobs103.adapter.JobAdapter
import com.sdk.remotejobs103.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private val jobAdapter by lazy { JobAdapter() }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.rv.apply {
            adapter = jobAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.editText.addTextChangedListener {
            it?.let { query ->
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(700L)
                    viewModel.intent.send(SearchIntent.OnSearch(query.toString()))
                }
            }
        }
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner) {
                when(it) {
                    is SearchState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.rv.isVisible = false
                    }
                    is SearchState.Error -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    }
                    is SearchState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.rv.isVisible = true
                        jobAdapter.submitList(it.list)
                    }
                }
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