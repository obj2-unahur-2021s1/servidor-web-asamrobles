package ar.edu.unahur.obj2.servidorWeb
//requerimiento 2

class Modulo(val extensiones: MutableList<String>, val mensaje: String, val tiempo: Int) {
    var contadorDeDemoras: Int = 0
    var contadorDeIpSospe: Int = 0

    fun puedeAtender(pedido: Pedido) = extensiones.contains(pedido.extension())

    fun sumarContadorDeIps() {
        contadorDeIpSospe += 1
    }

    fun sumarContadorDeDemoras() {
        contadorDeDemoras += 1
    }

    fun generarRespuesta(pedido: Pedido) =
        Respuesta(CodigoHttp.OK, this.mensaje, this.tiempo, pedido)


}