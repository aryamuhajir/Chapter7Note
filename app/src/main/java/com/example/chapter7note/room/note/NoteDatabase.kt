package com.example.chapter7note.room.note

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chapter7note.room.user.User

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object{
        private var INSTANCE : NoteDatabase? = null
        fun getInstance(context : Context): NoteDatabase? {
            if (INSTANCE == null){
                synchronized(User::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,"Note.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        //       fun destroyInstance(){
        //          INSTANCE = null
        //      }
    }
}