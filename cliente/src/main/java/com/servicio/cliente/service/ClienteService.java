package com.servicio.cliente.service;
import com.servicio.cliente.dto.ClienteDTO;
import com.servicio.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    ClienteDTO crearCliente(Cliente cliente);
    Optional<ClienteDTO> obtenerClientePorId(int clienteId);
    List<ClienteDTO> listarClientes();
    ClienteDTO actualizarCliente(int clienteId, Cliente cliente);
    void eliminarCliente(int clienteId);
}
