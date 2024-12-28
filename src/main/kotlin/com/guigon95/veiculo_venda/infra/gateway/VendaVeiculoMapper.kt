package com.guigon95.veiculo_venda.infra.gateway

import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import com.guigon95.veiculo_venda.infra.persistence.entity.VendaVeiculoEntity

class VendaVeiculoMapper {
    fun toEntity(vendaVeiculo: VendaVeiculo): VendaVeiculoEntity {
        return VendaVeiculoEntity(
            vendaVeiculo.id,
            vendaVeiculo.idVeiculo,
            vendaVeiculo.codigoPagamento,
            vendaVeiculo.valor,
            vendaVeiculo.status
        )
    }

    fun toVendaVeiculo(vendaVeiculoEntity: VendaVeiculoEntity): VendaVeiculo {
        return VendaVeiculo(
            vendaVeiculoEntity.id,
            vendaVeiculoEntity.idVeiculo,
            vendaVeiculoEntity.codigoPagamento,
            vendaVeiculoEntity.valor,
            vendaVeiculoEntity.status
        )
    }
}