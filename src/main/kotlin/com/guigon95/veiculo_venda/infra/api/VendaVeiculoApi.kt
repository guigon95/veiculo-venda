package com.guigon95.veiculo_venda.infra.api

import com.guigon95.veiculo_venda.infra.controller.VendaVeiculoController
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoRequest
import com.guigon95.veiculo_venda.infra.controller.dto.VendaVeiculoResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/venda")
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

}