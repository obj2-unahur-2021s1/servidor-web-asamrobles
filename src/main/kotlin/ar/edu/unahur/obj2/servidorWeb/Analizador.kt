package ar.edu.unahur.obj2.servidorWeb

class Analizador (modulo: Modulo,respuesta: Respuesta) {
    val ipSospe = mutableListOf<String>()
    val pedidosRealizadosSospe = mutableListOf<Pedido>()
    val modulosDeIpSospechosas = mutableListOf<Modulo>()
    var demoraMinima = 10

    fun agregarIpSospe(dato : String) = ipSospe.add(dato)

    fun registrarPedidoSospe(respuesta: Respuesta,modulo : Modulo) =
        if (ipSospe.contains(respuesta.pedido.ip)) {
            pedidosRealizadosSospe.add(respuesta.pedido)
            modulosDeIpSospechosas.add(modulo)
            this.sumarIpsSospeAModulo(modulo)

        }
        else {}

    //

    fun moduloMasConsultado() = modulosDeIpSospechosas.maxBy{ it.contadorDeIpSospe }
    //

    //
    fun mapearIpsDelosPedidosRutas() = pedidosRealizadosSospe.map {it.ruta()}

    fun ipsSospeRuta(ruta : String) = this.mapearIpsDelosPedidosRutas().filter {it == ruta}

    //

    fun cuantosPedidosRealizoIpSospe(ipSospechosa : String) =
        this.mapearIpsDelosPedidos().filter{it == ipSospechosa}.size

    fun mapearIpsDelosPedidos() = pedidosRealizadosSospe.map{it.ip}

    fun sumarIpsSospeAModulo (modulo: Modulo) = modulo.sumarContadorDeIps()

    fun sumarRespuestaDemorada(modulo:Modulo,respuesta: Respuesta) =
        if (demoraMinima < respuesta.tiempo)
            modulo.sumarContadorDeDemoras()
        else {}

}
