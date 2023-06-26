package br.com.alura.forum.service

import br.com.alura.forum.dto.input.AtualizacaoTopicoForm
import br.com.alura.forum.dto.input.TopicoForm
import br.com.alura.forum.dto.output.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private var topicosService: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
) {

    fun listar(): List<TopicoView> =
        topicosService.map { topico -> topicoViewMapper.map(topico) }

    fun buscarPorId(id: Long): TopicoView = topicoViewMapper.map(getTopicoById(id))

    fun cadastrar(topicoDto: TopicoForm): TopicoView {
        val topico = topicoFormMapper.map(topicoDto)
        topico.id = topicosService.size.toLong() + 1
        topicosService = topicosService.plus(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(topicoForm: AtualizacaoTopicoForm): TopicoView {
        val topico = getTopicoById(topicoForm.id)
        val topicoAtualizado = Topico(
            id = topicoForm.id,
            titulo = topicoForm.titulo,
            mensagem = topicoForm.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
        topicosService = topicosService.minus(topico).plus(topicoAtualizado)
        return topicoViewMapper.map(topicoAtualizado)
    }

    fun deletar(id: Long) {
        val topico = getTopicoById(id)
        topicosService = topicosService.filterNot { it == topico }
    }

    private fun getTopicoById(id: Long) =
        topicosService.firstOrNull { topic -> topic.id == id }
            ?: throw NotFoundException("Topico não encontrado, com esse ID: $id")
}