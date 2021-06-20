package ar.edu.unahur.obj2.servidorWeb

class ServidorWebHTTP(val modulos: MutableList<Modulo>) {

  fun urlEsHTTP(pedido: Pedido) = pedido.protocolo() == "http"

  fun codigoSegunProtocolo(pedido: Pedido) =
    if (this.urlEsHTTP(pedido)) CodigoHttp.OK.codigo
    else CodigoHttp.NOT_IMPLEMENTED.codigo

// Hasta aca requerimiento 1 devolver 200 o 501 segun el caso

  fun algunModuloPuedeAtender(pedido: Pedido) = modulos.any { it.puedeAtender(pedido) }

  fun moduloAtiende(pedido: Pedido) = modulos.find { it.puedeAtender(pedido) }!!

  fun atenderPedido(pedido: Pedido) =
    if (this.algunModuloPuedeAtender(pedido))
      Respuesta(CodigoHttp.OK, this.moduloAtiende(pedido).mensaje, this.moduloAtiende(pedido).tiempo, pedido)

    else Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)

  fun atenderPedidoSiEsHTTP(pedido: Pedido) =
    if(this.urlEsHTTP(pedido)) this.atenderPedido(pedido)
    else Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)

  fun cargarModulo(modulo: Modulo) { modulos.add(modulo) }
}





















