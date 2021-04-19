package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)

    val graduacion = Video(120,"SD")
    val comunion = Video(310,"720")
    val miBoda = Video(240,"1080")


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
          graduacion.espacioQueOcupa().shouldBe(120)
        }
        it("calidad HD 720") {
          comunion.espacioQueOcupa().shouldBe(930)
        }
        it("calidad HD 1080") {
          miBoda.espacioQueOcupa().shouldBe(1440)
        }
      }
      describe("modificar calidad") {
        it("cambiar graduacion de calidad SD a 720") {
          graduacion.calidad = "720"
          graduacion.calidad.shouldBe("720")
        }
      }

    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val juana = Usuario()
        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
        juana.agregarPublicacion(graduacion)
        juana.agregarPublicacion(comunion)
        juana.espacioDePublicaciones().shouldBe(551598)
        juana.agregarPublicacion(miBoda)
        juana.espacioDePublicaciones().shouldBe(553038)
      }
    }

    describe("Una publicacion") {
      it("Dar me gusta 2 veces") {
        val antonia = Usuario()
        val elsa = Usuario()
        fotoEnCuzco.darLike(antonia)
        fotoEnCuzco.cantidadLikes().shouldBe(1)
        fotoEnCuzco.darLike(antonia)
        fotoEnCuzco.darLike(elsa)
        fotoEnCuzco.cantidadLikes().shouldBe(2)
      }
    }
  }
})