package ar.edu.unahur.obj2.servidorWeb

class ServidorWebHTTP(val modulos: MutableList<Modulo>, val analizadores: MutableList<Analizador>) {

  fun urlEsHTTP(pedido: Pedido) = pedido.protocolo() == "http"

  fun codigoSegunProtocolo(pedido: Pedido) =
    if (this.urlEsHTTP(pedido)) CodigoHttp.OK.codigo
    else CodigoHttp.NOT_IMPLEMENTED.codigo

  // Modulos //

  fun agregarModulo(modulo: Modulo) { modulos.add(modulo) }

  fun sacarModulo(modulo: Modulo){ modulos.remove(modulo) }

  fun algunModuloPuedeAtender(pedido: Pedido) = modulos.any { it.puedeAtender(pedido) }

  fun moduloAtiende(pedido: Pedido) = modulos.find { it.puedeAtender(pedido) }!!

  fun atenderPedido(pedido: Pedido) =
    if (this.algunModuloPuedeAtender(pedido)) {
      val moduloQueAtiende = this.moduloAtiende(pedido)
      moduloQueAtiende.generarRespuesta(pedido)
      this.analizarPedidos(moduloQueAtiende,moduloQueAtiende.generarRespuesta(pedido))

    }
    else Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)

  fun atenderPedidoSiEsHTTP(pedido: Pedido) =
    if(this.urlEsHTTP(pedido)) this.atenderPedido(pedido)
    else Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)

  // ANALAIZADORES //

  fun agregarAnalizador(analizador : Analizador) { analizadores.add(analizador) }

  fun sacarAnalizador(analizador: Analizador){ analizadores.remove(analizador) }

  fun analizarPedidos(modulo: Modulo, respuesta: Respuesta) = analizadores.forEach{it.devolverModulo(modulo)}
}





















