package com.servicio.cuenta.service.impl;
import com.servicio.cuenta.dto.CuentaDTO;
import com.servicio.cuenta.entity.Cuenta;
import com.servicio.cuenta.repository.CuentaRepository;
import com.servicio.cuenta.service.CuentaService;
import com.servicio.cuenta.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public CuentaDTO crearCuenta(Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
        // Enviar evento a Kafka
        kafkaProducerService.enviarEvento("cuenta-creada", "Cuenta creada: " + cuenta.getNumeroCuenta());
        return mapToDTO(nuevaCuenta);
    }

    @Override
    public Optional<CuentaDTO> obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta).map(this::mapToDTO);
    }

    @Override
    public List<CuentaDTO> listarCuentasPorCliente(int clienteId) {
        return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO actualizarCuenta(String numeroCuenta, Cuenta cuenta) {
        Cuenta cuentaExistente = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuentaExistente.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaExistente.setEstado(cuenta.isEstado());

        return mapToDTO(cuentaRepository.save(cuentaExistente));
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuentaRepository.delete(cuenta);
    }

    private CuentaDTO mapToDTO(Cuenta cuenta) {
        return new CuentaDTO(cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.isEstado(),
                cuenta.getClienteId());
    }
}
