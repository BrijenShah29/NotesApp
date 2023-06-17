package notesapi.by.brijen.Utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import notesapi.by.brijen.Utils.Constants.PREF_TOKEN_FILE
import notesapi.by.brijen.Utils.Constants.USER_TOKEN
import javax.inject.Inject


class TokenManager @Inject constructor(@ApplicationContext context: Context)
{
    private var prefs = context.getSharedPreferences(PREF_TOKEN_FILE,Context.MODE_PRIVATE)

    fun saveToken(token : String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun getToken() : String? {
        return prefs.getString(USER_TOKEN,null)
    }

}