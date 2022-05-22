package com.example.chapter7note.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.chapter7note.R
import com.example.chapter7note.datastore.UserManager
import com.example.chapter7note.room.user.UserDatabase
import com.example.chapter7note.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var userManager: UserManager

    var userDb : UserDatabase? = null
    lateinit var viewModel : ViewModelUser
    lateinit var userNama : String

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())

        userDb = UserDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(ViewModelUser::class.java)

        txtBlmPunyaAkun.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnLogin.setOnClickListener {
            var user = editUsername.text.toString()
            var password = editPassword.text.toString()
            if (user.isNotBlank() && password.isNotBlank()){
                viewModel.cekData.observe(requireActivity(), Observer {
                    if (it != 0){
                        Toast.makeText(requireContext(), "Berhasil Login", Toast.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        loginDataStore(user, password)

                        viewModel.cekNama.observe(requireActivity()){
                            userNama = it
                        }
                        viewModel.namaLive(user)

                    }else{
                        Toast.makeText(requireContext(), "Username atau Password salah", Toast.LENGTH_LONG).show()
                    }
                })
                viewModel.loginLive(user, password)
            }else{
                Toast.makeText(requireContext(), "Username atau Password masih kosong!", Toast.LENGTH_LONG).show()

            }

        }
    }



    fun loginDataStore(username : String, password : String){
        GlobalScope.launch {
            userManager.login(username, password, userNama)
            userManager.setStatus("yes")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}