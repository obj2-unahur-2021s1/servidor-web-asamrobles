package ar.edu.unahur.obj2.servidorWeb

class Analizador (val demoraMinima: Int) {
    val ipSospe = mutableListOf<String>()
    val pedidosRealizadosSospe = mutableListOf<Pedido>()
    val modulosDeIpSospechosas = mutableListOf<Modulo>()

    fun agregarIpSospe(dato: String) = ipSospe.add(dato)

    fun sumarRespuestaDemorada(respuesta: Respuesta, modulo: Modulo) {
        if (demoraMinima < respuesta.tiempo) modulo.sumarContadorDeDemoras()
    }
    fun cuantosPedidosRealizoIpSospe(ipSospechosa: String) =
        this.pedidosRealizadosSospe.filter { it.ip == ipSospechosa }.size

    fun sumarIpsSospeAModulo(modulo: Modulo) = modulo.sumarContadorDeIps()
    fun moduloMasConsultado() = modulosDeIpSospechosas.maxBy { it.contadorDeIpSospe }

    fun ipsSospeRuta(ruta: String) = pedidosRealizadosSospe.filter { it.ruta() == ruta }
    fun conjuntoDeIpsSospeEnRuta(ruta: String) = this.ipsSospeRuta(ruta).map { it.ip }.toSet()

    fun registrarPedidoSospe(respuesta: Respuesta, modulo: Modulo) {
        if (ipSospe.contains(respuesta.pedido.ip)) {
            pedidosRealizadosSospe.add(respuesta.pedido)
            modulosDeIpSospechosas.add(modulo)
            this.sumarIpsSospeAModulo(modulo)
        }
    }
    fun aplicarFuncionesAnalizadoras(respuesta: Respuesta, modulo: Modulo) {
        this.sumarRespuestaDemorada(respuesta, modulo)
        this.registrarPedidoSospe(respuesta, modulo)
    }
}
