package pe.edu.upc.rickagendaapp.data.remote

import retrofit2.Response
import pe.edu.upc.rickagendaapp.utils.Resource
import timber.log.Timber
import java.lang.Exception

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T>{
        try{
            val response = call()
            if(response.isSuccessful){
                val body = response.body()
                if(body != null)
                    return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        }catch (e: Exception){
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T>{
        Timber.d(message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}