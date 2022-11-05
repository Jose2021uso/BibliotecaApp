package com.example.bibliotecaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bibliotecaapp.Repository.PublicacionRepository
import com.example.bibliotecaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Variable para manejar el viewBinding
    private lateinit var binding: ActivityMainBinding

    //Configuración de companion object para agregar desde otras pantallas
    companion object{
        val publicacionRepository : PublicacionRepository = PublicacionRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar evento click de los botones
        binding.layoutPrincipal.btnAgregarPub.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarLista.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarDatos.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.layoutPrincipal.btnAgregarPub.id -> {
                // Abrir pantalla seleccionar publicación

                startActivity(Intent(this, SeleccionarPublicacionActivity::class.java))
            }

            binding.layoutPrincipal.btnMostrarLista.id->{
                // Mostrar lista de publicaciones
                startActivity(Intent(this, MostrarListaActivity::class.java))
            }
            binding.layoutPrincipal.btnMostrarDatos.id->{
                //Mostrar datos del desarrollador
                startActivity(Intent(this, MostrarDatosActivity::class.java))
            }
        }
    }
}