package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") DatosAutor autor,
        @JsonAlias("summaries") String sinopsis,
        @JsonAlias("languages") String idioma,
        @JsonAlias("media_type") String tipo,
        @JsonAlias("download_count") Integer descargas
) {
}
