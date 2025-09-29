package com.sura.cliente.pipeline.filters;

import com.sura.cliente.dto.ActualizarClienteRequest;
import com.sura.cliente.pipeline.Filter;
import com.sura.cliente.pipeline.FilterException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ValidacionNegocioFilter implements Filter<ActualizarClienteRequest> {

    private static final List<String> CIUDADES_VALIDAS = Arrays.asList(
            "BOGOTA", "MEDELLIN", "CALI", "BARRANQUILLA", "CARTAGENA",
            "CUCUTA", "BUCARAMANGA", "PEREIRA", "MANIZALES", "IBAGUE"
    );

    @Override
    public ActualizarClienteRequest process(ActualizarClienteRequest input) throws FilterException {

        if (input.getCiudad() != null && !CIUDADES_VALIDAS.contains(input.getCiudad().toUpperCase())) {
            throw new FilterException("Ciudad no válida. Ciudades permitidas: " + CIUDADES_VALIDAS);
        }

        if (input.getTelefono() != null && input.getTelefono().startsWith("0")) {
            throw new FilterException("El teléfono no puede comenzar con 0");
        }

        return input;
    }
}
