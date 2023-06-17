package notesapi.by.brijen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import notesapi.by.brijen.R
import notesapi.by.brijen.Utils.HelperClass
import notesapi.by.brijen.Utils.NetworkResult
import notesapi.by.brijen.Utils.TokenManager
import notesapi.by.brijen.databinding.FragmentRegistrationBinding
import notesapi.by.brijen.models.UserRequest
import notesapi.by.brijen.viewModel.AuthViewModel
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val helperClass = HelperClass()

    @Inject
    lateinit var tokenManager: TokenManager

    private val authViewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)

        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnSignUp.setOnClickListener {

            val validationResult = validateUserInput()

            if(validationResult.first)
            {

                authViewModel.registerUser(getUserRequest())
            }
            else
            {
                binding.txtError.text = validationResult.second.toString()
            }
        }

        binding.btnLogin.setOnClickListener {
            // Redirect to Login Fragment

            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        bindObserver()
    }

    private fun getUserRequest(): UserRequest{
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val userName = binding.txtUsername.text.toString()
        return UserRequest(emailAddress,password,userName)

    }

    private fun validateUserInput(): Pair<Boolean, String> {

        val userRequest = getUserRequest()
        return helperClass.validateCredentials(userRequest.username,userRequest.email,userRequest.password,false)

    }



    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {

                    // Token Implementation
                     tokenManager.saveToken(it.data!!.token)

                    //Navigation to main Fragment
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)


                }
                is NetworkResult.Error -> {


                    binding.txtError.text = it.message


                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}