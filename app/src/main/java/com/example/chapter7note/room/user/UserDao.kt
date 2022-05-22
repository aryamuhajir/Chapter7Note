package com.example.chapter7note.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun register(user : User) : Long

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username2 AND password = :password2) ")
    fun cekLogin(username2 : String, password2 : String) : Int

    @Query("SELECT name FROM User WHERE username = :username2")
    fun cekNama(username2: String) : String

//    @Query("SELECT * FROM Favorite")
//    fun getFav(): LiveData<List<Favorite>>
//    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE id = :id)")
//    fun cekFilm(id: String) :Int
//
//
//    @Query("DELETE FROM Favorite WHERE id = :id")
//    suspend fun deleteFavoriteFilmById(id: String)
//

}