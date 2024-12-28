package com.guigon95.veiculo_venda.config

import com.guigon95.veiculo_venda.application.gateway.IVeiculoClient
import com.guigon95.veiculo_venda.application.gateway.IVendaVeiculoRepository
import com.guigon95.veiculo_venda.application.usecase.VendaVeiculoUseCaseImpl
import com.guigon95.veiculo_venda.domain.usecase.VendaVeiculoUseCase
import com.guigon95.veiculo_venda.infra.client.VeiculoFeign
import com.guigon95.veiculo_venda.infra.gateway.VeiculoFeignClient
import com.guigon95.veiculo_venda.infra.gateway.VendaVeiculoMapper
import com.guigon95.veiculo_venda.infra.gateway.VendaVeiculoRepositoryJpa
import com.guigon95.veiculo_venda.infra.persistence.VendaVeiculoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean
    fun vendaVeiculoMapper(): VendaVeiculoMapper {
        return VendaVeiculoMapper()
    }

    @Bean
    fun veiculoRepositorioJpa(
        vendaVeiculoRepository: VendaVeiculoRepository,
        vendaVeiculoMapper: VendaVeiculoMapper
    ): VendaVeiculoRepositoryJpa {
        return VendaVeiculoRepositoryJpa(
            vendaVeiculoRepository,
            vendaVeiculoMapper
        )
    }

    @Bean
    fun veiculoUseCase(
        iVendaVeiculoRepository: IVendaVeiculoRepository,
        iVeiculoClient: IVeiculoClient
    ): VendaVeiculoUseCase {
        return VendaVeiculoUseCaseImpl(
            iVendaVeiculoRepository,
            iVeiculoClient
        )
    }

    @Bean
    fun veiculoFeign(
        veiculoFeign: VeiculoFeign
    ): IVeiculoClient {
        return VeiculoFeignClient(veiculoFeign)
    }
}