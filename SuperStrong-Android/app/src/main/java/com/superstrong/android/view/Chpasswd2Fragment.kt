package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.superstrong.android.databinding.FragmentChpasswd2Binding
import com.superstrong.android.viewmodel.ChpasswdVModel

class Chpasswd2Fragment : Fragment() {

    private var _binding: FragmentChpasswd2Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: ChpasswdVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChpasswd2Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmodel.samePass.observe(viewLifecycleOwner, Observer {
            if(it)
                binding.samepass.visibility=View.VISIBLE

            else
                binding.samepass.visibility=View.INVISIBLE

        } )
        binding.setpass.setOnClickListener {
            vmodel.newpass(binding.pass1.text.toString(), binding.pass2.text.toString())

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}