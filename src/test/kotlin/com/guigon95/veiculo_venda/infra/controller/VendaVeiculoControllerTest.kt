import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.domain.usecase.VendaVeiculoUseCase
import com.guigon95.veiculo_venda.infra.controller.VendaVeiculoController
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoRequest
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class VendaVeiculoControllerTest {

    private val vendaVeiculoUseCase = mock(VendaVeiculoUseCase::class.java)
    private val vendaVeiculoController = VendaVeiculoController(vendaVeiculoUseCase)

    @Test
    fun salvar_ReturnsVendaVeiculoResponse() {
        val request = VendaVeiculoRequest(1, BigDecimal(100))
        val vendaVeiculo = VendaVeiculo(1, 1, UUID.randomUUID(), BigDecimal(100), StatusPagamento.PROCESSANDO)
        val response = VendaVeiculoResponse.from(vendaVeiculo)
        `when`(vendaVeiculoUseCase.salvar(request.toVendaVeiculo())).thenReturn(vendaVeiculo)

        val result = vendaVeiculoController.salvar(request)

        assertThat(result).isNotNull
        assertThat(result.id).isEqualTo(response.id)
        assertThat(result.idVeiculo).isEqualTo(response.idVeiculo)
        assertThat(result.codigoPagamento).isEqualTo(response.codigoPagamento)
        assertThat(result.valor).isEqualTo(response.valor)
        assertThat(result.status).isEqualTo(response.status)
    }

    @Test
    fun salvar_ThrowsExceptionForInvalidRequest() {
        val request = VendaVeiculoRequest(1, BigDecimal(-100))
        `when`(vendaVeiculoUseCase.salvar(request.toVendaVeiculo())).thenThrow(IllegalArgumentException::class.java)

        assertThrows(IllegalArgumentException::class.java) {
            vendaVeiculoController.salvar(request)
        }
    }

    @Test
    fun processaPagamento_ReturnsExpectedResponse() {
        val codigoPagamento = UUID.randomUUID().toString()
        val response = VendaVeiculo(1, 1, UUID.fromString(codigoPagamento), BigDecimal(100), StatusPagamento.PROCESSANDO)
        `when`(vendaVeiculoUseCase.processaPagamento(UUID.fromString(codigoPagamento))).thenReturn(response)

        val result = vendaVeiculoController.processaPagamento(codigoPagamento)

        assertEquals(response, result)
    }

    @Test
    fun processaPagamento_ThrowsExceptionForInvalidCodigoPagamento() {
        val codigoPagamento = UUID.randomUUID().toString()
        `when`(vendaVeiculoUseCase.processaPagamento(UUID.fromString(codigoPagamento))).thenThrow(NoSuchElementException::class.java)

        assertThrows(NoSuchElementException::class.java) {
            vendaVeiculoController.processaPagamento(codigoPagamento)
        }
    }
}