package com.sura.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteEventDto {

    private String eventType;
    private String numeroDocumento;
    private String nombreCompleto;
    private Map<String, Object> changedFields;
    private LocalDateTime timestamp;
    private String source;

    public ClienteEventDto(String eventType, String numeroDocumento,
                           String nombreCompleto, Map<String, Object> changedFields) {
        this.eventType = eventType;
        this.numeroDocumento = numeroDocumento;
        this.nombreCompleto = nombreCompleto;
        this.changedFields = changedFields;
        this.timestamp = LocalDateTime.now();
        this.source = "cliente-service";
    }
}
