package org.example

import filemaker.Database

fun main() {
    try {
        val host = "127.0.0.1"
        val database = "Bibliotheque_maison"
        val userName = "vincent"
        val password = ""

        println("Connecting to $host:$database")
        val db = Database(host, database, userName, password)
        println("Connected!")
        db.getTables()
        db.example()
    } catch (e: Exception) {
        println("error $e")
    }
}