package com.CaicedoAna.libreria.repository;

import com.CaicedoAna.libreria.model.AutorD;
import com.CaicedoAna.libreria.model.LibroD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorD, Long> {
    Optional<AutorD> findByNombreContainsIgnoreCase(String nombreAutor);
    @Query("SELECT l FROM LibroD l JOIN l.autor a WHERE l.titulo LIKE %:nombre%")
    Optional<LibroD> buscarLibroPorNombre(@Param("nombre") String nombre);

    @Query("SELECT l FROM LibroD l JOIN l.autor a")
    List<LibroD> buscarTodosLosLibros();

    @Query("SELECT a FROM AutorD a WHERE a.deceso > :fecha AND a.nacimiento < :fecha")
    List<AutorD> buscarAutoresVivos(@Param ("fecha") Integer fecha);
    @Query("SELECT l FROM LibroD l JOIN l.autor a WHERE l.idiomas = :idioma")
    List<LibroD> buscarPorIdima(@Param ("idioma") String idioma);
}
