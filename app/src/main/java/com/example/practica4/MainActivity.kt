package com.example.practica4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var txtNombre: EditText
    private lateinit var rgPeso: RadioGroup
    private lateinit var rbLibra: RadioButton
    private lateinit var rbKilo: RadioButton
    private lateinit var rgComplementos: RadioGroup
    private lateinit var rbEntrenador: RadioButton
    private lateinit var rbProteina: RadioButton
    private lateinit var rbDieta: RadioButton
    private lateinit var btnRegistro: Button
    private lateinit var tvMessage: TextView
    private lateinit var layoutRegistro: View
    private lateinit var layoutResultado: View

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)
            initializeViews()
            setupToolbar()
            setupListeners()
            handleEdgeToEdgeLayout()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}", e)
            showToast("Error initializing app: ${e.message}")
            finish()
        }
    }

    private fun initializeViews() {
        try {
            toolbar = findViewById(R.id.toolbar)
            txtNombre = findViewById(R.id.inputName)
            rgPeso = findViewById(R.id.containerGroup)
            rbLibra = findViewById(R.id.libras)
            rbKilo = findViewById(R.id.kilos)
            rgComplementos = findViewById(R.id.radioGroup)
            rbEntrenador = findViewById(R.id.radioButton8)
            rbProteina = findViewById(R.id.radioButton9)
            rbDieta = findViewById(R.id.radioButton10)
            btnRegistro = findViewById(R.id.button)
            tvMessage = findViewById(R.id.textView2)
            layoutRegistro = findViewById(R.id.layoutRegistro)
            layoutResultado = findViewById(R.id.layoutResultado)
        } catch (e: Exception) {
            Log.e(TAG, "Error in initializeViews: ${e.message}", e)
            throw e
        }
    }

    private fun setupToolbar() {
        try {
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Registro"
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupToolbar: ${e.message}", e)
            throw e
        }
    }

    private fun setupListeners() {
        try {
            btnRegistro.setOnClickListener {
                if (validateInput()) {
                    processRegistration()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupListeners: ${e.message}", e)
            showToast("Error setting up listeners: ${e.message}")
        }
    }

    private fun validateInput(): Boolean {
        return try {
            when {
                txtNombre.text.toString().trim().isEmpty() -> {
                    showToast("Por favor, ingrese su nombre")
                    false
                }
                rgPeso.checkedRadioButtonId == -1 -> {
                    showToast("Por favor, seleccione una unidad de peso")
                    false
                }
                rgComplementos.checkedRadioButtonId == -1 -> {
                    showToast("Por favor, seleccione un complemento")
                    false
                }
                else -> true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in validateInput: ${e.message}", e)
            showToast("Error validating input: ${e.message}")
            false
        }
    }

    private fun processRegistration() {
        try {
            val nombre = txtNombre.text.toString().trim()
            val unidadPeso = if (rbLibra.isChecked) "Libras (Ls)" else "Kilogramos (kg)"
            val complemento = when {
                rbEntrenador.isChecked -> "Entrenador privado"
                rbProteina.isChecked -> "Bote de proteína"
                rbDieta.isChecked -> "Dieta personalizada"
                else -> ""
            }

            val mensaje = "¡Registro exitoso!\n\nNombre: $nombre\nUnidad de peso: $unidadPeso\nComplemento: $complemento"
            tvMessage.text = mensaje

            layoutRegistro.visibility = View.GONE
            layoutResultado.visibility = View.VISIBLE
        } catch (e: Exception) {
            Log.e(TAG, "Error in processRegistration: ${e.message}", e)
            showToast("Error processing registration: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleEdgeToEdgeLayout() {
        try {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
                val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom)
                insets
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in handleEdgeToEdgeLayout: ${e.message}", e)

        }
    }
}