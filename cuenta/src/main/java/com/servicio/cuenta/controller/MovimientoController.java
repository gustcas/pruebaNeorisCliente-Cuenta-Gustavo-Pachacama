package com.servicio.cuenta.controller;
import com.servicio.cuenta.dto.MovimientoDTO;
import com.servicio.cuenta.entity.Movimiento;
import com.servicio.cuenta.service.MovimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarMovimiento(@RequestBody Movimiento movimiento) {
        try {
            MovimientoDTO movimientoDTO = movimientoService.registrarMovimiento(movimiento);
            return new ResponseEntity<>(movimientoDTO, HttpStatus.CREATED); // Devuelve 201 (Created)
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Devuelve 400 (Bad Request)
        }
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorCuenta(@PathVariable String numeroCuenta) {
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

}
