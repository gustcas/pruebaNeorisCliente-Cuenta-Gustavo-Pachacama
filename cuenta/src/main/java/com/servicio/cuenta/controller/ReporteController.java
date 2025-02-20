package com.servicio.cuenta.controller;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getReporte(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime fechaFin,
            @RequestParam String numeroCuenta) {
        return ResponseEntity.ok(movimientoService.getMovimientosByNmeroCuentaAndFecha(numeroCuenta, fechaInicio, fechaFin));
    }
}