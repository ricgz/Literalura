package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(DatosAutor autor) {
        this.nombre = autor.nombre();
        this.nacimiento = autor.nacimiento();
        this.fallecimiento = autor.fallecimiento();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return getNombre() +
                " (Nacimiento: " + getNacimiento() +
                " - Fallecimiento: " +  getFallecimiento() +
                ")";
    }

    private List<String> getTituloLibros(){
        return getLibros().stream()
                .map(l -> l.getTitulo())
                .toList();
    }

    public String showAutor() {

        return "Autor: " + getNombre() + "\n" +
                "Fecha de nacimiento: " + getNacimiento() + "\n" +
                "Fecha de fallecimiento: " +  getFallecimiento() + "\n" +
                "Libros: " + getTituloLibros() + "\n";
    }
}
