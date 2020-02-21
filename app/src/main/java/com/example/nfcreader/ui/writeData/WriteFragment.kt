package com.example.nfcreader.ui.writeData

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nfcreader.R
import com.example.nfcreader.databinding.FragmentWriteBinding
import com.example.nfcreader.ui.readData.ReadViewModel

class WriteFragment : Fragment() {

    private val writeViewModel: WriteViewModel by activityViewModels()
    //private lateinit var writeViewModel: WriteViewModel
    private var _binding: FragmentWriteBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        writeViewModel =
//            ViewModelProvider(this).get(WriteViewModel::class.java)
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val view = binding.root
        val textView: TextView = binding.textWrite
        binding.saveTagButtonWrapper.setOnClickListener()  {
            showDialog()
            writeViewModel.messageToSave = binding.messageToSave.text!!.toString()
            writeViewModel.isWriteTagOptionOn = true
        }
        writeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        writeViewModel.closeDialog.observe(viewLifecycleOwner, Observer {
            if(it && dialog?.isShowing != null){
                dialog!!.dismiss()
            }
        })
        return view
    }
    private fun showDialog() {
        dialog = Dialog(activity)
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.dialog_write_tag)
        dialog!!.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}