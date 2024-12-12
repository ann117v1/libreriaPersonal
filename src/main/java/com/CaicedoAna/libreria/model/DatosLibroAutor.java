package com.CaicedoAna.libreria.model;

public class DatosLibroAutor {

    private LibroD libro;
    private AutorD autor;

    public DatosLibroAutor(LibroD libro, AutorD autor) {
        this.libro = libro;
        this.autor = autor;
    }

    public LibroD getLibro() {
        return libro;
    }

    public void setLibro(LibroD libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "DatosLibroAutor{" +
                "libro=" + libro +
                ", autor=" + autor +
                '}';
    }

    public AutorD getAutor() {
        return autor;
    }

    public void setAutor(AutorD autor) {
        this.autor = autor;
    }
}
