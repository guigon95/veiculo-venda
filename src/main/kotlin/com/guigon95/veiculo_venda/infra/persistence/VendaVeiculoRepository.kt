package com.guigon95.veiculo_venda.infra.persistence

import com.guigon95.veiculo_venda.infra.persistence.entity.VendaVeiculoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VendaVeiculoRepository : JpaRepository<VendaVeiculoEntity, Long> {
}