package com.servicio.cliente.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private int clienteid;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;
}
