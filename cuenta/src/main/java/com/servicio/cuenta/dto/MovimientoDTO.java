package com.servicio.cuenta.dto;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private String numeroCuenta;
}
