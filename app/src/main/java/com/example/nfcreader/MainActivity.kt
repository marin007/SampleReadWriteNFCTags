package com.example.nfcreader

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nfcreader.databinding.ActivityMainBinding
import com.example.nfcreader.ui.readData.ReadViewModel
import com.example.nfcreader.ui.writeData.WriteViewModel
import com.example.nfcreader.utils.NFCUtilManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException


class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var lifeCycleRegistry : LifecycleRegistry
    private var mPendingIntent: PendingIntent? = null
    private lateinit var readViewModel: ReadViewModel
    private lateinit var writeViewModel: WriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        readViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)
        writeViewModel =
            ViewModelProvider(this).get(WriteViewModel::class.java)
        lifeCycleRegistry = LifecycleRegistry(this)
        lifeCycleRegistry.markState(Lifecycle.State.CREATED)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_read, R.id.navigation_write
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        mPendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )

    }
    override fun onResume() {
        super.onResume()
        lifeCycleRegistry.markState(Lifecycle.State.RESUMED)
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // check if device support NFC
        if(NFCUtilManager.checkIfNFCIsAvailable(nfcAdapter)){
            //if support check if NFC is turned on
            if(NFCUtilManager.checkIfNFCEnabled(nfcAdapter)){
                nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null)
                //nfcAdapter.setNdefPushMessageCallback(this, this)

            } else {
                showToastMessage("NFC is disabled. Please turn on to proceed")
            }
        } else {
            showToastMessage("NFC not available for your device")
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if(writeViewModel?.isWriteTagOptionOn){
            val messageWrittenSuccessfully = NFCUtilManager.createNFCMessage(writeViewModel?.messageToSave, intent)
            writeViewModel?.isWriteTagOptionOn = false
            writeViewModel?._closeDialog.value = true
            if(messageWrittenSuccessfully){
                showToastMessage("Message has been saved successfully")
            } else {
                showToastMessage("Failed to save message. Please try again")
            }
        } else {
            if(NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action){
                // Check if the fragment is an instance of the right fragment
                val ndefMessageArray = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES
                )
                val ndefMessage = ndefMessageArray[0] as NdefMessage
                val msg = String(ndefMessage.records[0].payload)
                //set message
                readViewModel?.setTagMessage(msg)
            } else {
                readViewModel?.setTagMessage("Empty empty or not valid tag")
            }
        }
    }

    private fun showToastMessage(message: String){
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}
