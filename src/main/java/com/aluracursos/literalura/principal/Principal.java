package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosAutor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Libro> librosBuscados = new ArrayList<>();

    public Principal() {

    }

    public void muestraElMenu() {
        System.out.println("\n=============== Bienvenido a LiterAlura ===============");
        var opcion = -1;

            while (opcion != 0) {
                var menu = """
                        \n
                    1 - Buscar libro por titulo (buscar en API y registrar en BD)
                    2 - Listar libros registrados (buscar en BD)
                    3 - Listar autores registrados (buscar en BD)
                    4 - Listar autores vivos en un determinado año (buscar en BD)
                    5 - Listar libros por idioma (Buscar en BD)
                                  
                    0 - Salir
                    """;
                System.out.println(menu);
                try{
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1:
                            buscarLibrosApi();
                            break;
                        case 2:
                            mostrarLibrosRegistrados();
                            break;
                        case 3:
                            mostrarAutoresRegistrados();
                            break;
                        case 4:
                            autoresVivosPorAnio();
                            break;
                        case 5:
                            mostrarLibrosPorIdioma();
                            break;
                        case 0:
                            System.out.println("Cerrando la aplicación...");
                            break;
                        default:
                            System.out.println("Opción fuera de rango! reintente...");
                    }
                }catch (InputMismatchException e){
                    System.out.println("Opcion invalida! reintente...");
                    teclado.nextLine(); // limpiamos el buffer de teclado.
                    opcion = -1;
                }
            }

    }

    private void buscarLibrosApi() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var libroBuscar = teclado.nextLine();
        String json = null;
        try {
            json = consumoApi.obtenerDatos(URL_BASE + "?search=" + URLEncoder.encode(libroBuscar, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // Procesamos la respuesta Json y la formateamos como un Record Datoslibro
        // ademas filtramos solos los que cominecen con el texto indicado
        Optional <Libro> libroEncontrado = this.procesaJsonLibros(json).stream()
                .filter(l -> l.titulo().toLowerCase().contains(libroBuscar.toLowerCase()))
                //.sorted(Comparator.comparing(DatosLibro::descargas).reversed())
                .map(l-> new Libro(l))
                .findFirst();

        if(libroEncontrado.isPresent()){
            var libro = libroEncontrado.get();
            librosBuscados.add(libro);
            System.out.println(libro);
        }

    }

    private void mostrarLibrosRegistrados() {
        System.out.println(" ======= Hay " + librosBuscados.size() + " libros registrados =======");
        librosBuscados.forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        if(!librosBuscados.isEmpty()){
            System.out.println(" ======= Autores registrados =======");
            librosBuscados.forEach(l -> System.out.println(l.getAutor()));
        }else{
            System.out.println("Aun no nada por aca!!! realice una busqueda primero..");
        }

    }

    private void autoresVivosPorAnio() {

        if(!librosBuscados.isEmpty()){
            System.out.println("Ingrese el año desde el cual desea consultar autores vivos:");
            var anio = teclado.nextLine();
            try{
                var anioConsulta = Integer.valueOf(anio);
                System.out.println(" ======= Autores vivos desde el año " + anioConsulta + " =======");
                librosBuscados.stream()
                        .filter(l-> l.getAutor().getNacimiento() >= anioConsulta)
                        .forEach(System.out::println);
            }catch (NumberFormatException e){
                System.out.println("El formato del año no es correcto! reintente...");
            }
        }else{
            System.out.println("Aun no nada por aca!!! realice una busqueda primero..");
        }
    }

    private void mostrarLibrosPorIdioma() {
        if(!librosBuscados.isEmpty()){
            System.out.println("Ingrese el idiaoma de los libros que desea buscar (es: español, en: ingles, fr: frances)");
            var idiomaBuscado = teclado.nextLine();

                System.out.println(" ======= Libros en idioma " + idiomaBuscado + " =======");
                librosBuscados.stream()
                        .filter(a -> a.getIdioma().equalsIgnoreCase(idiomaBuscado))
                        .forEach(System.out::println);

        }else{
            System.out.println("Aun no nada por aca!!! realice una busqueda primero..");
        }
    }


    // metodo para probar la conversion de datos
    public void testApi() {
        String busqueda = "pride";
        List<Libro> librosEncontrados;

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
            var cantidadDeResultados = rootNode.get("count").asInt();

            // ... si encontramos al menos 1 libro, continuamos...
            if(cantidadDeResultados > 0){
                ArrayNode librosNode = (ArrayNode) rootNode.get("results");
                //System.out.println(librosNode);
                for (JsonNode libroNode : librosNode ){
                    // filtramos solo los tipo texto
                    if(libroNode.get("media_type").asText().equals("Text")){
                        ArrayNode autoresNode = (ArrayNode) libroNode.get("authors");

                        DatosLibro libro = new DatosLibro(
                                libroNode.get("id").asInt(),
                                libroNode.get("title").asText(),
                                conversor.obtenerDatos(autoresNode.get(0).toString(), DatosAutor.class),
                                libroNode.get("summaries").get(0).asText(),
                                libroNode.get("languages").get(0).asText(),
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
            System.out.println("Error procesando la respuesta!. Error: " + e.toString());
            throw new RuntimeException(e);
        }
        return librosEncontrados;
    }
}
