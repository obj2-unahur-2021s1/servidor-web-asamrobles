package ar.edu.unahur.obj2.servidorWeb
//requerimiento 2

class Modulo(val extensiones: MutableList<String>, val mensaje: String, val tiempo: Int) {
    var contadorDeDemoras: Int = 0
    var demoraMinima : Int = 10

    fun puedeAtender(pedido: Pedido) = extensiones.contains(pedido.extension())

    fun sumarContador() {
        if (this.tiempo > demoraMinima)
        contadorDeDemoras += 1
    }

    fun generarRespuesta(pedido: Pedido) : Respuesta {
        this.sumarContador()
        return Respuesta(CodigoHttp.OK, this.mensaje, this.tiempo, pedido)
    }

}