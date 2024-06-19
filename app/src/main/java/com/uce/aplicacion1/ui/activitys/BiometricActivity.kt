package com.uce.aplicacion1.ui.activitys


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope

import com.uce.aplicacion1.databinding.ActivityBiometricBinding
import com.uce.aplicacion1.ui.entites.DataStoreEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor


val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name="settings")
class BiometricActivity : AppCompatActivity() {



    private lateinit var binding: ActivityBiometricBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo
    private lateinit var biometricManager: BiometricManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val splash  = installSplashScreen()
        binding = ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Aplicacion1")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()



        biometricPrompt = BiometricPrompt(this,
            executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val x= Intent(applicationContext, MainActivity::class.java)
                    startActivity(x)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }
            })

        initListeners()
        Thread.sleep(2000)
        splash.setKeepOnScreenCondition{false}
    }

    private fun initListeners() {
        binding.imagFinger.setOnClickListener(){
            dataStoreSave(DataStoreEntity("Andres",true))
            Log.d("TAG",dataStoreGet().toString())
            //initBiometric()
        }

    }

    private fun initBiometric() {
        //accedemos al biometrico
        val biometricManager = BiometricManager.from(this)

        //debemos poner como nos vamos auttentificar
        val x = biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL
                or BiometricManager.Authenticators.BIOMETRIC_STRONG)

        when(x) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // si ya lo tenemos el sistema nos muetra la pantlla donde esta la huella
                //si existe autetifique y mande la info
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->{}
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED ->{}
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->{
                // llama al sistema y vamos a pedir una peticion para que ingrese el pin y la huella o solo la huella :v
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                }
                startActivity(enrollIntent)
            }
        }
    }

    private fun dataStoreSave(user:DataStoreEntity){
        //Guardar
        lifecycleScope.launch ( Dispatchers.IO ){
            settingDataStore.edit {prefs ->
                prefs[booleanPreferencesKey("active")] = user.valor
                prefs[stringPreferencesKey("user")] =  user.name

            }
        }
    }
    private fun dataStoreGet():DataStoreEntity{
         var ret=DataStoreEntity()
        //Recuperar
        lifecycleScope.launch(Dispatchers.Main) {
            val x = withContext(Dispatchers.IO){
                settingDataStore.data.map{ prefs->
                    DataStoreEntity(
                        prefs[stringPreferencesKey(name="user")] ?:"",
                        prefs[booleanPreferencesKey(name="valor")] ?: false
                    )

                }
            }
            ret = x.first()
        }
        return ret!!

    }


}