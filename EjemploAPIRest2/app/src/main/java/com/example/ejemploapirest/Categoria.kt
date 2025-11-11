package com.example.ejemploapirest

data class Categoria(
    val nombre: String,
    val valor: String
){

    override fun toString(): String {
        return nombre
    }

    companion object{
        val datos
            get() = arrayListOf(
                Categoria(
                    "Negocios",
                    "business"
                ),
                Categoria(
                    "Entretenimiento",
                    "entretaiment"
                ),
                Categoria(
                    "General",
                    "genral"
                )
            )
    }
}