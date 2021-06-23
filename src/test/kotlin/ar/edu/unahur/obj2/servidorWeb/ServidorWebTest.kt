package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
    describe("Un servidor web") {

//pedidos con protocolo http
//pedidos que pueden ser atendidos por algun modulo
        val pedidoHTTP = Pedido("125.333.345.1", "http://unahur.org.ar/documentos/doc1.html",
            LocalDateTime.of(2021,5,10,12,25,55))
        val pedidoTextoPDF_IP2 = Pedido("125.333.345.2", "http://unahur.org.ar/documentos/texto1.pdf",
            LocalDateTime.of(2021,2,3,4,5,6))
        val pedidoAudioMP3_IP3 = Pedido("125.333.345.3", "http://unahur.org.ar/documentos/audio1.mp3",
            LocalDateTime.of(2020,1,2,3,4,5))
        val pedidoVideoMKV_IP4 = Pedido("125.333.345.4", "http://unahur.org.ar/documentos/video1.mkv",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoVideoMP4_IP5 = Pedido("125.333.345.5", "http://unahur.org.ar/documentos/video1.mp4",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoVideoMKV_IP6 = Pedido("125.333.345.6", "http://unahur.org.ar/documentos/video1.mkv",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoVideoMP4_IP7 = Pedido("125.333.345.7", "http://unahur.org.ar/documentos/video1.mp4",
            LocalDateTime.of(2021,7,8,9,10,11))

//pedidos con diferentes IPs
        val pedidoAudioMP3_IP2 = Pedido("125.333.345.2", "http://unahur.org.ar/documentos/audio1.mp3",
            LocalDateTime.of(2020,1,2,3,4,5))
        val pedidoVideoMKV_IP2 = Pedido("125.333.345.2", "http://unahur.org.ar/documentos/video1.mkv",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoVideoMP4_IP3 = Pedido("125.333.345.3", "http://unahur.org.ar/documentos/video1.mp4",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoVideoMKV_IP5 = Pedido("125.333.345.5", "http://unahur.org.ar/documentos/video1.mkv",
            LocalDateTime.of(2021,7,8,9,10,11))
        val pedidoComprimidoRAR = Pedido("125.333.345.5", "http://unahur.org.ar/documentos/docs.rar",
            LocalDateTime.of(2021,8,9,10,11,12))

//pedidos q no pueden ser atendidos por ningun modulo
        val pedidoImagenGIF = Pedido("125.333.345.6", "http://unahur.org.ar/documentos/gif1.gif",
            LocalDateTime.of(2021,9,10,11,12,13))

//pedidos con protocolo diferente a http
        val pedidoHTTPS = Pedido("125.333.345.7", "https://unahur.org.ar/documentos/doc2.html",
            LocalDateTime.of(2021,10,11,12,13,14))
        val pedidoFTP = Pedido("125.333.345.8", "ftp://unahur.org.ar/documentos/doc3.txt",
            LocalDateTime.of(2021,11,12,13,14,15))

//modulos
        val moduloImagenes = Modulo(mutableListOf("jpg", "jpeg", "png", "bmp"), "La imagen fue procesada con exito", 5)
        val moduloTexto = Modulo(mutableListOf("md", "txt", "pdf", "doc", "html"), "El texto fue procesado con exito", 2)
        val moduloVideo = Modulo(mutableListOf("mp4", "avi", "mkv", "flv", "h.264"), "El video fue procesado con exito", 8)
        val moduloAudio = Modulo(mutableListOf("mp3", "midi", "wav", "wma", "flac"), "El audio fue procesado con exito", 6)
        val moduloCompresion = Modulo(mutableListOf("rar", "zip", "7z"), "El archivo fue procesado con exito", 6)

//Analizadores
        val analizardor1 = Analizador(9)
        val analizardor2 = Analizador(11)
        val analizardor3 = Analizador(5)
        val analizardor4 = Analizador(1)

//Acciones
        analizardor1.agregarIpSospe("125.333.345.2")
        analizardor1.agregarIpSospe("125.333.345.3")
        analizardor1.agregarIpSospe("125.333.345.7")

        analizardor2.agregarIpSospe("125.333.345.3")
        analizardor2.agregarIpSospe("125.333.345.4")
        analizardor2.agregarIpSospe("125.333.345.5")
        analizardor2.agregarIpSospe("125.333.345.6")

        val servidorHTTP = ServidorWebHTTP(mutableListOf(moduloAudio, moduloImagenes, moduloTexto, moduloVideo),
            mutableListOf(analizardor1, analizardor2, analizardor3))

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

        describe("Req 2: test de modulos") {
            it("un pedido que no puede ser atendido por ningun modulo devuelve una respuesta " +
                    "con codigo NOT_FOUND, body vacio, tiempo 10 segundos y una referencia al pedido") {

                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).codigo shouldBe CodigoHttp.NOT_FOUND
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).body shouldBe ""
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).tiempo shouldBe 10
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).pedido shouldBe pedidoComprimidoRAR
            }
            it("si cargo un modulo al servidor que puede atender pedidos que antes no podia devuelve una" +
                    "respuesta con codigo OK, un body y tiempo especificados por el modulo y una referencia al pedido") {

                servidorHTTP.agregarModulo(moduloCompresion)

                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).codigo shouldBe CodigoHttp.OK
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).body shouldBe "El archivo fue procesado con exito"
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).tiempo shouldBe 6
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoComprimidoRAR).pedido shouldBe pedidoComprimidoRAR
            }
            it("un pedido cuyo protocolo no es http devuelve una respuesta con codigo" +
                    "NOT_IMPLEMENTED, body vacio, tiempo 10 segundos y una referencia al pedido") {

                servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).codigo shouldBe CodigoHttp.NOT_IMPLEMENTED
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).body shouldBe ""
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).tiempo shouldBe 10
                servidorHTTP.atenderPedidoSiEsHTTP(pedidoFTP).pedido shouldBe pedidoFTP
            }
        }
        describe("Req 3: test de analizadores") {
            // mod video
            servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP4) //8
            servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP4) //8
            servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP4) //8

            servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3) //6
            servidorHTTP.atenderPedidoSiHayModulo(pedidoTextoPDF_IP2) //2

            it("cantidad de respuestas demoradas para un modulo") {
                moduloVideo.contadorDeDemoras shouldBe 3
                moduloAudio.contadorDeDemoras shouldBe 1
                moduloTexto.contadorDeDemoras shouldBe 0
            }
            it("agrego analizador 4 y modifica el contador de demoras de todos los " +
                    "modulos para todos los pedidos nuevos que se atiendan") {

                servidorHTTP.agregarAnalizador(analizardor4)//1

                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP4)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3) //6
                servidorHTTP.atenderPedidoSiHayModulo(pedidoTextoPDF_IP2) //2

                moduloVideo.contadorDeDemoras shouldBe 5
                moduloAudio.contadorDeDemoras shouldBe 3
                moduloTexto.contadorDeDemoras shouldBe 1
            }
            it("la IP 125.333.345.4 realizo 3 pedidos, ninguno es sospechoso para el analizador 1 ya que no esta en" +
                    " su lista de IPs sospechosas pero todos lo son para el analizador 2") {

                analizardor1.cuantosPedidosRealizoIpSospe("125.333.345.4") shouldBe 0
                analizardor2.cuantosPedidosRealizoIpSospe("125.333.345.4") shouldBe 3
            }
            it("la IP 125.333.345.2 realizo un pedido sospechoso para el analizador 1 ya que esta en su lista de IPs sospechosas") {
                analizardor1.cuantosPedidosRealizoIpSospe("125.333.345.2") shouldBe 1
            }
            it("si la IP 125.333.345.3 realiza otro pedido el analizador 1 debe contarlo") {
                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3)

                analizardor1.cuantosPedidosRealizoIpSospe("125.333.345.3") shouldBe 2
            }
            it("con 2 pedidos con extension de audio atendidos, hechos por la ip sospechosa" +
                    "125.333.345.3 el modulo mas consultado para el analizador 1 debe ser el de audio") {

                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3)
                analizardor1.moduloMasConsultado() shouldBe moduloAudio
            }
            it("con 3 pedidos con extension de texto atendidos, hechos por la ip sospechosa" +
                    "125.333.345.2 el modulo mas consultado para el analizador 1 debe ser el de texto") {

                servidorHTTP.atenderPedidoSiHayModulo(pedidoTextoPDF_IP2)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoTextoPDF_IP2)

                analizardor1.moduloMasConsultado() shouldBe moduloTexto
            }
            it("con 3 pedidos con extension de video atendidos, hechos por la ip sospechosa" +
                    "125.333.345.4 el modulo mas consultado para el analizador 2 debe ser el de video") {

                analizardor2.moduloMasConsultado() shouldBe moduloVideo
            }
            it("con 4 pedidos con extension de audio atendidos, hechos por la ip sospechosa" +
                    "125.333.345.3 el modulo mas consultado para el analizador 2 debe ser el de audio") {

                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoAudioMP3_IP3)

                analizardor2.moduloMasConsultado() shouldBe moduloAudio
            }
            it("para la ruta /documentos/video1.mkv el conjunto de IPs sospechosas debe estar formado por IPs en " +
                    "pedidos cuya URL contiene esa ruta y ademas esten en la lista de IPs sospechosas de un analizador") {

                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP2)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP5)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMKV_IP6)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMP4_IP3)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoVideoMP4_IP7)
                servidorHTTP.atenderPedidoSiHayModulo(pedidoHTTP)

                analizardor2.conjuntoDeIpsSospeEnRuta("/documentos/video1.mkv").
                    shouldContainExactlyInAnyOrder(listOf("125.333.345.4", "125.333.345.5", "125.333.345.6"))

                analizardor2.conjuntoDeIpsSospeEnRuta("/documentos/video1.mp4").
                    shouldContainExactlyInAnyOrder(listOf("125.333.345.3"))

                analizardor1.conjuntoDeIpsSospeEnRuta("/documentos/video1.mkv").
                    shouldContainExactlyInAnyOrder(listOf("125.333.345.2"))

                analizardor1.conjuntoDeIpsSospeEnRuta("/documentos/video1.mp4").
                    shouldContainExactlyInAnyOrder(listOf("125.333.345.3", "125.333.345.7"))
            }
        }
    }
})
