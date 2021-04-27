package com.example.nfcreader.ui.readData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.nfcreader.databinding.FragmentReadBinding

class ReadFragment : Fragment() {

    private val readViewModel: ReadViewModel by activityViewModels()

    private lateinit var _binding: FragmentReadBinding

    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val view = binding.root
        val textView: TextView = binding.textRead
        readViewModel.tag.observe(viewLifecycleOwner, Observer { textView.text = it })
        return view
    }
}