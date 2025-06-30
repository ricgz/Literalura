package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosAutor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Principal {

    private static final Logger log = LoggerFactory.getLogger(Principal.class);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Libro> librosEncontrados;

    public Principal() {

    }

    // metodo para probar la conversion de datos
    public void testApi() {
        String busqueda = "pride";

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + busqueda);

        // Procesamos la respuesta Json y la formateamos como un Record Datoslibro
        // ademas filtramos solos los que cominecen con el texto indicado
        librosEncontrados = this.procesaJsonLibros(json).stream().
                filter(l -> l.titulo().toLowerCase().startsWith(busqueda.toLowerCase()))
                .map(l-> new Libro(l))
                .toList();

        librosEncontrados.forEach(System.out::println);

    }

    private List<DatosLibro> procesaJsonLibros(String json){
        List<DatosLibro> librosEncontrados = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(json);
            ArrayNode librosNode = (ArrayNode) rootNode.get("results");

            if(librosNode != null){
                for (JsonNode libroNode : librosNode ){
                    // filtramos solo los tipo texto
                    if(libroNode.get("media_type").asText().equals("Text")){
                        ArrayNode autoresNode = (ArrayNode) libroNode.get("authors");

                        List<DatosAutor> autores = new ArrayList<>();

                        for (JsonNode autorNode : autoresNode) {
                            autores.add(conversor.obtenerDatos(autorNode.toString(), DatosAutor.class));
                        }

                        DatosLibro libro = new DatosLibro(
                                libroNode.get("id").asInt(),
                                libroNode.get("title").asText(),
                                autores,
                                libroNode.get("summaries").toString(),
                                libroNode.get("languages").toString(),
                                libroNode.get("media_type").asText(),
                                libroNode.get("download_count").asInt()
                        );

                        librosEncontrados.add(libro);
                    }

                }

                return librosEncontrados;

            }else{
                System.out.println("No se encontraron resultados!");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return librosEncontrados;
    }
}
