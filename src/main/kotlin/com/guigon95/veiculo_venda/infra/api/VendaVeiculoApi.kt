package com.guigon95.veiculo_venda.infra.api

import com.guigon95.veiculo_venda.infra.controller.VendaVeiculoController
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoRequest
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/venda")
@Tag(name = "Venda de Veiculos", description = "Acesso ao gerenciamento de venda de veiculos")
class VendaVeiculoApi(
    val vendaVeiculoController: VendaVeiculoController
) {

    @PostMapping
    fun salvar(
        @RequestBody vendaVeiculoRequest: VendaVeiculoRequest
    ): ResponseEntity<VendaVeiculoResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(vendaVeiculoController.salvar(vendaVeiculoRequest))
    }

    @PostMapping("/webhook/{codigoPagamento}")
    fun webHook(
        @PathVariable codigoPagamento: String
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(vendaVeiculoController.processaPagamento(codigoPagamento))
    }

}