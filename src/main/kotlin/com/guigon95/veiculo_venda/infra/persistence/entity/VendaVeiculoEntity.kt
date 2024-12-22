package com.guigon95.veiculo_venda.infra.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.*

@Entity
data class VendaVeiculoEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val idVeiculo: Long,
    val codigoPagamento: UUID,
    val valor: BigDecimal
)
