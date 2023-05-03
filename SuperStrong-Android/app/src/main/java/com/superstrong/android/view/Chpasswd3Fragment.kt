package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.superstrong.android.R
import com.superstrong.android.databinding.FragmentChpasswd2Binding
import com.superstrong.android.databinding.FragmentChpasswd3Binding
import com.superstrong.android.viewmodel.ChpasswdVModel

/**
 * A simple [Fragment] subclass.
 * Use the [Chpasswd3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Chpasswd3Fragment : Fragment() {

    private var _binding: FragmentChpasswd3Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: ChpasswdVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChpasswd3Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chpassDone.setOnClickListener {
            vmodel.relogin()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}