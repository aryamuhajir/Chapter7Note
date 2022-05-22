package com.example.chapter7note.room.note

class NoteRepository(private val dao : NoteDao) {

    suspend fun tambah(note: Note) {
        dao.tambahNote(note)
    }

    suspend fun getAllNote(username : String) : List<Note>{
        return dao.getNotes(username)
    }
    suspend fun hapusNoteR(id : Int, username: String) {
        dao.hapusNote(id, username)
    }
}