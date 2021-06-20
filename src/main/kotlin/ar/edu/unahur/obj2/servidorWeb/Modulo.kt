package ar.edu.unahur.obj2.servidorWeb
//requerimiento 2

class Modulo(val extensiones: MutableList<String>, val mensaje: String, val tiempo: Int) {

    fun puedeAtender(pedido: Pedido) = extensiones.contains(pedido.extension())

    fun generarRespuesta(pedido: Pedido) = Respuesta(CodigoHttp.OK, this.mensaje, this.tiempo, pedido)
}