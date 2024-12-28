package com.guigon95.veiculo_venda.domain.usecase

import com.guigon95.veiculo_venda.domain.model.VendaVeiculo
import java.util.UUID

interface VendaVeiculoUseCase {
    fun salvar(vendaVeiculo: VendaVeiculo): VendaVeiculo
    fun gerarCodigoPagamento(): UUID
    fun processaPagamento(codigoPagamento: UUID): VendaVeiculo
}