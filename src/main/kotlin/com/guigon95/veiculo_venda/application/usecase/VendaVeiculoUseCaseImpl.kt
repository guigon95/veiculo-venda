package com.guigon95.veiculo_venda.application.usecase

import com.guigon95.veiculo_venda.application.usecase.exception.VendaVeiculoException
import com.guigon95.veiculo_venda.application.gateway.IVeiculoClient
import com.guigon95.veiculo_venda.application.gateway.IVendaVeiculoRepository
import com.guigon95.veiculo_venda.application.usecase.exception.VeiculoNotFoundException
import com.guigon95.veiculo_venda.application.usecase.exception.VeiculoVendaException
import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.domain.usecase.VendaVeiculoUseCase
import java.util.*

class VendaVeiculoUseCaseImpl(
    private val iVendaVeiculoRepository: IVendaVeiculoRepository,
    private val veiculoFeignClient: IVeiculoClient
): VendaVeiculoUseCase {
    override fun salvar(vendaVeiculo: VendaVeiculo): VendaVeiculo {
        iVendaVeiculoRepository.findByIdVeiculo(vendaVeiculo.idVeiculo)?.let {
            throw VeiculoVendaException("Veiculo ja esta em processo de venda")
        }

        val veiculo = veiculoFeignClient.getById(vendaVeiculo.idVeiculo)
            ?: throw VeiculoNotFoundException("Veiculo nao encontrado")

        if(veiculo.situacao == "VENDIDO") {
            throw VendaVeiculoException("Veiculo já foi vendido")
        }

        if(vendaVeiculo.valor != veiculo.preco)
            throw VendaVeiculoException("Valor do pagamento diferente do veiculo")

        vendaVeiculo.codigoPagamento = iVendaVeiculoRepository.gerarCodigoPagamento()

        veiculoFeignClient.reservarVeiculo(vendaVeiculo.idVeiculo)

        return iVendaVeiculoRepository.salvar(vendaVeiculo)
    }

    override fun gerarCodigoPagamento(): UUID {
        return iVendaVeiculoRepository.gerarCodigoPagamento()
    }

    override fun processaPagamento(codigoPagamento: UUID): VendaVeiculo {
        val vendaVeiculo = iVendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)
        veiculoFeignClient.updateSituacaoVeiculo(vendaVeiculo.idVeiculo)
        return iVendaVeiculoRepository.salvar(vendaVeiculo.apply {
            status = StatusPagamento.FINALIZADO
        })
    }
}