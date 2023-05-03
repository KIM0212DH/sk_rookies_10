package com.superstrong.android.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.superstrong.android.databinding.FragmentSignup2Binding
import com.superstrong.android.viewmodel.SignupVModel

/**
 * A simple [Fragment] subclass.
 * Use the [Signup2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Signup2Fragment : Fragment() {

    private var _binding: FragmentSignup2Binding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val vmodel: SignupVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignup2Binding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmodel.error_code.observe(viewLifecycleOwner, Observer { //입력 정보 오류
            val value = vmodel.error_code.value!!
            val message = arrayOf("", "이미 가입된 아이디가 있습니다.", "이미 가입된 유저입니다.",
                "이미 가입된 유저입니다.", "이미 가입된 유저입니다.",
                "인증이 끝나지 않은 유저입니다.", "비어있는 항목이 있습니다.",
                "비밀번호가 일치하지 않습니다.", "서버와 통신과정에서 문제가 발생했습니다.")
            if(value!=0)
                Toast.makeText(activity, message[value.toInt()], Toast.LENGTH_SHORT).show()
        })


        binding.confirmButton.setOnClickListener{ //확인 버튼 서버에 데이터 보내기
            vmodel.signupRequst(binding.ideditText.text.toString(), binding.passeditText.text.toString(),
                binding.passeditText2.text.toString(), binding.emailedit.text.toString(), binding.numedit.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}