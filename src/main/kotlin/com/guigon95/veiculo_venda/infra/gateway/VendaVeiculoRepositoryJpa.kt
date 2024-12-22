package com.guigon95.veiculo_venda.infra.gateway

import com.guigon95.veiculo_venda.application.gateway.IVendaVeiculoRepository
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.infra.persistence.VendaVeiculoRepository
import java.util.*

class VendaVeiculoRepositoryJpa(
    private val vendaVeiculoRepository: VendaVeiculoRepository,
    private val mapper: VendaVeiculoMapper
) : IVendaVeiculoRepository {
    override fun salvar(vendaVeiculo: VendaVeiculo): VendaVeiculo {
        val entity = mapper.toEntity(vendaVeiculo)
        return mapper.toVendaVeiculo(vendaVeiculoRepository.save(entity))
    }

    override fun gerarCodigoPagamento(): UUID {
        return UUID.randomUUID()
    }
}