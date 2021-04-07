package com.example.nfcreader.ui.writeData

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.nfcreader.R
import com.example.nfcreader.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    private val writeViewModel: WriteViewModel by activityViewModels()

    private var _binding: FragmentWriteBinding? = null

    private val binding get() = _binding!!

    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        initUi()
        startObservers()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        binding.saveTagButtonWrapper.setOnClickListener  {
            showDialog()
            writeViewModel.messageToSave = binding.messageToSave.text!!.toString()
            writeViewModel.isWriteTagOptionOn = true
        }
    }

    private fun startObservers() {
        val textView: TextView = binding.textWrite

        writeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        writeViewModel.closeDialog.observe(viewLifecycleOwner, Observer {
            if(it && dialog?.isShowing == true){
                dialog?.dismiss()
            }
        })
    }

    private fun showDialog() {
        dialog = Dialog(context!!)
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.dialog_write_tag)
        dialog?.show()
    }
}