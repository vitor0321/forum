package br.com.alura.forum.controller

import br.com.alura.forum.dto.input.AtualizacaoTopicoForm
import br.com.alura.forum.dto.input.TopicoForm
import br.com.alura.forum.dto.output.TopicoView
import br.com.alura.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val topicoService: TopicoService,
) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 10) paginacao: Pageable,
    ): Page<TopicoView> =
        topicoService.listar(nomeCurso = nomeCurso, paginacao = paginacao)

    @PostMapping
    @Transactional
    fun cadastrar(
        @RequestBody @Valid topicoForm: TopicoForm,
        uriBuilder: UriComponentsBuilder,
    ): ResponseEntity<TopicoView> {
        val topicoView = topicoService.cadastrar(topicoDto = topicoForm)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView =
        topicoService.buscarPorId(id = id)

    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid topicoForm: AtualizacaoTopicoForm): ResponseEntity<TopicoView> =
        ResponseEntity.ok(topicoService.atualizar(topicoForm = topicoForm))


    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) =
        topicoService.deletar(id = id)
}