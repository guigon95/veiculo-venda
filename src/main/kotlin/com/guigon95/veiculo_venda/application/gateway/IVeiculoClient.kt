package com.guigon95.veiculo_venda.application.gateway

import com.guigon95.veiculo_venda.domain.model.Veiculo

interface IVeiculoClient {
    fun getById(id: Long): Veiculo?

    fun updateSituacaoVeiculo(id: Long): Veiculo?
    fun reservarVeiculo(idVeiculo: Long): Veiculo?
}