package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

// Para no tener los códigos "tirados por ahí", usamos un enum que le da el nombre que corresponde a cada código
// La idea de las clases enumeradas es usar directamente sus objetos: CodigoHTTP.OK, CodigoHTTP.NOT_IMPLEMENTED, etc
enum class CodigoHttp(val codigo: Int) {
  OK(200),
  NOT_IMPLEMENTED(501),
  NOT_FOUND(404),
}

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime) {

  fun protocolo() = url.substringBefore("://")
  fun dominioYRuta() = url.substringAfter("://")
  fun indiceRuta() = this.dominioYRuta().indexOf("/")

  fun ruta() =
    try { this.dominioYRuta().drop(indiceRuta()) }
    catch (e:Exception) { "sin ruta" }

  fun extension() = url.substringAfterLast(".")
}

class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)

class ServidorWebHTTP(val modulos: MutableList<Modulo>) {

  fun urlEsHTTP(pedido: Pedido) = pedido.protocolo() == "http"

  fun codigoSegunProtocolo(pedido: Pedido) =
    if (this.urlEsHTTP(pedido)) CodigoHttp.OK.codigo
    else CodigoHttp.NOT_IMPLEMENTED.codigo

// Hasta aca requerimiento 1 devolver 200 o 501 segun el caso

  fun algunModuloPuedeAtender(pedido: Pedido) = modulos.any { it.puedeAtender(pedido) }

  fun moduloAtiende(pedido: Pedido) = modulos.find { it.puedeAtender(pedido) }!!

// ver tiempo de respuesta q varia si es ok y si devuelve el codigo o la rta

  fun atenderPedido(pedido: Pedido) =
    if (this.algunModuloPuedeAtender(pedido))
      Respuesta(CodigoHttp.OK, this.moduloAtiende(pedido).mensaje, this.moduloAtiende(pedido).tiempo, pedido)

    else Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)

  fun atenderPedidoSiEsHTTP(pedido: Pedido) =
    if(this.urlEsHTTP(pedido)) this.atenderPedido(pedido)
    else Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)

  fun cargarModulo(modulo: Modulo) { modulos.add(modulo) }
}





















