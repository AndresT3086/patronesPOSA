package com.sura.cliente.pipeline;

import java.util.ArrayList;
import java.util.List;

public class ProcessingPipeline <T>{

    private final List<Filter<T>> filters = new ArrayList<>();

    public ProcessingPipeline<T> addFilter(Filter<T> filter) {
        filters.add(filter);
        return this;
    }

    public T execute(T input) throws FilterException {
        T result = input;
        for (Filter<T> filter : filters) {
            result = filter.process(result);
        }
        return result;
    }
}
