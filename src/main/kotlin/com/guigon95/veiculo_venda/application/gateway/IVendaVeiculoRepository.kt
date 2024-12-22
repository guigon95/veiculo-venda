package com.guigon95.veiculo_venda.application.gateway

import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import java.util.UUID

interface IVendaVeiculoRepository {
    fun salvar(vendaVeiculo: VendaVeiculo): VendaVeiculo
    fun gerarCodigoPagamento(): UUID
}