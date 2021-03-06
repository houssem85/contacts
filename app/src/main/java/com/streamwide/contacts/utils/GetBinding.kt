package com.streamwide.contacts.utils

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GetBinding<in V : RecyclerView.ViewHolder, out DB : ViewDataBinding>(
    private val itemView: View
) : ReadOnlyProperty<V, DB> {

    private var value: Any? = null

    override fun getValue(thisRef: V, property: KProperty<*>): DB {
        if (value == null) {
            value = DataBindingUtil.getBinding(itemView)
        }
        @Suppress("UNCHECKED_CAST")
        return value as DB
    }
}

fun <V : RecyclerView.ViewHolder, DB : ViewDataBinding> getBinding(itemView: View) =
    GetBinding<V, DB>(itemView)