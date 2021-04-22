package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
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

    describe("un usuario es más amistoso que otro") {
      val carlos = Usuario()
      val martin = Usuario()
      val jonas = Usuario()
      val matias = Usuario()
      it("El primero tiene mas amigos, debe dar verdadero") {
        carlos.agregarAmigo(jonas)
        carlos.agregarAmigo(matias)
        martin.agregarAmigo(jonas)
        carlos.esMasAmistosoQue(martin).shouldBeTrue()
      }
      it("El primero tiene menos amigos,debe dar falso") {
        carlos.agregarAmigo(jonas)
        martin.agregarAmigo(jonas)
        martin.agregarAmigo(matias)
        carlos.esMasAmistosoQue(martin).shouldBeFalse()
      }
    }

    describe("tiene Amigo") {
      val ruben = Usuario()
      val jeremias = Usuario()
      ruben.agregarAmigo(jeremias)
      it("tiene amigo"){
        ruben.tieneAmigo(jeremias).shouldBeTrue()
      }
    }

    describe("propietario") {
      val carlos = Usuario()
      val fotoPlaya = Foto(123,462)
      it("propietario de foto en la playa = ruben"){
        carlos.agregarPublicacion(fotoPlaya)
        fotoPlaya.propietario().shouldBe(carlos)
      }
    }

    describe("Un usuario puede ver una cierta publicacion") {
      val ramon = Usuario()
      val jose = Usuario()
      val carta = Texto("Hola queridos seguidores espero que se encuentren ok")
      carta.cambiarPermiso("publico")
      it("Puede ver su propia publicacion en publica"){
        ramon.agregarPublicacion(carta)
        ramon.puedeVer(carta).shouldBeTrue()
      }
      it("Puede ver su propia publicacion en solo amigos") {
        val otraCarta = Texto("Devuelvanme wollok plis")
        otraCarta.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(otraCarta)
        ramon.puedeVer(otraCarta).shouldBeTrue()
      }
      it("Puede ver su propia privado con lista de permitidos") {
        val otraCarta3 = Texto("Devuelvanme wollok plis")
        otraCarta3.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(otraCarta3)
        ramon.puedeVer(otraCarta3).shouldBeTrue()
      }
      it("Puede ver su propia publico con lista de excluidos") {
        val otraCarta4 = Texto("Devuelvanme wollok plis")
        otraCarta4.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(otraCarta4)
        ramon.puedeVer(otraCarta4).shouldBeTrue()
      }
      it("no es amigo") {
        val cartaAmigo = Texto("Devuelvanme wollok plis")
        cartaAmigo.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(cartaAmigo)
        jose.puedeVer(cartaAmigo).shouldBeFalse()
      }
      it("Es amigo") {
        val cartaAmigo2 = Texto("Devuelvanme wollok plis")
        cartaAmigo2.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(cartaAmigo2)
        ramon.agregarAmigo(jose)
        jose.puedeVer(cartaAmigo2).shouldBeTrue()
      }
      it("no esta en lista de permitidos") {
        val testamento = Texto("blablabla")
        testamento.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(testamento)
        ramon.agregarAmigo(jose)
        jose.puedeVer(testamento).shouldBeFalse()
      }
      it("esta en lista de permitidos") {
        val testamento = Texto("blablabla")
        testamento.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(testamento)
        testamento.agregarPermitido(jose)
        jose.puedeVer(testamento).shouldBeTrue()
      }
      it("no esta en lista de excluidos") {
        val testamento = Texto("blablabla")
        testamento.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(testamento)
        jose.puedeVer(testamento).shouldBeTrue()
      }
      it("esta en lista de excluidos") {
        val testamento = Texto("blablabla")
        testamento.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(testamento)
        testamento.agregarExcluido(jose)
        jose.puedeVer(testamento).shouldBeFalse()
      }
    }


  }
})
