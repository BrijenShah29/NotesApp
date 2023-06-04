package notesapi.by.brijen.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import notesapi.by.brijen.R
import notesapi.by.brijen.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null

    private val binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {


            // Redirecting to Login Fragment
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}