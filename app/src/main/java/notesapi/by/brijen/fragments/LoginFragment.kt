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
import notesapi.by.brijen.databinding.FragmentLoginBinding
import notesapi.by.brijen.models.UserRequest
import notesapi.by.brijen.viewModel.AuthViewModel
import javax.inject.Inject

@AndroidEntryPoint
class  LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null

    private val binding = _binding!!

    private val helperClass = HelperClass()

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val validateResult = validateUserInput()

            if(validateResult.first){
                authViewModel.loginUser(getUserRequest())
            }
            else
            {
                binding.txtError.text = validateResult.second
            }


            // Redirecting to Login Fragment
            //findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

        }
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()


    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false

            when(it)
            {
                is NetworkResult.Success->{

                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }
                is NetworkResult.Error ->{
                    binding.txtError.text = it.message
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(emailAddress,password,"")

    }

    private fun validateUserInput(): Pair<Boolean,String> {

        val userRequest = getUserRequest()
        return helperClass.validateCredentials(userRequest.username,userRequest.email,userRequest.password,true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}