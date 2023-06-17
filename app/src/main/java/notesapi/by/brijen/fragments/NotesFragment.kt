package notesapi.by.brijen.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import notesapi.by.brijen.R
import notesapi.by.brijen.Utils.NetworkResult
import notesapi.by.brijen.databinding.FragmentNotesBinding
import notesapi.by.brijen.models.NoteRequest
import notesapi.by.brijen.models.NoteResponse
import notesapi.by.brijen.viewModel.NoteViewModel


@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding:FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private var note : NoteResponse? = null

    private val noteViewModel by viewModels<NoteViewModel>()








    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(layoutInflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {


                }
                is NetworkResult.Loading -> {

                }
            }
        })



    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it!!._id)
            }

        }
        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val desc = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(desc,title)
            if(note == null)
            {
                noteViewModel.createNote(noteRequest)
            }
            else
            {
                noteViewModel.updateNote(note!!._id,noteRequest)
            }
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if(jsonNote!=null){
            note = Gson().fromJson(jsonNote,NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
                binding.addEditText.text = "Edit Note"
            }
        }else
        {
            binding.addEditText.text = "Add Note"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}