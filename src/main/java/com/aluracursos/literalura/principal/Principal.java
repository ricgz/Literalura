package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosAutor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
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
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Libro> librosBuscados = new ArrayList<>();

    public Principal(LibroRepository lRepository, AutorRepository aRepository) {
        this.libroRepository = lRepository;
        this.autorRepository = aRepository;
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
                            buscarLibro();
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
                    teclado.nextLine();
                    opcion = -1;
                }
            }

    }

    private void buscarLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var libroBuscar = teclado.nextLine();
        Libro libro = null;

        Optional<Libro> libroEncontrado = libroRepository.findByTituloContainsIgnoreCase(libroBuscar);

        if(libroEncontrado.isPresent()){
            System.out.println("Libro ya registrado en BD");
            libro = libroEncontrado.get();
        }else{
            System.out.println("Buscando en API...");
            libroEncontrado = buscarLibrosApi(libroBuscar);
            if(libroEncontrado.isPresent()){
                libro = libroEncontrado.get();
                libroRepository.save(libro);
            }else{
                System.out.println("No hay resultados para la busqueda: " + libroBuscar);
            }
        }
        if(libro != null){
            System.out.println(libro);
        }

    }

    private Optional<Libro> buscarLibrosApi(String titulo) {
        String json = null;

        try {
            json = consumoApi.obtenerDatos(URL_BASE + "?search=" + URLEncoder.encode(titulo, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // Procesamos la respuesta Json y la formateamos como un Record Datoslibro
        // ademas filtramos solos los que comiencen con el texto indicado
        Optional <Libro> libroEncontrado = this.procesaJsonLibros(json).stream()
                .filter(l -> l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .map(l-> new Libro(l))
                .findFirst();

        return libroEncontrado;

    }

    private void mostrarLibrosRegistrados() {
        librosBuscados = libroRepository.findAll();

        if(!librosBuscados.isEmpty()){
            System.out.println(" ======= Hay " + librosBuscados.size() + " libros registrados =======");
            librosBuscados.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(l-> System.out.println(l.toString()));
        }else{
            System.out.println("Aun no nada por aca!!! realice una busqueda primero..");
        }

    }

    private void mostrarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        if(!autores.isEmpty()){
            System.out.println(" ======= Autores registrados =======");
            autores.forEach(l -> System.out.println(l.showAutor()));
        }else{
            System.out.println("Aun no nada por aca!!! realice una busqueda primero..");
        }

    }

    private void autoresVivosPorAnio() {
            System.out.println("Ingrese el año desde el cual desea consultar autores vivos:");
            var anio = teclado.nextLine();
            try{
                var anioConsulta = Integer.valueOf(anio);
                List<Autor> autores = autorRepository.autoresPorAnio(anioConsulta);

                if(autores.isEmpty()){
                    System.out.println("No hay autores registrados desde el año " + anioConsulta);
                }else{
                    System.out.println(" ======= Autores vivos desde el año " + anioConsulta + " =======");
                    autores.stream()
                            .forEach(System.out::println);
                }
            }catch (NumberFormatException e){
                System.out.println("El formato del año no es correcto! reintente...");
            }
    }

    private void mostrarLibrosPorIdioma() {

            System.out.println("Ingrese el idioma de los libros que desea buscar (es: español, en: ingles, fr: frances)");
            var idiomaBuscado = teclado.nextLine();
            List<Libro> libros = libroRepository.findByIdioma(idiomaBuscado);

            if(!libros.isEmpty()){
                System.out.println(" ======= " + libros.size() +" Libros en idioma '" + idiomaBuscado + "' =======");
                libros.stream()
                        .forEach(System.out::println);
            }else{
                System.out.println("No hay libros registrados en el idioma '" + idiomaBuscado + "'");
            }

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
                                (autoresNode.isEmpty() ? null : conversor.obtenerDatos(autoresNode.get(0).toString(), DatosAutor.class)),
                                (libroNode.get("summaries").isEmpty() ? "Sin informacion!" : libroNode.get("summaries").get(0).asText()),
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
