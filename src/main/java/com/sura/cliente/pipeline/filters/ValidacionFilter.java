package com.sura.cliente.pipeline.filters;

import com.sura.cliente.dto.ActualizarClienteRequest;
import com.sura.cliente.pipeline.Filter;
import com.sura.cliente.pipeline.FilterException;
import org.springframework.stereotype.Component;

@Component
public class ValidacionFilter implements Filter<ActualizarClienteRequest> {

    @Override
    public ActualizarClienteRequest process(ActualizarClienteRequest input) throws FilterException {

        if (input.getEmail() != null && !input.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new FilterException("Formato de email inválido");
        }

        if (input.getTelefono() != null && !input.getTelefono().matches("\\d{10,15}")) {
            throw new FilterException("Teléfono debe tener entre 10 y 15 dígitos");
        }

        return input;
    }
}
