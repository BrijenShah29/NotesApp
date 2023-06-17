package notesapi.by.brijen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import notesapi.by.brijen.Adapter.NoteAdapter
import notesapi.by.brijen.R
import notesapi.by.brijen.Utils.NetworkResult
import notesapi.by.brijen.databinding.FragmentMainBinding
import notesapi.by.brijen.models.NoteResponse
import notesapi.by.brijen.viewModel.NoteViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding ? = null
    private  val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var noteAdapter : NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)

        noteAdapter = NoteAdapter(::onNoteClicked)




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()

        noteViewModel.getNotes()

        binding.noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = noteAdapter
        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment)
        }
    }


    private fun bindObservers() {
        noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it) {
                is NetworkResult.Success -> {
                    noteAdapter.submitList(it.data)

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    private fun onNoteClicked(noteResponse: NoteResponse) {
        // Click event code for notes
        val bundle  = Bundle()
        bundle.putString("note", Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainFragment_to_notesFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}