package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.superstrong.android.databinding.FragmentChpasswd1Binding
import com.superstrong.android.viewmodel.ChpasswdVModel

class Chpasswd1Fragment : Fragment() {

    private var _binding: FragmentChpasswd1Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: ChpasswdVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChpasswd1Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmodel.wrongPasswd.observe(viewLifecycleOwner, Observer {
            if(it==1)
                binding.wrongText.visibility=View.VISIBLE

            else
                binding.wrongText.visibility=View.INVISIBLE

        } )
        binding.passCheck.setOnClickListener {
            vmodel.checkCurrentPassword(binding.currentPass.text.toString())

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

