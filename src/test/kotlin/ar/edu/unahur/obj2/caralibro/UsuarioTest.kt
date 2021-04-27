package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
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
      it("Una publicacion de tipo publica") {
        ramon.agregarPublicacion(carta)
        jose.puedeVer(carta).shouldBeTrue()
      }
      it("Puede ver su propia publicacion en solo amigos") {
        val otraCarta = Texto("a la tarde se viene wrestlemania")
        otraCarta.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(otraCarta)
        ramon.puedeVer(otraCarta).shouldBeTrue()
      }
      it("Puede ver su propia privado con lista de permitidos") {
        val otraCarta3 = Texto("fue bueno el torneo de tenis")
        otraCarta3.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(otraCarta3)
        ramon.puedeVer(otraCarta3).shouldBeTrue()
      }
      it("Puede ver su propia publico con lista de excluidos") {
        val otraCarta4 = Texto("no llegamos al concierto, que pena")
        otraCarta4.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(otraCarta4)
        ramon.puedeVer(otraCarta4).shouldBeTrue()
      }
      it("no es amigo") {
        val cartaAmigo = Texto("preparate para el torneo de la semana que viene")
        cartaAmigo.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(cartaAmigo)
        jose.puedeVer(cartaAmigo).shouldBeFalse()
      }
      it("Es amigo") {
        val cartaAmigo2 = Texto("instalate esa app que esta buena")
        cartaAmigo2.cambiarPermiso("solo amigos")
        ramon.agregarPublicacion(cartaAmigo2)
        ramon.agregarAmigo(jose)
        jose.puedeVer(cartaAmigo2).shouldBeTrue()
      }
      it("no esta en lista de permitidos") {
        val testamento = Texto("dono mi coleccion de mariposas")
        testamento.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(testamento)
        ramon.agregarAmigo(jose)
        jose.puedeVer(testamento).shouldBeFalse()
      }
      it("esta en lista de permitidos") {
        val testamento2 = Texto("dono mi coche")
        testamento2.cambiarPermiso("privado con lista de permitidos")
        ramon.agregarPublicacion(testamento2)
        testamento2.agregarPermitido(jose)
        jose.puedeVer(testamento2).shouldBeTrue()
      }
      it("no esta en lista de excluidos") {
        val testamento3 = Texto("dono mi set de pelucas")
        testamento3.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(testamento3)
        jose.puedeVer(testamento3).shouldBeTrue()
      }
      it("esta en lista de excluidos") {
        val testamento4 = Texto("dono mi casa de vacaciones")
        testamento4.cambiarPermiso("publico con lista de excluidos")
        ramon.agregarPublicacion(testamento4)
        testamento4.agregarExcluido(jose)
        jose.puedeVer(testamento4).shouldBeFalse()
      }
    }

    describe("Mejores amigos de un usuario") {
      val fotoEnMarDelPlata = Foto(720, 1980)
      val fotoEnParqueChas = Foto(240,720)
      val videoGraduacion = Video(120,"SD")

      val ramon = Usuario()
      val marely = Usuario()
      val fredy = Usuario()
      val analia = Usuario()
      val roque = Usuario()

      ramon.agregarAmigo(marely)
      ramon.agregarAmigo(fredy)
      ramon.agregarAmigo(analia)
      ramon.agregarAmigo(roque)

      ramon.agregarPublicacion(fotoEnMarDelPlata)
      ramon.agregarPublicacion(fotoEnParqueChas)
      ramon.agregarPublicacion(videoGraduacion)

      fotoEnMarDelPlata.cambiarPermiso("solo amigos")
      fotoEnParqueChas.cambiarPermiso("privado con lista de permitidos")
      videoGraduacion.cambiarPermiso("publico con lista de excluidos")
      fotoEnParqueChas.agregarPermitido(fredy)
      fotoEnParqueChas.agregarPermitido(analia)
      videoGraduacion.agregarExcluido(roque)

      it("Puede ver todas las publicaciones") {
        analia.puedeVer(fotoEnMarDelPlata).shouldBeTrue()
        analia.puedeVer(fotoEnParqueChas).shouldBeTrue()
        analia.puedeVer(videoGraduacion).shouldBeTrue()
        ramon.puedeVerTodasMisPublicaciones(analia).shouldBeTrue()
        ramon.puedeVerTodasMisPublicaciones(fredy).shouldBeTrue()
      }
      it("No puede ver todas las publicaciones") {
        ramon.puedeVerTodasMisPublicaciones(roque).shouldBeFalse()
      }
      it("los mejores amigos de Ramon") {
        ramon.mejoresAmigos().shouldContainExactlyInAnyOrder(fredy, analia)
      }
      //ramon.mejoresAmigos.shouldContainExactlyInAnyOrder(fredy, analia)
      //ALTERNATIVA PERO CON ORDEN DE LA LISTA.
      // ramon.mejoresAmigos.shouldContainExactly(fredy, analia)
      it ("El amigo mas popular de") {
        val diego = Usuario()
        val diana = Usuario()
        val fede = Usuario()
        val brayan = Usuario()

        val miPrimerCoche = Foto(1200, 1980)
        val miEstado = Texto("hoy se estrena mi serie favorita")
        val unboxingPS5 = Video(480, "720")

        val miPrimeraCasa = Foto(1280, 720)
        val viajando = Texto("estoy en el avion, viajando a la costa")
        val abriendoRegalosDeNavidad = Video(240, "720")

        val conciertoEnLunaPark = Foto(1480, 640)
        val reflexion = Texto("me acabo de dar cuenta que no debo molestar a mis hermanos")
        val primerDienteDeMiHijo = Video(1280, "SD")

        diego.agregarAmigo(diana)
        diego.agregarAmigo(fede)
        diego.agregarAmigo(brayan)

        diana.agregarPublicacion(miPrimerCoche)
        diana.agregarPublicacion(miEstado)
        diana.agregarPublicacion(unboxingPS5)

        fede.agregarPublicacion(miPrimeraCasa)
        fede.agregarPublicacion(viajando)
        fede.agregarPublicacion(abriendoRegalosDeNavidad)

        brayan.agregarPublicacion(conciertoEnLunaPark)
        brayan.agregarPublicacion(reflexion)
        brayan.agregarPublicacion(primerDienteDeMiHijo)

        viajando.darLike(diana)
        abriendoRegalosDeNavidad.darLike(diana)
        conciertoEnLunaPark.darLike(diana)
        reflexion.darLike(diana)
        primerDienteDeMiHijo.darLike(diana)

        unboxingPS5.darLike(fede)
        reflexion.darLike(fede)
        primerDienteDeMiHijo.darLike(fede)

        miPrimerCoche.darLike(brayan)
        miEstado.darLike(brayan)
        unboxingPS5.darLike(brayan)
        viajando.darLike(brayan)

        diego.elAmigoMasPopular().shouldBe(brayan)
      }
    }

    describe("Un usuario stalkea a otro") {
      val cristian = Usuario()
      val ruben = Usuario()//stalker

      val fotoEnMiami = Foto(123,695)
      val fotoEnGalicia = Foto(596,839)
      val fotoEnRoma = Foto(350,399)
      val fotoEnGrecia = Foto(474,344)
      val fotoEnTurkia = Foto(322,645)
      val fotoEnJapon = Foto(882,638)
      val fotoEnColiseo = Foto(2533,1047)
      val fotoEnEstocolmo = Foto(1933,3773)
      val fotoEnCataratas = Foto(288,902)
      val fotoEnGlaciar = Foto(829,1022)
      val videoEnComicon = Video(1234,"720")

      fotoEnMiami.cambiarPermiso("publico")
      fotoEnGalicia.cambiarPermiso("publico")
      fotoEnRoma.cambiarPermiso("publico")
      fotoEnGrecia.cambiarPermiso("publico")
      fotoEnTurkia.cambiarPermiso("publico")
      fotoEnJapon.cambiarPermiso("publico")
      fotoEnColiseo.cambiarPermiso("publico")
      fotoEnEstocolmo.cambiarPermiso("publico")
      fotoEnCataratas.cambiarPermiso("publico")
      fotoEnGlaciar.cambiarPermiso("publico")
      videoEnComicon.cambiarPermiso("publico")

      cristian.agregarPublicacion(fotoEnMiami)
      cristian.agregarPublicacion(fotoEnGalicia)
      cristian.agregarPublicacion(fotoEnRoma)
      cristian.agregarPublicacion(fotoEnGrecia)
      cristian.agregarPublicacion(fotoEnTurkia)
      cristian.agregarPublicacion(fotoEnJapon)
      cristian.agregarPublicacion(fotoEnColiseo)
      cristian.agregarPublicacion(fotoEnEstocolmo)
      cristian.agregarPublicacion(fotoEnCataratas)
      cristian.agregarPublicacion(fotoEnGlaciar)
      cristian.agregarPublicacion(videoEnComicon)

      fotoEnMiami.darLike(ruben)
      fotoEnGalicia.darLike(ruben)
      fotoEnRoma.darLike(ruben)
      fotoEnGrecia.darLike(ruben)
      fotoEnTurkia.darLike(ruben)
      fotoEnJapon.darLike(ruben)
      fotoEnColiseo.darLike(ruben)
      fotoEnEstocolmo.darLike(ruben)
      fotoEnCataratas.darLike(ruben)

      it("recibiLikeDe") {
        fotoEnRoma.recibiLikeDe(ruben).shouldBeTrue()
      }
      it("no recibi like de ruben") {
        videoEnComicon.recibiLikeDe(ruben).shouldBeFalse()
      }
      it("cantidad de publicaciones") {
        cristian.cantidadPublicaciones().shouldBe(11)
      }
      it("likesRecibidosDe") {
        cristian.likesRecibidosDe(ruben).shouldBe(9)
      }
      it("Ruben no stalkea a Cristian") {
        ruben.stalkeaA(cristian).shouldBeFalse()
      }
      it("Ruben stalkea a cristian") {
        fotoEnGlaciar.darLike(ruben)
        ruben.stalkeaA(cristian).shouldBeTrue()
      }
    }
  }
})
