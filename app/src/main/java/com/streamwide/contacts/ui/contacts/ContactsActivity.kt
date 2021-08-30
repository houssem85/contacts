package com.streamwide.contacts.ui.contacts

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.araujo.jordan.excuseme.ExcuseMe
import com.streamwide.contacts.R
import com.streamwide.contacts.databinding.ActivityContactsBinding
import com.streamwide.contacts.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityContactsBinding
    private val viewModel: ContactsViewModel by viewModels()
    lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        bindViewModel()
        initAdapter()
        setupRecyclerViewContacts()
        observeContacts()
        setupNavigation()
        lifecycleScope.launch {
            val isPermissionsGranted = isPermissionsGranted()
            val isSavedInstanceStateEmpty = savedInstanceState == null
            if(isPermissionsGranted && isSavedInstanceStateEmpty){
                viewModel.getContacts()
            }
        }
    }

    private fun setupBinding()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts)
        binding.lifecycleOwner = this
    }

    private fun bindViewModel()
    {
        binding.viewModel = viewModel
    }

    private fun initAdapter(){
        adapter = ContactsAdapter(this,{
            val contact = adapter.getItem(it)
            showDetailsContact(contact.contactId)
        }, {
            val contact = adapter.getItem(it)
            callContact(contact.phoneNumber)
        })
    }

    private fun showDetailsContact(contactId:Long){
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId.toString())
        intent.data = uri
        startActivity(intent)
    }

    private fun callContact(phoneNumber:String){
        // if contact has more then phone ex: 22635854 - 95474125
        val phone = phoneNumber.split(" - ").first()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phone}")
        startActivity(intent)
    }

    private fun setupRecyclerViewContacts(){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewContacts.init(adapter,layoutManager)
    }

    private suspend fun isPermissionsGranted(): Boolean {
        return ExcuseMe.couldYouGive(this).permissionFor(Manifest.permission.READ_CONTACTS)
    }

    private fun observeContacts()
    {
        viewModel.contactsFiltred.observe(this, {
            when(it.status){
                Status.LOADING ->{
                    binding.progressBar.show()
                }
                Status.ERROR ->{
                    binding.progressBar.hide()
                    toast(it.message)
                }
                Status.SUCCESS ->{
                    binding.progressBar.hide()
                    adapter.setData(it.data!!)
                }
                else -> { }
            }
        })
    }

    private fun setupNavigation()
    {
        binding.editTextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.filter()
                KeyBoardUtils.hideKeyboard(this)
            }
            false
        }
    }
}