package com.example.chapter7note.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chapter7note.room.note.Note
import com.example.chapter7note.room.note.NoteDatabase
import com.example.chapter7note.room.note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelNote (application: Application) : AndroidViewModel(application) {

    lateinit var repository: NoteRepository


    //
    lateinit var cekData : MutableLiveData<List<Note>>
//
    lateinit var liveDataNote : MutableLiveData<List<Note>>

    init {
        val dao = NoteDatabase.getInstance(application)?.noteDao()
        repository = NoteRepository(dao!!)
        cekData = MutableLiveData()
        liveDataNote = MutableLiveData()

    }
    fun getLiveNoteObserver(): MutableLiveData<List<Note>> {
        return liveDataNote
    }

    fun insertNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.tambah(note)
    }
    fun getAll(username : String) = viewModelScope.launch (Dispatchers.IO){
        liveDataNote.postValue(repository.getAllNote(username))
    }

//    fun getPinjamLive(username : String) = viewModelScope.launch(Dispatchers.IO) {
//        cekData.postValue(repository.getPinjamRepo(username))
//    }
//    fun kembaliLive(id : Int, username: String) = viewModelScope.launch(Dispatchers.IO) {
//        repository.kembaliRepo(id, username)
//    }


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