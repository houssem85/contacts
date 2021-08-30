package com.streamwide.contacts.ui.contacts

import androidx.lifecycle.*
import com.streamwide.contacts.data.model.Contact
import com.streamwide.contacts.data.repository.ContactRepository
import com.streamwide.contacts.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val contactRepository: ContactRepository
): ViewModel() {

    val search = MutableLiveData("")

    private val _contacts = MutableLiveData<Resource<List<Contact>>>()

    private val _contactsFiltred = MutableLiveData<Resource<List<Contact>>>()
    val contactsFiltred : LiveData<Resource<List<Contact>>> = _contactsFiltred

    fun getContacts()
    {
        viewModelScope.launch(Dispatchers.IO){
            _contactsFiltred.postValue(Resource.loading())
            val resource = contactRepository.getContacts()
            _contactsFiltred.postValue(resource)
            _contacts.postValue(resource)
        }
    }

    fun filter()
    {
        _contacts.value?.data?.let {
           val search =  search.value
           val listFiltred =  it.filter {
               it.name.contains("$search", ignoreCase = true) ||
               it.phoneNumber.contains("$search", ignoreCase = true)
           }
           val resource = Resource.success(listFiltred)
           _contactsFiltred.postValue(resource)
        }
    }
}