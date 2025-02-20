package com.servicio.cuenta.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @Column(nullable = false)
    private String tipoMovimiento;
    @Column(nullable = false)
    private double valor;
    private double saldo;
    @Column(nullable = false)
    private String numeroCuenta;
}
