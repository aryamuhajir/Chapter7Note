package com.example.chapter7note.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chapter7note.R
import com.example.chapter7note.room.user.User
import com.example.chapter7note.room.user.UserDatabase
import com.example.chapter7note.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var userDb : UserDatabase? = null
    lateinit var viewModel : ViewModelUser

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDb = UserDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(ViewModelUser::class.java)


        btnDaftar.setOnClickListener {
            val username = editUsername1.text.toString()
            val password = editPassword1.text.toString()
            val password1 = editPassword2.text.toString()
            val email = editEmail1.text.toString()
            if (password.equals(password1)){
                GlobalScope.launch {
                    viewModel.registerLive(User(null, username, email, password))
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Berhasil membuat akun", Toast.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}