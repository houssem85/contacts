package com.streamwide.contacts.utils

import android.app.Activity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Activity.toast(message: String?) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

fun View.show(){
    this.visibility = VISIBLE
}

fun View.hide(){
    this.visibility = INVISIBLE
}

fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: LinearLayoutManager
): RecyclerView {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    return this
}