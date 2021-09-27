package dev.tindallia.registration

import android.content.Context
import android.widget.Toast

object Data {
    private lateinit var userData: UserData
    private lateinit var userId: UserId

    fun setData(username: String, gender: String, docId: String, dateOfBirth: String, context: Context): Boolean{
        try{
            userData = UserData(username,gender, docId, dateOfBirth)
        }catch(e: Exception){
            Toast.makeText(context,"Failed to store data!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun setUserId(id: String, context: Context): Boolean{
        try{
            userId = UserId(id)
        }catch(e: Exception){
            Toast.makeText(context,"Failed to store data!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun getData(): UserData{
        return userData
    }

    fun getUserId(): UserId{
        return userId
    }
}