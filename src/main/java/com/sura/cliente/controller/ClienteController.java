package com.sura.cliente.controller;

import com.sura.cliente.dto.ActualizarClienteRequest;
import com.sura.cliente.model.Cliente;
import com.sura.cliente.service.ClienteService;
import com.sura.cliente.pipeline.FilterException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

/*    @PutMapping("/{numeroDocumento}")
    public ResponseEntity<Map<String, Object>> actualizarCliente(
            @PathVariable String numeroDocumento,
            @Valid @RequestBody ActualizarClienteRequest request) {

        try {
            // Asegurar que el número de documento coincida
            request.setNumeroDocumento(numeroDocumento);

            Cliente clienteActualizado = clienteService.actualizarCliente(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente actualizado exitosamente");
            response.put("data", clienteActualizado);

            return ResponseEntity.ok(response);

        } catch (FilterException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error de validación: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }*/

    @PutMapping("/{numeroDocumento}")
    public ResponseEntity<Map<String, Object>> actualizarCliente(
            @Parameter(description = "Número de documento del cliente", example = "12345678")
            @PathVariable String numeroDocumento,
            @Valid @RequestBody ActualizarClienteRequest request) {

        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(numeroDocumento, request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente actualizado exitosamente");
            response.put("data", clienteActualizado);

            return ResponseEntity.ok(response);

        } catch (FilterException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error de validación: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<Map<String, Object>> obtenerCliente(@PathVariable String numeroDocumento) {
        try {
            Cliente cliente = clienteService.obtenerCliente(numeroDocumento);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", cliente);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
