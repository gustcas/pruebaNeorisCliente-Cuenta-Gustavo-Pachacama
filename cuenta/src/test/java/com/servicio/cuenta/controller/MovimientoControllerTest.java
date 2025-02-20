package com.servicio.cuenta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;
import com.servicio.cuenta.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, ports = 9092)
class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        movimientoRepository.deleteAll();
    }

    @Test
    void testCrearMovimiento_Exitoso() throws Exception {
        MovimientoDTO movimientoDTO = new MovimientoDTO(LocalDateTime.now(), "Depósito", 500.0, 500.0, "495878");

        mockMvc.perform(post("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movimientoDTO)))
                .andExpect(status().isCreated()) // Espera 201 (Created)
                .andExpect(jsonPath("$.tipoMovimiento", is("Depósito")))
                .andExpect(jsonPath("$.valor", is(500.0)))
                .andExpect(jsonPath("$.saldo", is(500.0)));
    }

    @Test
    void testCrearMovimiento_SaldoInsuficiente() throws Exception {
        movimientoRepository.save(new Movimiento(null, LocalDateTime.now(), "Depósito", 500.0, 500.0, "495878"));

        MovimientoDTO movimientoDTO = new MovimientoDTO(LocalDateTime.now(), "Retiro", -600.0, 0.0, "495878");

        mockMvc.perform(post("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movimientoDTO)))
                .andExpect(status().isBadRequest()) // Espera 400 (Bad Request)
                .andExpect(jsonPath("$", is("Saldo no disponible")));
    }

    @Test
    void testObtenerMovimientos() throws Exception {
        movimientoRepository.save(new Movimiento(null, LocalDateTime.now(), "Depósito", 500.0, 500.0, "495878"));

        mockMvc.perform(get("/movimientos/cuenta/495878")) // Usa el endpoint correcto
                .andExpect(status().isOk()) // Espera 200 (OK)
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));
    }
}