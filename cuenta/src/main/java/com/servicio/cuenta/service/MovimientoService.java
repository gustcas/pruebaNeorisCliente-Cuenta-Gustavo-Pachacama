package com.servicio.cuenta.service;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MovimientoService {
    MovimientoDTO registrarMovimiento(Movimiento movimiento);
    List<MovimientoDTO> obtenerMovimientosPorCuenta(String numeroCuenta);
    List<MovimientoDTO> getMovimientosByNmeroCuentaAndFecha(String numeroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}