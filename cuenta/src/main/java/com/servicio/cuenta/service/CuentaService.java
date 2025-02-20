package com.servicio.cuenta.service;
import com.servicio.cuenta.dto.CuentaDTO;
import com.servicio.cuenta.entity.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaService {
    CuentaDTO crearCuenta(Cuenta cuenta);
    Optional<CuentaDTO> obtenerCuentaPorNumero(String numeroCuenta);
    List<CuentaDTO> listarCuentasPorCliente(int clienteId);
    CuentaDTO actualizarCuenta(String numeroCuenta, Cuenta cuenta);
    void eliminarCuenta(String numeroCuenta);
}