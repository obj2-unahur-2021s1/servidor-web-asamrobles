package ar.edu.unahur.obj2.servidorWeb

class ServidorWebHTTP(val modulos: MutableList<Modulo>, val analizadores: MutableList<Analizador>) {
  val respuestas = mutableListOf<Respuesta>()

  fun urlEsHTTP(pedido: Pedido) = pedido.protocolo() == "http"

  fun codigoSegunProtocolo(pedido: Pedido) =
    if (this.urlEsHTTP(pedido)) CodigoHttp.OK.codigo
    else CodigoHttp.NOT_IMPLEMENTED.codigo

  // Modulos //

  fun agregarModulo(modulo: Modulo) { modulos.add(modulo) }

  fun sacarModulo(modulo: Modulo) { modulos.remove(modulo) }

  fun agregarRespuesta(respuesta: Respuesta) { respuestas.add(respuesta) }

  fun algunModuloPuedeAtender(pedido: Pedido) = modulos.any { it.puedeAtender(pedido) }

  fun moduloAtiende(pedido: Pedido) = modulos.find { it.puedeAtender(pedido) }!!

  fun atenderPedidoSiHayModulo(pedido: Pedido) =
    if (this.algunModuloPuedeAtender(pedido)) {
      val moduloQueAtiende = this.moduloAtiende(pedido)
      this.analizarPedidos(moduloQueAtiende.generarRespuesta(pedido), moduloQueAtiende)
      moduloQueAtiende.generarRespuesta(pedido)
    }
    else Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)

  fun atenderPedidoSiEsHTTP(pedido: Pedido) =
    if (this.urlEsHTTP(pedido)) this.atenderPedidoSiHayModulo(pedido)
    else Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)

  fun atenderPedidoYGuardarRespuesta(pedido: Pedido) {
    this.agregarRespuesta(this.atenderPedidoSiEsHTTP(pedido))
  }

  // ANALIZADORES //

  fun agregarAnalizador(analizador: Analizador) { analizadores.add(analizador) }

  fun sacarAnalizador(analizador: Analizador) { analizadores.remove(analizador) }

  fun analizarPedidos(respuesta: Respuesta, modulo: Modulo) =
    analizadores.forEach { it.aplicarFuncionesAnalizadoras(respuesta, modulo) }
}



















