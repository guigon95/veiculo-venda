package com.guigon95.veiculo_venda.infra.client

import com.guigon95.veiculo_venda.infra.controller.dto.VeiculoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "VeiculoService", url = "http://localhost:8081/")
interface VeiculoFeign {

    @GetMapping("/veiculos/{id}")
    fun getById(@PathVariable("id") id: Long): VeiculoResponse?

    @PostMapping("veiculos/vender/{id}")
    fun updateSituacao(@PathVariable("id") id: Long): VeiculoResponse?

    @PostMapping("veiculos/reservar/{id}")
    fun reservarVeiculo(@PathVariable("id") id: Long): VeiculoResponse?
}