package com.example.ejemplosqlite

data class Materia(
    val codigoM: String,
    val nombre: String,
    val creditos: String
){
    companion object{
        val datos
            get() = arrayListOf(
                Materia(
                    "2731",
                    "Desarrollo de Aplicaciones Móviles",
                    "5"
                ),
                Materia(
                    "1932",
                    "Bases de Datos",
                    "4"
                ),
                Materia(
                    "3077",
                    "Estructura de Datos",
                    "4"
                )
            )
    }
}