package com.example.internproject.presentation.recyclerview

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.R
import com.example.internproject.databinding.SignUpFieldBinding

class SignUpPageAdapter(): ListAdapter<SignUpPage, SignUpPageAdapter.SignUpPageViewHolder>(
    object: DiffUtil.ItemCallback<SignUpPage>() {
        override fun areItemsTheSame(oldItem: SignUpPage, newItem: SignUpPage): Boolean {
            return oldItem.guide == newItem.guide
        }
        override fun areContentsTheSame(oldItem: SignUpPage, newItem: SignUpPage): Boolean {
            return oldItem.guide == newItem.guide
        }
    }
) {

    inner class SignUpPageViewHolder(val binding: SignUpFieldBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SignUpPage) = with(binding) {
            guideTv.text = item.guide
            contentEt.hint = item.content

            item.onContentClick?.let { onClick ->
                contentEt.focusable = View.NOT_FOCUSABLE
                contentEt.setOnClickListener { onClick(contentEt) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignUpPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sign_up_field, parent, false)
        return SignUpPageViewHolder(SignUpFieldBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SignUpPageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}