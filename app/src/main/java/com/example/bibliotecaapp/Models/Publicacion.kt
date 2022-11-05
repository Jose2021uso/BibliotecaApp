package com.example.bibliotecaapp.Models

abstract class Publicacion(
    protected var codigo : Int,
    protected var titulo: String,
    protected  var anioPublicacion : Int) {

    fun getCode(): Int = this.codigo

    fun getTitle(): String = this.titulo

    fun getYerPub() : Int = this.anioPublicacion

    abstract fun getTipoPublicacion() : Int

}