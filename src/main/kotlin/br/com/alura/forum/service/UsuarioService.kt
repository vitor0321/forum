package br.com.alura.forum.service

import br.com.alura.forum.exception.UserNotFoundException
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UsuarioService(
    private val repository: UsuarioRepository
) : UserDetailsService {

    fun buscarPorId(id: Long): Optional<Usuario> {
        return repository.findById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw UserNotFoundException("Usuário não encontrado: $username")
        return UserDetail(usuario)
    }
}

