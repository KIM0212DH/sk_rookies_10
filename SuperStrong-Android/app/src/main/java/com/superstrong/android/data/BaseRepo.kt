package com.superstrong.android.data

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


sealed class Resource<T>(
    val data :T? = null,
    val message: String? = null){

    // We'll wrap our data in this 'Success'
    // class in case of success response from api
    class Success<T>(data: T) : Resource<T>(data = data)

    // We'll pass error message wrapped in this 'Error'
    // class to the UI in case of failure response
    class Error<T>( errorMessage: String) : Resource<T>( message = errorMessage)

    // We'll just pass object of this Loading
    // class, just before making an api call

    class Loading<T> : Resource<T>()
}

abstract class BaseRepo() {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    Log.d("safeApicall", "not successful")
                    Resource.Error( errorMessage =  "Something went wrong")
                }

            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Log.d("safeApicall", "something wrong")
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }
}

data class E2eReq(
    @SerializedName("e2e_req")
    val result :String
)

data class E2eReq2(
    @SerializedName("e2e_req")
    val result :String,

    @SerializedName("token")
    val token :String
)

data class E2eRes(
    @SerializedName("e2e_res")
    val encData :String
)