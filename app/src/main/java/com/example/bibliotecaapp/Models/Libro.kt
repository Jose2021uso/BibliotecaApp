package com.example.bibliotecaapp.Models

import com.example.bibliotecaapp.Interfaces.IPrestable

class Libro(private var c : Int,
            private var t: String,
            private var a: Int,
            private var estadoPrestamo: Boolean = false) : Publicacion(c,t,a), IPrestable  {
    override fun prestar() {
        this.estadoPrestamo = true
    }

    override fun devolver() {
        this.estadoPrestamo = false
    }

    override fun prestado(): Boolean {
        return this.estadoPrestamo
    }

    override fun getTipoPublicacion(): Int = 1
}