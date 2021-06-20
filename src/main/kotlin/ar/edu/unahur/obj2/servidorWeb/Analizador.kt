package ar.edu.unahur.obj2.servidorWeb

class Analizador (modulo: Modulo,respuesta: Respuesta) {

    var demoraMinima = 10

    fun sumarRespuestaDemorada(modulo:Modulo,respuesta: Respuesta) =
        if (demoraMinima < respuesta.tiempo)
            modulo.sumarContador()
        else {}


}
