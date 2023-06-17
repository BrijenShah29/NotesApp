package notesapi.by.brijen.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import notesapi.by.brijen.Utils.NetworkResult
import notesapi.by.brijen.api.NotesAPI
import notesapi.by.brijen.models.NoteRequest
import notesapi.by.brijen.models.NoteResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class NoteRepository @Inject constructor(private val notesAPI: NotesAPI)
{

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val noteLiveData : LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLiveData


    // To get the status of notes
    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
        get() = _statusLiveData


    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())

        // Creating notes Query
        val response = notesAPI.getNotes()

        // verifying response

        if(response.isSuccessful && response.body()!=null)
        {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))

        }
        else if (response.errorBody()!=null)
        {
            // Reading error from json " Message : "
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }
        else
        {
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }

    }

    suspend fun createNote(noteRequest: NoteRequest)
    {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponse(response, "Notes Created")

    }



    suspend fun deleteNote(noteId:String)
    {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleResponse(response,"Note Deleted")


    }
    suspend fun updateNote(noteId:String, noteRequest: NoteRequest)
    {

        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNote(noteId,noteRequest)
        handleResponse(response,"Note updated")


    }


    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }




}