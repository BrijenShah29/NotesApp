package notesapi.by.brijen.api

import notesapi.by.brijen.models.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserRequest>

    @POST("/users/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserRequest>


}