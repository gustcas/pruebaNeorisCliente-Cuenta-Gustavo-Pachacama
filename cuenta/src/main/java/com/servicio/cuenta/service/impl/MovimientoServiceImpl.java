package com.servicio.cuenta.service.impl;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;
import com.servicio.cuenta.repository.MovimientoRepository;
import com.servicio.cuenta.service.MovimientoService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.movimientoRepository = movimientoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public MovimientoDTO registrarMovimiento(Movimiento movimiento) {
        movimiento.setFecha(LocalDateTime.now());

        // Validar saldo disponible
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuenta(movimiento.getNumeroCuenta());
        double saldoActual = movimientos.isEmpty() ? 0 : movimientos.get(movimientos.size() - 1).getSaldo();

        double nuevoSaldo = saldoActual + movimiento.getValor();
        if (nuevoSaldo < 0) {
            kafkaTemplate.send("movimiento-error", "Saldo no disponible");
            throw new RuntimeException("Saldo no disponible");
        }

        movimiento.setSaldo(nuevoSaldo);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        // Enviar evento a Kafka
        kafkaTemplate.send("movimiento-creado", "Movimiento registrado para cuenta: " + movimiento.getNumeroCuenta());

        return mapToDTO(movimientoGuardado);
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientosPorCuenta(String numeroCuenta) {
        return movimientoRepository.findByNumeroCuenta(numeroCuenta)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MovimientoDTO mapToDTO(Movimiento movimiento) {
        return new MovimientoDTO(movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getNumeroCuenta());
    }


    @Override
    public List<MovimientoDTO> getMovimientosByNmeroCuentaAndFecha(String numeroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByNumeroCuentaAndFechaBetween(numeroCuenta, fechaInicio, fechaFin);
    }

}
