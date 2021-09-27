package dev.tindallia.registration.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(

    @Expose
    @SerializedName("username")
    val username: String,
    @Expose
    @SerializedName("gender")
    val gender: String,
    @Expose
    @SerializedName("documentId")
    val documentId: String,
    @Expose
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
)

data class ApiId(
    @Expose
    @SerializedName("name")
    val id: String
)