package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";

    public Principal() {

    }

    public void testApi() {
        String datosApi = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(datosApi);
    }
}
