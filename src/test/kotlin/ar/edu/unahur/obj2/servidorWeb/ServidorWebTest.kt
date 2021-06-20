package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Un servidor web") {

//pedidos con protocolo http
//pedidos que pueden ser atendidos por algun modulo
    val pedidoHTTP = Pedido("125.333.345.1", "http://unahur.org.ar/documentos/doc1.html", LocalDateTime.of(2021,5,10,12,25,55))
    val pedidoTextoPDF = Pedido("125.333.345.2", "http://unahur.org.ar/documentos/texto1.pdf", LocalDateTime.of(2021,2,3,4,5,6))
    val pedidoAudioMP3 = Pedido("125.333.345.3", "http://unahur.org.ar/documentos/audio1.mp3", LocalDateTime.of(2020,1,2,3,4,5))
    val pedidoVideoMKV = Pedido("125.333.345.4", "http://unahur.org.ar/documentos/video1.mkv", LocalDateTime.of(2021,7,8,9,10,11))

//pedidos q no pueden ser atendidos por ningun modulo
    val pedidoComprimidoRAR = Pedido("125.333.345.5", "http://unahur.org.ar/documentos/docs.rar", LocalDateTime.of(2021,8,9,10,11,12))
    val pedidoImagenGIF = Pedido("125.333.345.6", "http://unahur.org.ar/documentos/gif1.gif", LocalDateTime.of(2021,9,10,11,12,13))

//pedidos con protocolo diferente a http
    val pedidoHTTPS = Pedido("125.333.345.7", "https://unahur.org.ar/documentos/doc2.html", LocalDateTime.of(2021,10,11,12,13,14))
    val pedidoFTP = Pedido("125.333.345.8", "ftp://unahur.org.ar/documentos/doc3.txt", LocalDateTime.of(2021,11,12,13,14,15))


    //modulos
    val moduloImagenes = Modulo(mutableListOf("jpg", "jpeg", "png", "gif", "bmp"), "La imagen fue procesada con exito", 5)
    val moduloTexto = Modulo(mutableListOf("md", "txt", "pdf", "doc", "html"), "El texto fue procesado con exito", 2)
    val moduloVideo = Modulo(mutableListOf("mp4", "avi", "mkv", "flv", "h.264"), "El video fue procesado con exito", 8)
    val moduloAudio = Modulo(mutableListOf("mp3", "midi", "wav", "wma", "flac"), "El audio fue procesado con exito", 6)
    val moduloCompresion = Modulo(mutableListOf("rar", "zip", "7z"), "El archivo fue procesado con exito", 6)

    val servidorHTTP = ServidorWebHTTP(mutableListOf(moduloAudio, moduloImagenes, moduloTexto, moduloVideo),
        mutableListOf())

    describe("test descomponer URL") {
      it("separar protocolo") {
        pedidoHTTP.protocolo() shouldBe "http"
      }
      it("separar ruta") {
        pedidoHTTP.ruta() shouldBe "/documentos/doc1.html"
      }
      it("separar extension") {
        pedidoHTTP.extension() shouldBe "html"
      }
    }

    describe("Req 1: test de protocolo") {
      it("un servidor http que recibe un pedido con protocolo http debe devolver codigo 200") {
        servidorHTTP.codigoSegunProtocolo(pedidoHTTP) shouldBe 200
      }
      it("un servidor http que recibe un pedido con protocolo distinto de http debe devolver codigo 501") {
        servidorHTTP.codigoSegunProtocolo(pedidoHTTPS) shouldBe 501
      }
    }
//Hasta aca requerimiento 1 para subir probado separar url y devolver 200 o 501 segun el caso

    describe("Req 2: test de modulos") {
      it("un pedido que no puede ser atendido por ningun modulo devuelve una respuesta con codigo NOT_FOUND, body vacio, tiempo 10 segundos y una referencia al pedido") {
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).codigo shouldBe CodigoHttp.NOT_FOUND
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).body shouldBe ""
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).tiempo shouldBe 10
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).pedido shouldBe pedidoComprimidoRAR
      }
      it("si cargo un modulo al servidor que puede atender pedidos que antes no podia devuelve una respuesta con codigo OK, un body y tiempo especificados por el modulo y una referencia al pedido") {
        servidorHTTP.agregarModulo(moduloCompresion)

        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).codigo shouldBe CodigoHttp.OK
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).body shouldBe "El archivo fue procesado con exito"
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).tiempo shouldBe 6
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).pedido shouldBe pedidoComprimidoRAR
      }
      it("un pedido cuyo protocolo no es http devuelve una respuesta con codigo NOT_IMPLEMENTED, body vacio, tiempo 10 segundos y una referencia al pedido") {
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).codigo shouldBe CodigoHttp.NOT_IMPLEMENTED
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).body shouldBe ""
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).tiempo shouldBe 10
        servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).pedido shouldBe pedidoFTP
      }
    }

  }
})
