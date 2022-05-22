package com.example.chapter7note.room.user

class UserRepository (private val dao : UserDao) {

    suspend fun registerDao(user : User){
        dao.register(user)
    }
    suspend fun cekLoginRepo(user : String, password : String) : Int{
        return dao.cekLogin(user, password)
    }
    suspend fun userNama(user: String) : String{
        return dao.cekNama(user)
    }
}