package br.com.alura.forum.repository

import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository : JpaRepository<Topico, Long> {

    // seguindo essa nomenclatura o JPA sabe lidar com a pesquisa, Curso(nome: String)
    fun findByCursoNome(
        nomeCurso: String,
        paginacao: Pageable,
    ): Page<Topico>
}