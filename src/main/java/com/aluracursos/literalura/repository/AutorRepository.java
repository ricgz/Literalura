package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findFirstByNombre(String nombre);

    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE a.nacimiento >= :anio")
    List<Autor> autoresPorAnio(Integer anio);
}
