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



  fun likesEnTotal() =
    publicaciones.sumBy { p -> p.cantidadLikes() }


  fun elAmigoMasPopular() = amigos.maxByOrNull { it.likesEnTotal() }

  fun stalkeaA(usuario: Usuario) = usuario.likesRecibidosDe(this) >= usuario.cantidadPublicaciones() * 0.9
  fun cantidadPublicaciones() = publicaciones.size
  fun likesRecibidosDe(usuario: Usuario): Int {
    var likes = 0
    for (p in publicaciones) {
      if (p.recibiLikeDe(usuario)) {
        likes += 1
      }
    }
    return likes
  }
// otro modo de stalkeo alternativo by Cesar

//fun cantidadDeMeGustasA(usuario: Usuario) = usuario.publicaciones.filter { p -> p.recibiLikeDe(this) }.size

//fun stalkeaA(usuario: Usuario) = this.cantidadDeMeGustasA(usuario)  >= usuario.publicaciones.size * 0.9
}




