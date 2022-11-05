package com.example.bibliotecaapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecaapp.Adapters.PublicacionAdapter
import com.example.bibliotecaapp.MainActivity.Companion.publicacionRepository
import com.example.bibliotecaapp.databinding.ActivityMostrarListaBinding
import com.google.android.material.snackbar.Snackbar

class MostrarListaActivity : AppCompatActivity() {

    // Variable para configurar viewBinding
    private lateinit var binding: ActivityMostrarListaBinding

    // Variables necesarias para configurar el recyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var publicacionAdapter: PublicacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de viewBinding
        binding = ActivityMostrarListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Habilitar action var
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        // Validar si la lista esta vacia
        if (publicacionRepository.get().size == 0) {
            AlertDialog.Builder(this)
                .setTitle(this.resources.getString(R.string.titulo_lista_vacia))
                .setMessage(this.resources.getString(R.string.msg_lista_vacia)).setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener { dialogInterface, i -> finish() }).show()
        } else {
            configSwipe()
        }
    }

    // Método que configura el recyclerView
    private fun configRecyclerView() {
        recyclerView = binding.rcPublicaciones
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        publicacionAdapter = PublicacionAdapter(publicacionRepository.get(), this)
        recyclerView.adapter = publicacionAdapter
    }

    // Método que configura el action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                // Finaliza la actividad
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Metodo para actualizar la lista
    private fun configSwipe(){
        binding.switeLista.setColorSchemeResources(R.color.teal_200, R.color.blue, R.color.green)
        binding.switeLista.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white))

        binding.switeLista.setOnRefreshListener{
            Handler(Looper.getMainLooper()).postDelayed({
                binding.switeLista.isRefreshing = false
            }, 3000)
            configRecyclerView()
            Snackbar.make(binding.root, "Datos actualizados", Snackbar.LENGTH_SHORT).show()
        }

    }

}