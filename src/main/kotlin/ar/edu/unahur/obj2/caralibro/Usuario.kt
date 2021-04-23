package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()


  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
    publicacion.agregarPropietario(this)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun agregarAmigo(amigo: Usuario) {
    amigos.add(amigo)
  }
  fun amigos() = amigos
  fun esMasAmistosoQue(usuario: Usuario) = amigos.size > usuario.amigos.size
  fun puedeVer(publicacion: Publicacion) = publicacion.estaPermitido(this)
  fun tieneAmigo(usuario: Usuario) = amigos.contains(usuario)
  fun puedeVerTodasMisPublicaciones(amigo: Usuario): Boolean {
    var counter = 0
    for (p in publicaciones) {
      if (amigo.puedeVer(p)) {
        counter += 1
      }
    }
    if (counter == publicaciones.size) {
      return true
    }
      return false
  }
  fun mejoresAmigos() = amigos.filter { a -> this.puedeVerTodasMisPublicaciones(a) }

}
