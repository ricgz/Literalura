package com.aluracursos.literalura.model;
import jakarta.persistence.*;

@Entity
@Table (name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer id_api;
    @Column (unique = true)
    private String titulo;
    @ManyToOne (cascade = CascadeType.PERSIST)
    private Autor autor;
    @Column(length = 2000)
    private String sinopsis;
    private String idioma;
    private String tipo;
    private Integer descargas;

    public Libro() {
    }

    public Libro(DatosLibro l) {
        this.id_api = l.id();
        this.titulo = l.titulo();
        this.autor = new Autor(l.autor());
        this.sinopsis = l.sinopsis();
        this.idioma = l.idioma();
        this.tipo = l.tipo();
        this.descargas = l.descargas();
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

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
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

    @Override
    public String toString() {
        return
                "\n----- LIBRO ------" + '\n' +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor.toString() + '\n' +
                "idioma: " + idioma + '\n' +
                "Numero de descargas: " + descargas + '\n' +
                "---------------------" + '\n' ;
    }
}
