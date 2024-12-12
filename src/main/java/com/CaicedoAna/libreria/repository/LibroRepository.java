package com.CaicedoAna.libreria.repository;

import com.CaicedoAna.libreria.model.LibroD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository  extends JpaRepository<LibroD,Long> {
    Optional<LibroD> findByTituloContainsIgnoreCase(String titulo);


    @Query("SELECT DISTINCT l.idiomas FROM LibroD l")
    List <String> EncontrarlosIdimoas();


}
