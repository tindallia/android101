package dev.tindallia.registration

import android.content.Context
import android.widget.Toast
import dev.tindallia.registration.model.UserData
import dev.tindallia.registration.model.UserId
import dev.tindallia.registration.model.FbToken

object Data {
    private lateinit var userData: UserData
    private lateinit var userId: UserId
    private lateinit var fbToken: FbToken

    fun setData(username: String, gender: String, docId: String, dateOfBirth: String, context: Context): Boolean{
        try{
            userData = UserData(username,gender, docId, dateOfBirth)
        }catch(e: Exception){
            Toast.makeText(context,"Failed to store data!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun getData(): UserData{
        return userData
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

    fun getUserId(): UserId{
        return userId
    }

    fun setToken(idToken: String, context: Context): Boolean{
        try{
            fbToken = FbToken(idToken)
        }catch(e: Exception){
            Toast.makeText(context,"Failed to store data!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun getToken(): FbToken{
        return fbToken
    }
}