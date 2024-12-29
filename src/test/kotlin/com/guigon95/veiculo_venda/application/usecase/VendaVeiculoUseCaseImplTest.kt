package com.guigon95.veiculo_venda.application.usecase

import com.guigon95.veiculo_venda.application.gateway.IVeiculoClient
import com.guigon95.veiculo_venda.application.gateway.IVendaVeiculoRepository
import com.guigon95.veiculo_venda.application.usecase.exception.VendaVeiculoException
import com.guigon95.veiculo_venda.application.usecase.exception.VeiculoNotFoundException
import com.guigon95.veiculo_venda.application.usecase.exception.VeiculoVendaException
import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.Veiculo
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class VendaVeiculoUseCaseImplTest {

    private val iVendaVeiculoRepository = mock(IVendaVeiculoRepository::class.java)
    private val veiculoFeignClient = mock(IVeiculoClient::class.java)
    private val vendaVeiculoUseCase = VendaVeiculoUseCaseImpl(iVendaVeiculoRepository, veiculoFeignClient)

    @Test
    fun salvar_SuccessfullySavesVendaVeiculo() {
        val uuid = UUID.randomUUID()
        val vendaVeiculo = VendaVeiculo(1, 1, uuid, BigDecimal(100), StatusPagamento.PROCESSANDO)
        val veiculo = Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "A_VENDA")

        `when`(iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)).thenReturn(null)
        `when`(veiculoFeignClient.getById(vendaVeiculo.idVeiculo)).thenReturn(veiculo)
        `when`(iVendaVeiculoRepository.salvar(vendaVeiculo)).thenReturn(vendaVeiculo)

        val result = vendaVeiculoUseCase.salvar(vendaVeiculo)

        assertEquals(vendaVeiculo, result)
    }

    @Test
    fun salvar_ThrowsVeiculoVendaExceptionWhenVeiculoAlreadyInProcess() {
        val vendaVeiculo = VendaVeiculo(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)
        `when`(iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)).thenReturn(vendaVeiculo)

        assertThrows(VeiculoVendaException::class.java) {
            vendaVeiculoUseCase.salvar(vendaVeiculo)
        }
    }

    @Test
    fun salvar_ThrowsVeiculoNotFoundExceptionWhenVeiculoNotFound() {
        val vendaVeiculo = VendaVeiculo(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)
        `when`(iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)).thenReturn(null)
        `when`(veiculoFeignClient.getById(vendaVeiculo.idVeiculo)).thenReturn(null)

        assertThrows(VeiculoNotFoundException::class.java) {
            vendaVeiculoUseCase.salvar(vendaVeiculo)
        }
    }

    @Test
    fun salvar_ThrowsVendaVeiculoExceptionWhenVeiculoAlreadySold() {
        val vendaVeiculo = VendaVeiculo(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)
        val veiculo = Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "VENDIDO")
            `when`(iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)).thenReturn(null)
        `when`(veiculoFeignClient.getById(vendaVeiculo.idVeiculo)).thenReturn(veiculo)

        assertThrows(VendaVeiculoException::class.java) {
            vendaVeiculoUseCase.salvar(vendaVeiculo)
        }
    }

    @Test
    fun salvar_ThrowsVendaVeiculoExceptionWhenPaymentValueDiffers() {
        val vendaVeiculo = VendaVeiculo(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)
        val veiculo = Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(200), "A_VENDA")
            `when`(iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)).thenReturn(null)
        `when`(veiculoFeignClient.getById(vendaVeiculo.idVeiculo)).thenReturn(veiculo)

        assertThrows(VendaVeiculoException::class.java) {
            vendaVeiculoUseCase.salvar(vendaVeiculo)
        }
    }

    @Test
    fun processaPagamento_SuccessfullyProcessesPayment() {
        val codigoPagamento = UUID.randomUUID()
        val vendaVeiculo = VendaVeiculo(1, 1, codigoPagamento, BigDecimal(100), StatusPagamento.PROCESSANDO)
        `when`(iVendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)).thenReturn(vendaVeiculo)
        `when`(veiculoFeignClient.updateSituacaoVeiculo(vendaVeiculo.idVeiculo)).thenReturn(Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "VENDIDO"))
        `when`(iVendaVeiculoRepository.salvar(vendaVeiculo)).thenReturn(vendaVeiculo)

        val result = vendaVeiculoUseCase.processaPagamento(codigoPagamento)

        assertThat(result.status).isEqualTo(StatusPagamento.FINALIZADO)
        assertThat(result).isEqualTo(vendaVeiculo)
        assertThat(result.idVeiculo).isEqualTo(vendaVeiculo.idVeiculo)
        assertThat(result.codigoPagamento).isEqualTo(vendaVeiculo.codigoPagamento)
        assertThat(result.valor).isEqualTo(vendaVeiculo.valor)
        assertThat(result.status).isEqualTo(vendaVeiculo.status)
        assertThat(result.id).isEqualTo(vendaVeiculo.id)

    }

    @Test
    fun processaPagamento_ThrowsExceptionWhenCodigoPagamentoNotFound() {
        val codigoPagamento = UUID.randomUUID()
        `when`(iVendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)).thenThrow(VendaVeiculoException::class.java)

        assertThrows(VendaVeiculoException::class.java) {
            vendaVeiculoUseCase.processaPagamento(codigoPagamento)
        }
    }

    @Test
    fun gerarCodigoPagamento_ReturnsNewUUID() {
        val codigoPagamento = UUID.randomUUID()
        `when`(iVendaVeiculoRepository.gerarCodigoPagamento()).thenReturn(codigoPagamento)
        val result = vendaVeiculoUseCase.gerarCodigoPagamento()

        assertThat(result).isNotNull
        assertThat(result).isEqualTo(codigoPagamento)
    }
}