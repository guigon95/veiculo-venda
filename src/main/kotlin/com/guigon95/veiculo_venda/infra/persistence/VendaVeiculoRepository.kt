package com.guigon95.veiculo_venda.infra.persistence

import com.guigon95.veiculo_venda.infra.persistence.entity.VendaVeiculoEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VendaVeiculoRepository : JpaRepository<VendaVeiculoEntity, Long> {
    fun findByCodigoPagamento(codigoPagamento: UUID): VendaVeiculoEntity?
    fun findByIdVeiculo(idVeiculo: Long): VendaVeiculoEntity?
}