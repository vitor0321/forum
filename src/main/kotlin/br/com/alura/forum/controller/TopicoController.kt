package br.com.alura.forum.controller

import br.com.alura.forum.dto.input.AtualizacaoTopicoForm
import br.com.alura.forum.dto.input.TopicoForm
import br.com.alura.forum.dto.output.TopicoPorCategoriaView
import br.com.alura.forum.dto.output.TopicoView
import br.com.alura.forum.service.TopicoService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val topicoService: TopicoService,
) {

    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 10, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable,
    ): Page<TopicoView> =
        topicoService.listar(nomeCurso = nomeCurso, paginacao = paginacao)

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid topicoForm: TopicoForm,
        uriBuilder: UriComponentsBuilder,
    ): ResponseEntity<TopicoView> {
        val topicoView = topicoService.cadastrar(topicoDto = topicoForm)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @GetMapping("/{id}")
    @Cacheable("topicos")
    fun buscarPorId(@PathVariable id: Long): TopicoView =
        topicoService.buscarPorId(id = id)

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid topicoForm: AtualizacaoTopicoForm): ResponseEntity<TopicoView> =
        ResponseEntity.ok(topicoService.atualizar(topicoForm = topicoForm))


    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long) =
        topicoService.deletar(id = id)

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaView> =
        topicoService.relatorio()
}