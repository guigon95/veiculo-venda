package com.guigon95.veiculo_venda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class VeiculoVendaApplication

fun main(args: Array<String>) {
	runApplication<VeiculoVendaApplication>(*args)
}
