package com.sdk.remotejobs103.detail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.sdk.remotejobs103.R
import com.sdk.remotejobs103.database.JobDatabase
import com.sdk.remotejobs103.databinding.FragmentDetailBinding
import com.sdk.remotejobs103.model.Job
import com.sdk.remotejobs103.repository.JobRepository
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var job: Job? = null
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = arguments?.getParcelable("job")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val detailViewModelFactory = DetailViewModelFactory(JobRepository(null,JobDatabase(requireContext()).dao))
        viewModel = ViewModelProvider(this,detailViewModelFactory)[DetailViewModel::class.java]
        val webViewClient = WebViewClient()
        job?.let {
            binding.webView.apply {
                setWebViewClient(webViewClient)
                settings.javaScriptEnabled = true
                loadUrl(it.url ?: "")
            }
        }
        binding.fab.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(DetailIntent.OnSaveFavoriteJob(job!!))
                Snackbar.make(it, "Saved", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}