package notesapi.by.brijen.Utils

import android.text.TextUtils
import android.util.Patterns

class HelperClass {

    fun validateCredentials(username:String, emailAddress : String, password : String) : Pair<Boolean,String>
    {
        var result = Pair(true,"")

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(emailAddress)||TextUtils.isEmpty(password))
        {
            result = Pair(false,"Please Provide Credentials")

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){

            result = Pair(false,"Please Provide valid Email")

        }
        else if(password.length<=5){

            result = Pair(false,"Password Length Should be grater than 5")

        }
        return result
    }

}