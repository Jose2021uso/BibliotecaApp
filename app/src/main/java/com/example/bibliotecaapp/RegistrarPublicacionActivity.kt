package com.example.bibliotecaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.bibliotecaapp.MainActivity.Companion.publicacionRepository
import com.example.bibliotecaapp.Models.Libro
import com.example.bibliotecaapp.Models.Revista
import com.example.bibliotecaapp.databinding.ActivityRegistrarPublicacionBinding
import com.example.bibliotecaapp.databinding.ActivitySeleccionarPublicacionBinding

class RegistrarPublicacionActivity : AppCompatActivity(), View.OnClickListener {

    // Validar para gestionar el viewBinding
    private lateinit var binding: ActivityRegistrarPublicacionBinding
    private var tipoPublicacion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de viewBinding
        binding = ActivityRegistrarPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuraciín del action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tipoPublicacion = intent.extras!!.getInt("tipoPublicacion")

        // Configuración de evento click para el botón de registro
        binding.layoutRegistrarPublicacion.btnGuardarRegistro.setOnClickListener(this)

        if (tipoPublicacion == 1) {
            // En este caso el tipo publicaciín es libro
            // El formulario de registr debe ocultar el campo numero revista
            binding.layoutRegistrarPublicacion.tilNumeroRevista.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.layoutRegistrarPublicacion.btnGuardarRegistro.id -> {
                //Validar si todos los campos estan completos
                if (validar()){
                    //Guardar la publicación
                    //Hat que evaluar si el tipo publicación es Libro o Revista
                    if (tipoPublicacion == 1) {
                        publicacionRepository.add(
                            Libro(
                                binding.layoutRegistrarPublicacion.edtCodigo.text.toString().toInt(),
                                binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                                binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString().toInt()
                            )
                        )

                        // Llamado al dialog
                        configProgressDialog()
                    } else if (tipoPublicacion == 2) {

                        publicacionRepository.add(
                            Revista(
                                binding.layoutRegistrarPublicacion.edtCodigo.text.toString().toInt(),
                                binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                                binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString().toInt(),
                                binding.layoutRegistrarPublicacion.edtNumeroRevista.text.toString().toInt()
                            )
                        )
                        // Llamado al dialog
                        configProgressDialog()
                    }
                }

            }
        }
    }

    // Configuracion del action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Deberá permitir volver a la actividad anterior
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Configurador del progress dialog
    private fun configProgressDialog() {
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        alertBuilder.setView(dialogView)
        alertBuilder.setCancelable(false)
        val dialog = alertBuilder.create()
        dialog.show()
        //Configurando hilo, para asignar tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            finish()
        }, 3000)

    }

    // Función para validar campos
    fun validar(): Boolean {
        var retorno = true
        val vCod: String = binding.layoutRegistrarPublicacion.edtCodigo.text.toString()
        val vTit: String = binding.layoutRegistrarPublicacion.edtTitulo.text.toString()
        val vAnio: String = binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString()
        val vNumRev: String = binding.layoutRegistrarPublicacion.edtNumeroRevista.text.toString()

        if (vCod.isEmpty()) {
            binding.layoutRegistrarPublicacion.edtCodigo.setError("Ingrese el Código")
            retorno = false
        }
        if (vTit.isEmpty()) {
            binding.layoutRegistrarPublicacion.edtTitulo.setError("Ingrese el Título")
            retorno = false
        }
        if (vAnio.isEmpty()) {
            binding.layoutRegistrarPublicacion.edtAnioPublicacion.setError("Ingrese un año")
            retorno = false
        }
        if (tipoPublicacion == 2){
            if (vNumRev.isEmpty()) {
                binding.layoutRegistrarPublicacion.edtNumeroRevista.setError("Ingrese numero de revista")
                retorno = false
            }
        }

        return retorno
    }
}