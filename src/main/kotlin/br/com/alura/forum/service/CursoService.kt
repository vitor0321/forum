package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.repository.CursoRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import java.util.Optional

@Service
@RequestMapping("/curso")
class CursoService(
    private val repository: CursoRepository,
) {

    fun buscarPorId(id: Long): Optional<Curso> {
        return repository.findById(id)
    }
}
