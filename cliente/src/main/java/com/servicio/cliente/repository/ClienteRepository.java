package com.servicio.cliente.repository;
import com.servicio.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByClienteId(Integer clienteId);
}
