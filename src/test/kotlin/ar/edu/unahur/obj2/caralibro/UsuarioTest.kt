package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)

    val videoSD = Video(10,"720")
    val video720 = Video(10,"720")
    val video1080 = Video(10,"1080")


    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }
      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }


      describe("de tipo video" ) {
        it("calidad SD") {
          videoSD.modificarCalidad("SD")
          videoSD.espacioQueOcupa().shouldBe(10)
        }
        it("calidad HD 720") {
          video720.espacioQueOcupa().shouldBe(30)
        }
        it("calidad HD 1080") {
          video1080.espacioQueOcupa().shouldBe(60)
        }
      }

    }
//ARREGLAR EL TOTAL
    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.agregarPublicacion(videoSD)
        juana.agregarPublicacion(video720)
        juana.agregarPublicacion(video1080)
        juana.espacioDePublicaciones().shouldBe(550648)
      }
    }
  }
})