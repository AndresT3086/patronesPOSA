package com.sura.cliente.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActualizarClienteRequest {

/*    @NotBlank
    private String numeroDocumento;

    @Email
    private String email;

    @Size(min = 10, max = 20)
    private String telefono;

    private String direccion;
    private String ciudad;*/

    @Schema(description = "Correo electrónico del cliente",
            example = "nuevo@email.com",
            required = false)
    @Email(message = "Formato de email inválido")
    private String email;

    @Schema(description = "Número de teléfono celular (10-15 dígitos)",
            example = "3001234567",
            required = false)
    @Size(min = 10, max = 15, message = "Teléfono debe tener entre 10 y 15 dígitos")
    @Pattern(regexp = "\\d+", message = "Teléfono debe contener solo números")
    private String telefono;

    @Schema(description = "Dirección de residencia del cliente",
            example = "Calle 123 # 45-67",
            required = false)
    @Size(max = 500, message = "Dirección no puede exceder 500 caracteres")
    private String direccion;

    @Schema(description = "Ciudad de residencia",
            example = "MEDELLIN",
            allowableValues = {"BOGOTA", "MEDELLIN", "CALI", "BARRANQUILLA", "CARTAGENA",
                    "CUCUTA", "BUCARAMANGA", "PEREIRA", "MANIZALES", "IBAGUE"},
            required = false)
    private String ciudad;
}
