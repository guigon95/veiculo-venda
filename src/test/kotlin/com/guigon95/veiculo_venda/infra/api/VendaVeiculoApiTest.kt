package com.guigon95.veiculo_venda.infra.api

import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.infra.controller.VendaVeiculoController
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoRequest
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoResponse
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*
import kotlin.NoSuchElementException

class VendaVeiculoApiTest {

    private val vendaVeiculoController = mock(VendaVeiculoController::class.java)
    private val vendaVeiculoApi = VendaVeiculoApi(vendaVeiculoController)

    @Test
    fun salvar_ReturnsCreatedStatusAndResponseBody() {
        val request = VendaVeiculoRequest(1, BigDecimal(100))
        val response = VendaVeiculoResponse(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)

        `when`(vendaVeiculoController.salvar(request)).thenReturn(response)

        val result = vendaVeiculoApi.salvar(request)

        verify(vendaVeiculoController, times(1)).salvar(request)
        assertThat(result).isNotNull
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body).isEqualTo(response)
    }

    @Test
    fun salvar_ReturnsBadRequestForInvalidRequest() {
        val request = VendaVeiculoRequest(1, BigDecimal(-100))

        `when`(vendaVeiculoController.salvar(request)).thenThrow(IllegalArgumentException::class.java)

        assertThatThrownBy({ vendaVeiculoApi.salvar(request) })
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun webHook_ReturnsOkStatusAndResponseBody() {
        val codigoPagamento = "validCodigo"
        val response = Any() // replace with actual response type if needed
         `when`(vendaVeiculoController.processaPagamento(codigoPagamento)).thenReturn(response)
        val result = vendaVeiculoApi.webHook(codigoPagamento)

        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isEqualTo(response)
    }

    @Test
    fun webHook_ReturnsNotFoundForInvalidCodigoPagamento() {
        val codigoPagamento = "invalidCodigo"
        `when`(vendaVeiculoController.processaPagamento(codigoPagamento)).thenThrow(NoSuchElementException::class.java)

        assertThatThrownBy({ vendaVeiculoApi.webHook(codigoPagamento) })
            .isInstanceOf(NoSuchElementException::class.java)
    }
}