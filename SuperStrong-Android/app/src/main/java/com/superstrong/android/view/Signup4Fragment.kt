package com.superstrong.android.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.superstrong.android.databinding.FragmentSignup4Binding
import com.superstrong.android.viewmodel.SignupVModel


class Signup4Fragment : Fragment() {
    private var _binding: FragmentSignup4Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: SignupVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignup4Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcome.text="환영합니다. " + vmodel!!.userData!!.id + "님"
        val sharedPref = activity?.getSharedPreferences("strong", Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putString("id", vmodel.userData!!.id)
            putString("key", vmodel.userData!!.key)
            putString("pub_ad", vmodel.userData!!.pubAddress)
            putString("key_id", vmodel.userData!!.id)
            apply()
        }
        binding.confirmButton.setOnClickListener{
            vmodel.relogin()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}