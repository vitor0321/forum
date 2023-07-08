package br.com.alura.forum.exception

import org.springframework.security.core.AuthenticationException

class UserNotFoundException(message: String) : AuthenticationException(message)
