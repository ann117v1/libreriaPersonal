package com.CaicedoAna.libreria;

import com.CaicedoAna.libreria.principal.Principal;
import com.CaicedoAna.libreria.repository.AutorRepository;
import com.CaicedoAna.libreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibreriaApplication implements CommandLineRunner {

	@Autowired// acceso CRUD ( inyeccion de dependencias)
	private AutorRepository repositorioAutor;// acceso CRUD
	@Autowired
	private LibroRepository repositorioLibro;// acceso CRUD
	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal= new Principal(repositorioLibro,repositorioAutor );
		//principal.BusquedaInicialArray();
		principal.mostrarMenu();
//		principal.BusquedaLibros();

	}
}

