package com.example.practicacalificaciones

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLite (context: Context, bdName: String,version: Int): SQLiteOpenHelper(context, bdName, null, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Usuario(User TEXT, Password TEXT)")
        db?.execSQL("create table alumnos(matricula TEXT, nombre TEXT,carrera TEXT)")
        db?.execSQL("create table materia(codigoM TEXT, nombre TEXT, creditos TEXT)")
        db?.execSQL("create table cursa(matricula TEXT, codigoM TEXT, U1 NUMERIC(5,2))")
        db?.execSQL("create table calificaciones(matricula TEXT, nombreM TEXT, U1 NUMERIC(5,2),U2 NUMERIC(5,2),U3 NUMERIC(5,2),U4 NUMERIC(5,2))")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
