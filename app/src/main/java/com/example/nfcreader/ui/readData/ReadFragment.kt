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
    //private lateinit var readViewModel: ReadViewModel
    private var _binding: FragmentReadBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        readViewModel =
//            ViewModelProvider(this).get(ReadViewModel::class.java)
        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val view = binding.root

        val textView: TextView = binding.textRead
        readViewModel.tag.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}