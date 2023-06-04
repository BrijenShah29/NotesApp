package notesapi.by.brijen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import notesapi.by.brijen.Utils.NetworkResult
import notesapi.by.brijen.models.User
import notesapi.by.brijen.models.UserRequest
import notesapi.by.brijen.models.UserResponse
import notesapi.by.brijen.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(private val userRepository: UserRepository): ViewModel()
{
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest)
    {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }

    }

    fun loginUser(userRequest: UserRequest)
    {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }

    }

}
