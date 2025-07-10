package com.aluracursos.literalura.model;

import java.util.List;

public class Libro {
    private Long id;
    private Integer id_api;
    private String titulo;
    private List<Autor> autores;
    private String sinopsis;
    private String idioma;
    private String tipo;
    private Integer descargas;

    public Libro(DatosLibro l) {
        this.id_api = l.id();
        this.titulo = l.titulo();
        this.autores = this.obtenerAutores(l.autores());
        this.sinopsis = l.sinopsis();
        this.idioma = l.idioma();
        this.tipo = l.tipo();
        this.descargas = l.descargas();


    }

    private List<Autor> obtenerAutores(List<DatosAutor> autores){
        return autores.stream()
                .map(a -> new Autor(a))
                .toList();
    }

    public Integer getId_api() {
        return id_api;
    }

    public void setId_api(Integer id_api) {
        this.id_api = id_api;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idiomas) {
        this.idioma = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    private String getAutoresString(){
        String autores = "";
        for (Autor autor : getAutores()){
            autores += "\n" +
                    "\t\t*" + autor.getNombre() +
                    " - Nac: " + autor.getNacimiento() +
                    " - Fallecimiento: " +  autor.getFallecimiento() +
                    "\n";
        }
        return autores;
    }

    @Override
    public String toString() {
        return
                "\n==========================================" +
                "\nLibro: " + titulo + '\n' +
                "\tSinopsis: " + sinopsis  + '\n' +
                "\tidioma: " + idioma + '\n' +
                "\tAutores: " + getAutoresString()  +
                "\tTipo: " + tipo + '\n' +
                "\tDescargas: " + descargas + '\n';

    }
}
