package com.example.chapter7note.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chapter7note.room.user.User
import com.example.chapter7note.room.user.UserDatabase
import com.example.chapter7note.room.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUser (application: Application) : AndroidViewModel(application) {
//    lateinit var allFav : LiveData<List<Favorite>>

    lateinit var repository: UserRepository


    //
    lateinit var cekData : MutableLiveData<Int>
    lateinit var cekNama : MutableLiveData<String>

//
//    lateinit var liveDataFilm : MutableLiveData<List<DataFilmBaruItem>>

    init {
        val dao = UserDatabase.getInstance(application)?.userDao()
        repository = UserRepository(dao!!)
        cekData = MutableLiveData()
        cekNama = MutableLiveData()

    }

    fun registerLive(user : User) = viewModelScope.launch(Dispatchers.IO) {
        repository.registerDao(user)
    }

    fun loginLive(user : String, password : String) = viewModelScope.launch(Dispatchers.IO) {
        cekData.postValue(repository.cekLoginRepo(user, password))
    }
    fun namaLive(user: String) = viewModelScope.launch(Dispatchers.IO) {
        cekNama.postValue(repository.userNama(user))
    }

//    fun getLiveFilmObserver() : MutableLiveData<List<DataFilmBaruItem>> {
//        return liveDataFilm
//    }
//    fun insertFav(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(favorite)
//    }
//    fun deleteFav(id: String) = viewModelScope.launch(Dispatchers.IO) {
//        repository.delete(id)
//    }
//
//    fun checkFav(id: String) = viewModelScope.launch ( Dispatchers.IO )  {
//        cekData.postValue(repository.cek(id))
//    }
//
//




}