package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private var topicos: List<Topico>,
) {

    init {
        val topico = Topico(
            id = 2,
            titulo = "Duvida Kotlin",
            mensagem = "variaveis no kotlin",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programacao",
            ),
            autor = Usuario(
                id = 1,
                nome = "Ana da Silva",
                email = "ana@gamil.com",
            )
        )
        topicos = listOf(topico, topico, topico, topico)
    }

    fun listar(): List<Topico> = topicos

    fun buscarPorId(id: Long): Topico {
        return topicos.stream().filter { topic ->
            topic.id == id
        }.findFirst().get()
    }
}