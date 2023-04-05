package com.sdk.remotejobs103.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdk.remotejobs103.R
import com.sdk.remotejobs103.adapter.JobAdapter
import com.sdk.remotejobs103.database.JobDatabase
import com.sdk.remotejobs103.repository.JobRepository

class FavoriteFragment : Fragment() {

    private val jobAdapter by lazy { JobAdapter() }

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory =
            FavoriteViewModelFactory(JobRepository(null, JobDatabase(requireContext()).dao))
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        val rv: RecyclerView = view.findViewById(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jobAdapter
        }
        viewModel.jobList.observe(viewLifecycleOwner) {
            jobAdapter.submitList(it)
        }
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
        jobAdapter.onLongClick = {
            viewModel.deleteJob(it)
        }
    }
}