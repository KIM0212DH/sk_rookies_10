package com.superstrong.android.view;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.superstrong.android.databinding.FragmentLogBinding
import com.superstrong.android.viewmodel.LogAdapter
import com.superstrong.android.viewmodel.LogVModel
import org.json.JSONArray

public class LogFragment : Fragment() {
    private var _binding: FragmentLogBinding? = null
    private val binding get() = _binding!!
    private val vmodel: LogVModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logList.adapter = LogAdapter(requireContext(), vmodel.historyData)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
