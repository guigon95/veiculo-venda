package com.guigon95.veiculo_venda.domain.model

import java.math.BigDecimal

data class Veiculo(
    var id: Long?,
    var placa: String?,
    var marca: String?,
    var modelo: String?,
    var ano: Int?,
    var cor: String?,
    var preco: BigDecimal?,
    var situacao: String?
)