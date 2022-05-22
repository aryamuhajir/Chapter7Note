package com.example.chapter7note.room.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface NoteDao {

    @Insert
    fun tambahNote(note : Note) : Long


    @Query("SELECT * FROM Note WHERE username = :username")
    fun getNotes(username : String) : List<Note>

    @Query("DELETE FROM Note WHERE id = :id AND username = :username ")
    suspend fun hapusNote(id: Int, username: String)


}