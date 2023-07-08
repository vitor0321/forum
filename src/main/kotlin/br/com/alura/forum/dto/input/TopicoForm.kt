package br.com.alura.forum.dto.input

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class TopicoForm(
    @field:NotEmpty(message = " Titulo não pode ser em branco")
    @field:Size(min = 5, max = 100, message = " Titulo deve ter entre 5 e 100 caracteres")
    val titulo: String,
    @field:NotEmpty(message = " Mensagem não pode ser em branco")
    val mensagem: String,
    @field:NotNull
    val idCurso: Long,
    @field:NotNull
    val idAutor: Long,
)