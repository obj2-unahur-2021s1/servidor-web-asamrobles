package ar.edu.unahur.obj2.servidorWeb

class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)

enum class CodigoHttp(val codigo: Int) {
    OK(200),
    NOT_IMPLEMENTED(501),
    NOT_FOUND(404),
}
