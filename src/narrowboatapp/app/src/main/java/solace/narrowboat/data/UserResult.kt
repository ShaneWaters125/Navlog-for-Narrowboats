package solace.narrowboat.data

data class UserResult (

    var user_id: Int,
    var username: String,
    var password: String,
    var email: String,
    var created_on: String

)