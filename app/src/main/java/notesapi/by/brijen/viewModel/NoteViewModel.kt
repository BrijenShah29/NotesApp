package notesapi.by.brijen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import notesapi.by.brijen.models.NoteRequest
import notesapi.by.brijen.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository): ViewModel()
{

    val notesLiveData get() = noteRepository.noteLiveData

    val statusLiveData get() = noteRepository.statusLiveData


    // GETTING NOTE FROM REPOSITORY
    fun getNotes(){
        viewModelScope.launch {
            noteRepository.getNotes()
        }
    }

    // CREATING NOTE FROM REPOSITORY
    fun createNote(noteRequest: NoteRequest)
    {
        viewModelScope.launch {
            noteRepository.createNote(noteRequest)

        }

    }


    // UPDATING NOTE FROM REPOSITORY
    fun updateNote(noteId : String, noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.updateNote(noteId,noteRequest)
        }
    }


    // DELETING NOTE FROM REPOSITORY
    fun deleteNote(noteId: String){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }

}