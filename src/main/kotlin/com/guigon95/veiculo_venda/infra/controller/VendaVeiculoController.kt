package com.guigon95.veiculo_venda.infra.controller

import com.guigon95.veiculo_venda.domain.usecase.VendaVeiculoUseCase
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoRequest
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoResponse
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class VendaVeiculoController(
    private val vendaVeiculoUseCase: VendaVeiculoUseCase
) {
    fun salvar(vendaVeiculoRequest: VendaVeiculoRequest): VendaVeiculoResponse {
        val vendaVeiculo = vendaVeiculoUseCase.salvar(vendaVeiculoRequest.toVendaVeiculo())
        return VendaVeiculoResponse.from(vendaVeiculo)
    }

    fun processaPagamento(codigoPagamento: String): Any {
        return vendaVeiculoUseCase.processaPagamento(UUID.fromString(codigoPagamento))
    }

}
