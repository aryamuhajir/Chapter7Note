package com.example.chapter7note.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chapter7note.R
import com.example.chapter7note.datastore.UserManager
import com.example.chapter7note.room.note.Note
import com.example.chapter7note.room.note.NoteDatabase
import com.example.chapter7note.viewmodel.ViewModelNote
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dialog_edit.view.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var noteDb : NoteDatabase? = null
    lateinit var userManager : UserManager

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
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())
        noteDb = NoteDatabase.getInstance(requireContext())
        val dataNote = arguments?.getParcelable<Note>("detailNote") as Note

        var judul = dataNote.judul
        var tanggal = dataNote.tanggal
        var isi = dataNote.isi

        detailJudul.text = judul
        detailTanggal.text = tanggal
        detailIsi.text = isi

        noteDb = NoteDatabase.getInstance(requireContext())


        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.detailB -> {
                    // Respond to navigation item 1 click

                    true
                }
                R.id.editB -> {
                    // Respond to navigation item 2 click
                    val customDialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null)
                    val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setView(customDialog)
                        .create()

                    customDialog.updateJudul.setText(judul)
                    customDialog.updateIsi.setText(isi)

                    customDialog.btnEdit.setOnClickListener {
                        val editJudul = customDialog.updateJudul.text.toString()
                        val editIsi = customDialog.updateIsi.text.toString()

                        GlobalScope.async {
                            noteDb?.noteDao()?.updateNote(Note(dataNote.id, dataNote.username,editJudul, dataNote.tanggal, editIsi))

                        }
                        dialog.dismiss()
                    }
                    dialog.show()
                    true
                }
                R.id.shareB -> {
                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, detailIsi.text.toString())
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share"))
                    true
                }
                R.id.hapusB ->{
                    val a = AlertDialog.Builder(requireContext())
                        .setTitle("Hapus Note")
                        .setMessage("Apakah yakin menghapus catatan?")
                        .setPositiveButton("YA") { dialog: DialogInterface, i: Int ->
                            GlobalScope.async {
                                noteDb?.noteDao()?.hapusNote(dataNote.id!!, dataNote.username)
                                view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                            }
                            dialog.dismiss()
                        }
                        .setNegativeButton("TIDAK") {dialog : DialogInterface, i : Int ->
                            dialog.dismiss()
                        }
                        .show()
                    true

                }
                else -> false
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
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}