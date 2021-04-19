package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  abstract fun espacioQueOcupa(): Int
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}


// opcion 1 = calidad SD
// opcion 2 = calidad 720HD
// opcion 3 = calidad 1080HD
class Video(val duracion: Int, var calidad: String) :Publicacion() {
  override fun espacioQueOcupa(): Int {
    if (calidad == "SD") {
      return duracion
    } else if (calidad == "720") {
 //   720p HD
      return duracion * 3
    } else {
 //  1080p HD
      return duracion * 6
    }
  }
}