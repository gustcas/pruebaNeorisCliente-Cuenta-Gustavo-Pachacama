package com.servicio.cuenta.repository;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByNumeroCuenta(String numeroCuenta);

    List<MovimientoDTO> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}