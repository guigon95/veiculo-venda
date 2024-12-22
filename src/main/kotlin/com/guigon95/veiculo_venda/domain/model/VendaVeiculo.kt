package com.guigon95.veiculo_venda.domain.model

import com.guigon95.veiculo_venda.domain.enum.StatusPagamento
import java.math.BigDecimal
import java.util.UUID

data class VendaVeiculo(
    val id: Long?,
    val idVeiculo: Long,
    var codigoPagamento: UUID?,
    val valor: BigDecimal,
    val staus: StatusPagamento
) {
}