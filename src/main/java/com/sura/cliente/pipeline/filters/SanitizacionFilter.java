package com.sura.cliente.pipeline.filters;

import com.sura.cliente.dto.ActualizarClienteRequest;
import com.sura.cliente.pipeline.Filter;
import com.sura.cliente.pipeline.FilterException;
import org.springframework.stereotype.Component;

@Component
public class SanitizacionFilter implements Filter<ActualizarClienteRequest> {


    @Override
    public ActualizarClienteRequest process(ActualizarClienteRequest input) throws FilterException {
        if (input.getEmail() != null) {
            input.setEmail(input.getEmail().toLowerCase().trim());
        }

        if (input.getTelefono() != null) {
            String telefono = input.getTelefono().replaceAll("[^0-9]", "");
            input.setTelefono(telefono);
        }

        if (input.getDireccion() != null) {
            input.setDireccion(input.getDireccion().trim().toUpperCase());
        }

        if (input.getCiudad() != null) {
            input.setCiudad(input.getCiudad().trim().toUpperCase());
        }

        return input;
    }
}
