package br.com.alura.forum.service

import br.com.alura.forum.dto.input.AtualizacaoTopicoForm
import br.com.alura.forum.dto.input.TopicoForm
import br.com.alura.forum.dto.output.TopicoPorCategoriaView
import br.com.alura.forum.dto.output.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.repository.TopicoRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val entityManager: EntityManager,
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable,
    ): Page<TopicoView> {
        print(entityManager)
        return nomeCurso?.let {
            repository.findByCursoNome(nomeCurso = nomeCurso, paginacao = paginacao).map { topico ->
                topicoViewMapper.map(topico)
            }
        } ?: repository.findAll(paginacao).map { topico ->
            topicoViewMapper.map(topico)
        }
    }

    fun buscarPorId(id: Long): TopicoView =
        topicoViewMapper.map(getTopicoById(id))

    fun cadastrar(topicoDto: TopicoForm): TopicoView {
        val topico = topicoFormMapper.map(topicoDto)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(topicoForm: AtualizacaoTopicoForm): TopicoView {
        val topico = getTopicoById(topicoForm.id)
        topico.titulo = topicoForm.titulo
        topico.mensagem = topicoForm.mensagem
        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) =
        repository.deleteById(id)

    private fun getTopicoById(id: Long) =
        repository.findById(id).orElseThrow {
            NotFoundException("Topico não encontrado, com esse ID: $id")
        }

    fun relatorio(): List<TopicoPorCategoriaView> =
        repository.relatorio()
}