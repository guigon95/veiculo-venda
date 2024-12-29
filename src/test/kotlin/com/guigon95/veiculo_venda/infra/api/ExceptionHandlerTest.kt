package com.guigon95.veiculo_venda.infra.api

import com.guigon95.veiculo_venda.application.usecase.exception.VendaVeiculoException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest

class ExceptionHandlerTest {

    private val exceptionHandler = ExceptionHandler()

    @Test
    fun handleVendaVeiculoException_ReturnsBadRequestWithMessage() {
        val exception = VendaVeiculoException("Error message")
        val request = mock(WebRequest::class.java)

        val response = exceptionHandler.handleVendaVeiculoException(exception, request)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body).isEqualTo("Error message")
    }

    @Test
    fun handleVendaVeiculoException_ReturnsBadRequestWithNullMessage() {
        val exception = VendaVeiculoException("Test")
        val request = mock(WebRequest::class.java)

        val response = exceptionHandler.handleVendaVeiculoException(exception, request)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body).isEqualTo("Test")
    }
}