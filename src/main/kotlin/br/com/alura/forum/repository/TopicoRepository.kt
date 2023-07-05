package br.com.alura.forum.repository

import br.com.alura.forum.dto.output.TopicoPorCategoriaView
import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository : JpaRepository<Topico, Long> {

    // seguindo essa nomenclatura o JPA sabe lidar com a pesquisa, Curso(nome: String)
    fun findByCursoNome(
        nomeCurso: String,
        paginacao: Pageable,
    ): Page<Topico>

    @Query("SELECT new br.com.alura.forum.dto.output.TopicoPorCategoriaView(curso.categoria, count(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoriaView>
}