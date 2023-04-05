package com.sdk.remotejobs103.adapter

import com.sdk.remotejobs103.databinding.ItemLayoutBinding
import com.sdk.remotejobs103.model.Job
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class JobAdapter: ListAdapter<Job, JobAdapter.JobViewHolder>(DiffCallBack()) {
    lateinit var onClick: (Job) -> Unit
    lateinit var onLongClick: (Job) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JobViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.apply {
                Glide.with(ivCompanyLogo)
                    .load(job.company_logo)
                    .into(ivCompanyLogo)

                tvJobTitle.text = job.title
                tvDate.text = job.publication_date?.split("T")?.get(0).toString()
                tvJobType.text = job.job_type
                tvJobLocation.text = job.candidate_required_location
                tvCompanyName.text = job.company_name
            }
            itemView.setOnClickListener {
                onClick.invoke(job)
            }
            itemView.setOnLongClickListener {
                onLongClick(job)
                true
            }
        }
    }
}