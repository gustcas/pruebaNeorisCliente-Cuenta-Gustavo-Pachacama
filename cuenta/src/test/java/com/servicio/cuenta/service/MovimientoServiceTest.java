package com.servicio.cuenta.service;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;
import com.servicio.cuenta.repository.MovimientoRepository;
import com.servicio.cuenta.service.impl.MovimientoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMovimiento_exitoso() {
        Movimiento movimiento = new Movimiento(null, LocalDateTime.now(), "Depósito", 500.0, 500.0, "495878");

        when(movimientoRepository.findByNumeroCuenta("495878")).thenReturn(List.of());
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);

        MovimientoDTO resultado = movimientoService.registrarMovimiento(movimiento);

        assertEquals(500.0, resultado.getSaldo());
        verify(kafkaTemplate, times(1)).send("movimiento-creado", "Movimiento registrado para cuenta: 495878");
    }

    @Test
    void registrarMovimiento_saldoInsuficiente() {
        Movimiento movimiento = new Movimiento(null, LocalDateTime.now(), "Retiro", -600.0, 0.0, "495878");

        when(movimientoRepository.findByNumeroCuenta("495878"))
                .thenReturn(List.of(new Movimiento(1L, LocalDateTime.now(), "Depósito", 500.0, 500.0, "495878")));

        Exception exception = assertThrows(RuntimeException.class, () -> movimientoService.registrarMovimiento(movimiento));

        assertEquals("Saldo no disponible", exception.getMessage());
        verify(kafkaTemplate, times(1)).send("movimiento-error", "Saldo no disponible");
    }
}
