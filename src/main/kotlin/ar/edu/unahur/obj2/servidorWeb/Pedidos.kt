package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime) {

    fun protocolo() = url.substringBefore("://")
    fun dominioYRuta() = url.substringAfter("://")
    fun indiceRuta() = this.dominioYRuta().indexOf("/")

    fun ruta() =
        try { this.dominioYRuta().drop(indiceRuta()) }
        catch (e:Exception) { "sin ruta" }

    fun extension() = url.substringAfterLast(".")
}