package com.streamwide.contacts.ui.contacts

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.streamwide.contacts.data.model.Contact
import com.streamwide.contacts.databinding.ItemContactBinding
import java.io.File

class ContactsAdapter(
    private val context:Context,
    private val onItemClick:(Int) -> Unit,
    private val onBtnCallClick:(Int) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){

    private val items = ArrayList<Contact>()

    fun setData(list : List<Contact>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = ItemContactBinding.inflate(layoutInflater)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bindTo(model)
        holder.bindEvents(position,onItemClick,onBtnCallClick)
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View): BaseViewHolder<ItemContactBinding,Contact>(view){
        override fun bindTo(item: Contact) {
            dataBinding.lblName.text = item.name
            dataBinding.lblPhone.text = item.phoneNumber
            item.avatarPath?.let {
                val imgUri = Uri.parse(it)
                Glide.with(dataBinding.img.context).load(imgUri).into(dataBinding.img)
            }
        }
        fun bindEvents(position:Int,onItemClick:(Int) -> Unit,onBtnCallClick:(Int) -> Unit){
            dataBinding.btnCall.setOnClickListener {
                onBtnCallClick.invoke(position)
            }
            dataBinding.root.setOnClickListener {
                onItemClick.invoke(position)
            }
        }
    }
}