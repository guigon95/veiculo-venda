import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.infra.gateway.VendaVeiculoMapper
import com.guigon95.veiculo_venda.infra.gateway.VendaVeiculoRepositoryJpa
import com.guigon95.veiculo_venda.infra.persistence.VendaVeiculoRepository
import com.guigon95.veiculo_venda.infra.persistence.entity.VendaVeiculoEntity
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class VendaVeiculoRepositoryJpaTest {

    private val vendaVeiculoRepository = mock(VendaVeiculoRepository::class.java)
    private val mapper = mock(VendaVeiculoMapper::class.java)
    private val vendaVeiculoRepositoryJpa = VendaVeiculoRepositoryJpa(vendaVeiculoRepository, mapper)

    @Test
    fun salvar_SuccessfullySavesVendaVeiculo() {
        val codigoPagamento = UUID.randomUUID()
        val vendaVeiculo = VendaVeiculo(1, 1, codigoPagamento, BigDecimal(1000), StatusPagamento.PROCESSANDO)
        val entity = VendaVeiculoEntity(1, 1, codigoPagamento, BigDecimal(1000), StatusPagamento.PROCESSANDO)
            `when`(mapper.toEntity(vendaVeiculo)).thenReturn(entity)
        `when`(vendaVeiculoRepository.save(entity)).thenReturn(entity)
        `when`(mapper.toVendaVeiculo(entity)).thenReturn(vendaVeiculo)

        val result = vendaVeiculoRepositoryJpa.salvar(vendaVeiculo)

        assertThat(result).isEqualTo(vendaVeiculo)
    }

    @Test
    fun gerarCodigoPagamento_ReturnsNewUUID() {
        val result = vendaVeiculoRepositoryJpa.gerarCodigoPagamento()

        assertThat(result).isNotNull
        assertThat(result).isInstanceOf(UUID::class.java)
    }

    @Test
    fun findByCodigoPagamento_ReturnsVendaVeiculo() {
        val codigoPagamento = UUID.randomUUID()
        val entity = VendaVeiculoEntity(1, 1, codigoPagamento, BigDecimal(1000), StatusPagamento.PROCESSANDO)
        val vendaVeiculo = VendaVeiculo(1, 1, codigoPagamento, BigDecimal(1000), StatusPagamento.PROCESSANDO)
        `when`(vendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)).thenReturn(entity)
        `when`(mapper.toVendaVeiculo(entity)).thenReturn(vendaVeiculo)

        val result = vendaVeiculoRepositoryJpa.findByCodigoPagamento(codigoPagamento)

        assertThat(result).isEqualTo(vendaVeiculo)
    }

    @Test
    fun findByCodigoPagamento_ThrowsExceptionWhenNotFound() {
        val codigoPagamento = UUID.randomUUID()
        `when`(vendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)).thenReturn(null)

        assertThatThrownBy {
            vendaVeiculoRepositoryJpa.findByCodigoPagamento(codigoPagamento)
        }.isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun findByIdVeiculo_ReturnsVendaVeiculoWhenFound() {
        val idVeiculo = 1L
        val entity = VendaVeiculoEntity(1, 1, UUID.randomUUID(), BigDecimal(1000), StatusPagamento.PROCESSANDO)
        val vendaVeiculo = VendaVeiculo(1, 1, entity.codigoPagamento, BigDecimal(1000), StatusPagamento.PROCESSANDO)
        `when`(vendaVeiculoRepository.findByIdVeiculo(idVeiculo)).thenReturn(entity)
        `when`(mapper.toVendaVeiculo(entity)).thenReturn(vendaVeiculo)

        val result = vendaVeiculoRepositoryJpa.findByIdVeiculo(idVeiculo)

        assertThat(result).isEqualTo(vendaVeiculo)
    }

    @Test
    fun findByIdVeiculo_ReturnsNullWhenNotFound() {
        val idVeiculo = 1L
        `when`(vendaVeiculoRepository.findByIdVeiculo(idVeiculo)).thenReturn(null)

        val result = vendaVeiculoRepositoryJpa.findByIdVeiculo(idVeiculo)

        assertThat(result).isNull()
    }
}