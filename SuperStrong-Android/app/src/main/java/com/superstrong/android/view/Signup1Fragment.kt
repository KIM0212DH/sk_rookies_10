package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.superstrong.android.databinding.FragmentSignup1Binding
import com.superstrong.android.viewmodel.SignupVModel


class Signup1Fragment : Fragment() {

    private var _binding: FragmentSignup1Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: SignupVModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignup1Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmodel.unchecked.observe(viewLifecycleOwner, Observer {//개인정보 수집 동의
            if(it)
                binding.unchecked.visibility = View.VISIBLE
            else
                binding.unchecked.visibility = View.INVISIBLE
        })
        binding.termsCheckbox.setOnClickListener{
            if(binding.termsCheckbox.isChecked)
            {
                binding.privacyCheckbox.isChecked=true
                binding.metamaskCheckbox.isChecked=true
            }
        }
        binding.cancelButton.setOnClickListener{ //가입 취소
            vmodel.relogin()
        }
        binding.confirmButton.setOnClickListener{ //가입 진행
            vmodel.all_checked(binding.privacyCheckbox.isChecked, binding.metamaskCheckbox.isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}