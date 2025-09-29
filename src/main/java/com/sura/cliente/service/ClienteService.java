package com.sura.cliente.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sura.cliente.dto.ActualizarClienteRequest;
import com.sura.cliente.dto.ClienteEventDto;
import com.sura.cliente.model.Cliente;
import com.sura.cliente.pipeline.ProcessingPipeline;
import com.sura.cliente.pipeline.FilterException;
import com.sura.cliente.pipeline.filters.ValidacionFilter;
import com.sura.cliente.pipeline.filters.SanitizacionFilter;
import com.sura.cliente.pipeline.filters.ValidacionNegocioFilter;
import com.sura.cliente.repository.ClienteRepository;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ProcessingPipeline<ActualizarClienteRequest> pipeline;

    public ClienteService(ClienteRepository clienteRepository,
                          KafkaProducerService kafkaProducerService,
                          ValidacionFilter validacionFilter,
                          SanitizacionFilter sanitizacionFilter,
                          ValidacionNegocioFilter validacionNegocioFilter) {
        this.clienteRepository = clienteRepository;
        this.kafkaProducerService = kafkaProducerService;

        // Construir pipeline de procesamiento (Pipes & Filters)
        this.pipeline = new ProcessingPipeline<ActualizarClienteRequest>()
                .addFilter(validacionFilter)
                .addFilter(sanitizacionFilter)
                .addFilter(validacionNegocioFilter);
    }

    public Cliente actualizarCliente(String numeroDocumento, ActualizarClienteRequest request) throws FilterException {

        // Ejecutar pipeline de procesamiento
        ActualizarClienteRequest requestProcesado = pipeline.execute(request);

        // Buscar cliente por numeroDocumento del parámetro
        Cliente cliente = clienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + numeroDocumento));

        // Capturar cambios para el evento
        Map<String, Object> cambios = new HashMap<>();

        // Actualizar SOLO los campos que vienen en el request (no nulos)
        if (requestProcesado.getEmail() != null && !requestProcesado.getEmail().equals(cliente.getEmail())) {
            cambios.put("email", Map.of(
                    "anterior", cliente.getEmail(),
                    "nuevo", requestProcesado.getEmail()
            ));
            cliente.setEmail(requestProcesado.getEmail());
        }

        if (requestProcesado.getTelefono() != null && !requestProcesado.getTelefono().equals(cliente.getTelefono())) {
            cambios.put("telefono", Map.of(
                    "anterior", cliente.getTelefono(),
                    "nuevo", requestProcesado.getTelefono()
            ));
            cliente.setTelefono(requestProcesado.getTelefono());
        }

        if (requestProcesado.getDireccion() != null && !requestProcesado.getDireccion().equals(cliente.getDireccion())) {
            cambios.put("direccion", Map.of(
                    "anterior", cliente.getDireccion(),
                    "nuevo", requestProcesado.getDireccion()
            ));
            cliente.setDireccion(requestProcesado.getDireccion());
        }

        if (requestProcesado.getCiudad() != null && !requestProcesado.getCiudad().equals(cliente.getCiudad())) {
            cambios.put("ciudad", Map.of(
                    "anterior", cliente.getCiudad(),
                    "nuevo", requestProcesado.getCiudad()
            ));
            cliente.setCiudad(requestProcesado.getCiudad());
        }

        if (!cambios.isEmpty()) {
            Cliente clienteActualizado = clienteRepository.save(cliente);

            // Enviar evento a Kafka
            ClienteEventDto evento = new ClienteEventDto(
                    "CLIENT_UPDATED",
                    cliente.getNumeroDocumento(),
                    cliente.getNombreCompleto(),
                    cambios
            );
            kafkaProducerService.sendClientEvent(evento);

            return clienteActualizado;
        }

        return cliente;
    }

    public Cliente obtenerCliente(String numeroDocumento) {
        return clienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + numeroDocumento));
    }

}
