package com.uce.aplicacion1.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uce.aplicacion1.R
import com.uce.aplicacion1.databinding.ActivityBiometricBinding
import java.util.concurrent.Executor


class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Aplicacion1")
            .setSubtitle("Ingrese su huella digital")
            .build()

        biometricPrompt = BiometricPrompt(this,
            executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }
            })

        //accedemos al biometrico
        val biometricManager = BiometricManager.from(this)

        //debemos poner como nos vamos auttentificar
        val x = biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL
                or BiometricManager.Authenticators.BIOMETRIC_STRONG)

        when(x) {
           BiometricManager.BIOMETRIC_SUCCESS -> {
               // si ya lo tenemos el sistema nos muetra la pantlla donde esta la huella
                B
           }
           BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->{}
           BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED ->{}
           BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->{
               // llama al sistema y vamos a pedir una peticion para que ingrese el pin y la huella o solo la huella :v
               val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                   putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                       BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
               }
               startActivityForResult(enrollIntent, REQUEST_CODE)
           }
        }

    }

}