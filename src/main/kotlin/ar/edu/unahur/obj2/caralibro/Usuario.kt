package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun agregarAmigo(amigo: Usuario) {
    amigos.add(amigo)
  }
  fun esMasAmistosoQue(usuario: Usuario) = amigos.size > usuario.amigos.size

}
