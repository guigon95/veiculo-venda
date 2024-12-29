package com.guigon95.veiculo_venda.infra.api

import com.guigon95.veiculo_venda.application.usecase.exception.VendaVeiculoException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler
    fun handleVendaVeiculoException(
        ex: VendaVeiculoException,
        request: WebRequest
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}