package com.CaicedoAna.libreria.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "libreria")
public class LibroD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;

    private String idiomas;
    private Integer descargas;
    private String categoria;
    private String medio;
    @ManyToOne
    private AutorD autor;

    public AutorD getAutor() {
        return autor;
    }

    public void setAutor(AutorD autor) {
        this.autor = autor;
    }

    public LibroD(Libros libroRecord) {
        this.titulo = libroRecord.titulo();

        if (libroRecord.idiomas().size() > 1) {
            StringBuilder strIdiomas = new StringBuilder();
            for (String idioma : libroRecord.idiomas()) {
                if (strIdiomas.length() > 0) {
                    strIdiomas.append(",");
                }
                strIdiomas.append(idioma);
            }
            this.idiomas = strIdiomas.toString();
        } else {
            this.idiomas = String.join(",", libroRecord.idiomas());
        }

        this.descargas = multiplicar(libroRecord.descargas());
        this.categoria = DevolverCategoria(libroRecord.categoria());
        this.medio = libroRecord.medio();
    }

    LibroD (){}
    private String DevolverCategoria(ArrayList<String> meme) {

        // Palabras a contar
        String palabraFiccion = "fiction";
        String palabraDrama = "drama";
        String palabraComedia = "comedy";

        // Contadores
        int contadorFiccion = 0;
        int contadorDrama = 0;
        int contadorComedia = 0;
        int contador=0;
        String categoriaFinal= null;

        // Contar las palabras en la lista
        for (String frase : meme) {
            if (frase.toLowerCase().contains(palabraFiccion)) {
                contadorFiccion++;
                contador++;
            }
            if (frase.toLowerCase().contains(palabraDrama)) {
                contadorDrama++;
                contador++;
            }
            if (frase.toLowerCase().contains(palabraComedia)) {
                contadorComedia++;
                contador++;
            }
        }
        if ( contadorFiccion > contadorComedia && contadorFiccion > contadorDrama){categoriaFinal = "Ficcion";}
        if ( contadorComedia > contadorFiccion && contadorComedia> contadorDrama){categoriaFinal = "Comedia";}
        if ( contadorDrama > contadorComedia && contadorDrama > contadorFiccion){categoriaFinal = "Drama";}
        String valores ="ficcion"+contadorFiccion+"drama"+contadorDrama+"comedia"+contadorComedia;
        if (categoriaFinal == null) {  categoriaFinal = "Otra";
        }
        return categoriaFinal;

    }
    public Integer multiplicar (int numero)
    {
        int Resultado=0;
        Resultado = 100023*numero;
        return Resultado;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }



    public Long getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public void setIdiomas(ArrayList<String> idiomas) {
        this.idiomas = idiomas.toString();
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "LibroD{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +

                ", idiomas='" + idiomas + '\'' +
                ", descargas=" + descargas +
                ", categoria='" + categoria + '\'' +
                ", medio='" + medio + '\'' +
                '}';
    }
}
