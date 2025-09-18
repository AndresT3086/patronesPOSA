package com.sura.cliente.pipeline;

public interface Filter <T>{
    T process(T input) throws FilterException;
}
