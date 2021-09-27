package dev.tindallia.registration

data class UserData(
    var username: String,
    var gender: String,
    var docId: String,
    var dateOfBirth: String
)

data class UserId(
    var userId: String
)