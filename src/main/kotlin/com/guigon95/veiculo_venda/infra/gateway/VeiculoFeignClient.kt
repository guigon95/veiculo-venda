package com.guigon95.veiculo_venda.infra.gateway

import com.guigon95.veiculo_venda.application.gateway.IVeiculoClient
import com.guigon95.veiculo_venda.domain.model.Veiculo
import com.guigon95.veiculo_venda.infra.client.VeiculoFeign

class VeiculoFeignClient(
    private val veiculoFeign: VeiculoFeign
): IVeiculoClient {
    override fun getById(id: Long): Veiculo? {
        veiculoFeign.getById(id)?.let {
            return it.toVeiculo()
        }

        return null
    }
}