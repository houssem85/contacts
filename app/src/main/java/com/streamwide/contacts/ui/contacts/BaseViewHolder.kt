package com.streamwide.contacts.ui.contacts

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.streamwide.contacts.utils.getBinding

abstract class BaseViewHolder<DB : ViewDataBinding, T : Any>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    protected val dataBinding: DB by getBinding(itemView)

    abstract fun bindTo(item: T)
}