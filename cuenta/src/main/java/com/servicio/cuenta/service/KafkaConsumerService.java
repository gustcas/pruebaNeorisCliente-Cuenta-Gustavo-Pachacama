package com.servicio.cuenta.service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "cliente-creado", groupId = "movimiento-group")
    public void escucharClienteCreado(String mensaje) {
        System.out.println("Evento recibido - Cliente Creado: " + mensaje);
    }

    @KafkaListener(topics = "cuenta-creada", groupId = "movimiento-group")
    public void escucharCuentaCreada(String mensaje) {
        System.out.println("Evento recibido - Cuenta Creada: " + mensaje);
    }
}
