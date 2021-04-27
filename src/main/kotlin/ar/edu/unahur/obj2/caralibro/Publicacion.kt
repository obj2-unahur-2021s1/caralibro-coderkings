package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  val likes = mutableListOf<Usuario>()
  val listaPermitidos = mutableListOf<Usuario>()
  val listaExcluidos = mutableListOf<Usuario>()
  var propietario = mutableListOf<Usuario>()
  var permiso: String? = null


  abstract fun espacioQueOcupa(): Int
  fun darLike(usuario: Usuario) {//Los usuarios que pueden ver una publicación pueden indicar que esa publicación les gusta
    if (!likes.contains(usuario)) {
      likes.add(usuario)
    }
  }
  fun cantidadLikes() = likes.size
  fun agregarPropietario(usuario: Usuario) {
    propietario.add(usuario)
  }
  fun cambiarPermiso(texto:String) {
    permiso = texto
  }
  fun propietario() = propietario.first()
  fun estaPermitido(usuario: Usuario) :Boolean {
    if (usuario == this.propietario() || permiso == "publico") {
      return true
    } else if (permiso == "publico con lista de excluidos") {
      if (!listaExcluidos.contains(usuario)) {
        return true
      }
    } else if (permiso == "privado con lista de permitidos") {
      if (listaPermitidos.contains(usuario)) {
        return true
      }
    } else if (permiso == "solo amigos") {
      if (this.propietario().tieneAmigo(usuario)) {
        return true
      }
    }
    return false
  }

  fun agregarPermitido(usuario:Usuario) {
    listaPermitidos.add(usuario)
  }
  fun agregarExcluido(usuario: Usuario) {
    listaExcluidos.add(usuario)
  }
  fun recibiLikeDe(usuario: Usuario) = likes.contains(usuario)

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
