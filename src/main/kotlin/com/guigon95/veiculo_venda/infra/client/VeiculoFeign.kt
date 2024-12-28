package com.guigon95.veiculo_venda.infra.client

import com.guigon95.veiculo_venda.infra.controller.dto.VeiculoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "VeiculoService", url = "http://localhost:8080/")
interface VeiculoFeign {

    @GetMapping("/veiculos/{id}")
    fun getById(@PathVariable("id") id: Long): VeiculoResponse?

}