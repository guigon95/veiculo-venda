package com.guigon95.veiculo_venda.infra.controller.dto

import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import java.math.BigDecimal
import java.util.*

class VendaVeiculoResponse(
    val id: Long?,
    val idVeiculo: Long,
    val codigoPagamento: UUID?,
    val valor: BigDecimal,
    val status: StatusPagamento
) {
    companion object {
        fun from(vendaVeiculo: VendaVeiculo): VendaVeiculoResponse {
            return VendaVeiculoResponse(
                id = vendaVeiculo.id,
                idVeiculo = vendaVeiculo.idVeiculo,
                codigoPagamento = vendaVeiculo.codigoPagamento,
                valor = vendaVeiculo.valor,
                status = vendaVeiculo.status
            )
        }
    }
}