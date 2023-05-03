package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.superstrong.android.databinding.FragmentSignup3Binding
import com.superstrong.android.viewmodel.SignupVModel


class Signup3Fragment : Fragment() {
    private var _binding: FragmentSignup3Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: SignupVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignup3Binding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cancelButton.setOnClickListener{
            vmodel.relogin()
        }
        binding.confirmButton.setOnClickListener{
            vmodel.sendCode(binding.emailEdit.text.toString())
        }

        vmodel.auth_fail.observe(viewLifecycleOwner, Observer {
            if(vmodel.auth_fail.value == 0) {
                val myToast = Toast.makeText(activity, "코드가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                myToast.show()
            }
            else if(vmodel.auth_fail.value == -1) {
                val myToast = Toast.makeText(activity, "서버와 통신 과정에서 문제가 발생했습니다..", Toast.LENGTH_SHORT)
                myToast.show()
            }
            else if(vmodel.auth_fail.value != 1)
                 vmodel.relogin()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}