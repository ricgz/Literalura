package com.aluracursos.literalura.model;

public class Autor {
    private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

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

    @Override
    public String toString() {
        return "Nombre='" + nombre + '\'' +
                ", Nacimiento=" + nacimiento +
                ", Fallecimiento=" + fallecimiento;
    }
}
