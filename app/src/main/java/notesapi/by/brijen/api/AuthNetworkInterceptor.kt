package notesapi.by.brijen.api

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import notesapi.by.brijen.Utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthNetworkInterceptor @Inject constructor(@ApplicationContext context: Context) :
    Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response
    {

        val token = tokenManager.getToken()
        val request = chain.request().newBuilder()

        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }

//    private fun isInternetAvailable(): Boolean {
//
//        val connectionManager =
//            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        connectionManager.activeNetworkInfo.also {
//
//        }
//        }
    //  }
}