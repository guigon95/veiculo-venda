package com.guigon95.veiculo_venda.infra.controller.dto

import com.guigon95.veiculo_venda.domain.model.Veiculo
import java.math.BigDecimal

data class VeiculoResponse(
    val id: Long?,
    val placa: String?,
    val marca: String?,
    val modelo: String?,
    val ano: Int?,
    val cor: String?,
    val preco: BigDecimal?,
    val situacao: String?
) {

    fun toVeiculo(): Veiculo {
        return Veiculo(
            id = this@VeiculoResponse.id,
            placa = this@VeiculoResponse.placa,
            marca = this@VeiculoResponse.marca,
            modelo = this@VeiculoResponse.modelo,
            ano = this@VeiculoResponse.ano,
            cor = this@VeiculoResponse.cor,
            preco = this@VeiculoResponse.preco,
            situacao = this@VeiculoResponse.situacao
        )
    }
}
