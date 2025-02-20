package com.servicio.cliente.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int clienteId;

    @OneToOne(cascade = CascadeType.ALL) // Añade CascadeType.ALL
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    private String contrasena;
    private boolean estado;
}
