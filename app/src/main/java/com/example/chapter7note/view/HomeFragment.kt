package com.example.chapter7note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter7note.R
import com.example.chapter7note.adapter.RvAdapter
import com.example.chapter7note.datastore.UserManager
import com.example.chapter7note.room.note.NoteDatabase
import com.example.chapter7note.viewmodel.ViewModelNote
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var noteAdapter : RvAdapter

    lateinit var userManager : UserManager
    lateinit var viewModel : ViewModelNote
    lateinit var noteDatabase: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())

        userManager.userNAME.asLiveData().observe(requireActivity()){
            txtNama.text = it
            getAllNotes(it)
        }
        noteDatabase = NoteDatabase.getInstance(requireContext())!!




        btnProfile.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)        }

        btnAddNote.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    fun getAllNotes(username : String){
        GlobalScope.launch {
            val listData = noteDatabase.noteDao().getNotes(username)
            requireActivity().runOnUiThread {
                listData.let { it ->
                    if (it.size >= 1){
                        rv_item.layoutManager = LinearLayoutManager(requireContext())
                        noteAdapter = RvAdapter() {
                            val bund = Bundle()
                            bund.putParcelable("detailNote", it)
                            view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment, bund)
                        }
                        rv_item.adapter = noteAdapter

                        noteAdapter.setDataFilm(it!!)
                        noteAdapter.notifyDataSetChanged()
                        txtBelum.visibility = View.INVISIBLE
                    }else{
                        txtBelum.visibility = View.VISIBLE

                    }

                }
            }
        }
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}