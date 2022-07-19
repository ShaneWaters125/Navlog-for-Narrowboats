package solace.narrowboat.restapi
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("user_id") user_id:String,
                     @Field("username") username:String,
                     @Field("password") password:String,
                     @Field("email") email:String,
                     @Field("created_on") created_on:String
    ): Observable<String>

}