package com.guigon95.veiculo_venda.infra.gateway

import com.guigon95.veiculo_venda.domain.model.Veiculo
import com.guigon95.veiculo_venda.infra.client.VeiculoFeign
import com.guigon95.veiculo_venda.infra.controller.dto.VeiculoResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal

class VeiculoFeignClientTest {

    private val veiculoFeign = mock(VeiculoFeign::class.java)
    private val veiculoFeignClient = VeiculoFeignClient(veiculoFeign)

    @Test
    fun getById_ReturnsVeiculoWhenFound() {
        val id = 1L
        val veiculo = VeiculoResponse(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "A_VENDA")
        `when`(veiculoFeign.getById(id)).thenReturn(veiculo)

        val result = veiculoFeignClient.getById(id)

        assertThat(result).isEqualTo(Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "A_VENDA"))
    }

    @Test
    fun getById_ReturnsNullWhenNotFound() {
        val id = 1L
        `when`(veiculoFeign.getById(id)).thenReturn(null)

        val result = veiculoFeignClient.getById(id)

        assertThat(result).isNull()
    }

    @Test
    fun updateSituacaoVeiculo_ReturnsVeiculoWhenUpdated() {
        val id = 1L
        val veiculo = VeiculoResponse(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "A_VENDA")
        `when`(veiculoFeign.updateSituacao(id)).thenReturn(veiculo)

        val result = veiculoFeignClient.updateSituacaoVeiculo(id)

        assertThat(result).isEqualTo(Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "A_VENDA"))
    }

    @Test
    fun updateSituacaoVeiculo_ReturnsNullWhenNotUpdated() {
        val id = 1L
        `when`(veiculoFeign.updateSituacao(id)).thenReturn(null)

        val result = veiculoFeignClient.updateSituacaoVeiculo(id)

        assertThat(result).isNull()
    }

    @Test
    fun reservarVeiculo_ReturnsVeiculoWhenReserved() {
        val idVeiculo = 1L
        val veiculo = VeiculoResponse(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "RESERVADO")
        `when`(veiculoFeign.reservarVeiculo(idVeiculo)).thenReturn(veiculo)

        val result = veiculoFeignClient.reservarVeiculo(idVeiculo)

        assertThat(result).isEqualTo(Veiculo(1, "ABC1234","modelo", "marca", 2021, "prata", BigDecimal(100), "RESERVADO"))
    }

    @Test
    fun reservarVeiculo_ReturnsNullWhenNotReserved() {
        val idVeiculo = 1L
        `when`(veiculoFeign.reservarVeiculo(idVeiculo)).thenReturn(null)

        val result = veiculoFeignClient.reservarVeiculo(idVeiculo)

        assertThat(result).isNull()
    }
}