package com.servicio.cliente.service.impl;
import com.servicio.cliente.dto.ClienteDTO;
import com.servicio.cliente.entity.Cliente;
import com.servicio.cliente.entity.Persona;
import com.servicio.cliente.repository.ClienteRepository;
import com.servicio.cliente.service.ClienteService;
import com.servicio.cliente.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDTO crearCliente(Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        // Enviar evento a Kafka
        kafkaProducerService.enviarEvento("cliente-creado", "Cliente creado: " + cliente.getPersona().getIdentificacion());
        return mapToDTO(nuevoCliente);
    }

    @Override
    public Optional<ClienteDTO> obtenerClientePorId(int clienteId) {
        return clienteRepository.findByClienteId(clienteId).map(this::mapToDTO);
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO actualizarCliente(int clienteId, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Persona _persona = new Persona(
                (long) clienteId,
                cliente.getPersona().getNombre(),
                cliente.getPersona().getGenero(),
                cliente.getPersona().getEdad(),
                cliente.getPersona().getIdentificacion(),
                cliente.getPersona().getDireccion(),
                cliente.getPersona().getTelefono()
                );
        clienteExistente.setPersona(_persona);
        clienteExistente.setContrasena(cliente.getContrasena());
        clienteExistente.setEstado(cliente.isEstado());
        clienteExistente.setClienteId(clienteId);
        return mapToDTO(clienteRepository.save(clienteExistente));
    }

    @Override
    public void eliminarCliente(int clienteId) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    private ClienteDTO mapToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getClienteId(),
                cliente.getPersona().getNombre(),
                cliente.getPersona().getDireccion(),
                cliente.getPersona().getTelefono(),
                cliente.getContrasena(),
                cliente.isEstado());
    }




}
