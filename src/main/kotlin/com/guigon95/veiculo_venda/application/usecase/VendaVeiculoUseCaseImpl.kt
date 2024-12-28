package com.guigon95.veiculo_venda.application.usecase

import com.guigon95.veiculo_venda.application.VendaVeiculoException
import com.guigon95.veiculo_venda.application.gateway.IVeiculoClient
import com.guigon95.veiculo_venda.application.gateway.IVendaVeiculoRepository
import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.domain.usecase.VendaVeiculoUseCase
import java.util.*

class VendaVeiculoUseCaseImpl(
    private val iVendaVeiculoRepository: IVendaVeiculoRepository,
    private val veiculoFeignClient: IVeiculoClient
): VendaVeiculoUseCase {
    override fun salvar(vendaVeiculo: VendaVeiculo): VendaVeiculo {
        val veiculo = veiculoFeignClient.getById(vendaVeiculo.idVeiculo)

        if(veiculo?.situacao == "VENDIDO") {
            throw VendaVeiculoException("Veiculo já foi vendido")
        }

        if (vendaVeiculo.codigoPagamento == null)
            vendaVeiculo.codigoPagamento = iVendaVeiculoRepository.gerarCodigoPagamento()

        return iVendaVeiculoRepository.salvar(vendaVeiculo)
    }

    override fun gerarCodigoPagamento(): UUID {
        return iVendaVeiculoRepository.gerarCodigoPagamento()
    }

    override fun processaPagamento(codigoPagamento: UUID): VendaVeiculo {
        val vendaVeiculo = iVendaVeiculoRepository.findByCodigoPagamento(codigoPagamento)
        return iVendaVeiculoRepository.salvar(vendaVeiculo.apply {
            status = StatusPagamento.FINALIZADO
        })
    }
}