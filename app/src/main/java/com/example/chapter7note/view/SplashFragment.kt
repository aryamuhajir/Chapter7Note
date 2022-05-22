package com.example.chapter7note.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.example.chapter7note.R
import com.example.chapter7note.datastore.UserManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SplashFragment : Fragment() {
    lateinit var userManager: UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        userManager = UserManager(requireContext())
        Handler().postDelayed({
            userManager.userSTATUS.asLiveData().observe(requireActivity()) {
                if (it.equals("yes")) {
                    view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

                } else {
                    view.findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, 2000)
        return view
    }
}