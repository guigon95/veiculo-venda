package com.guigon95.veiculo_venda.infra.controller.dto

import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import java.math.BigDecimal

class VendaVeiculoRequest(
    val id: Long?,
    val idVeiculo: Long,
    val valor: BigDecimal
) {
    fun toVendaVeiculo() : VendaVeiculo {
        return VendaVeiculo(
            id = null,
            idVeiculo = idVeiculo,
            codigoPagamento = null,
            valor = valor,
            status = StatusPagamento.PROCESSANDO
        )
    }
}