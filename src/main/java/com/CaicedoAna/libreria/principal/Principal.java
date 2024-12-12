package com.CaicedoAna.libreria.principal;

import java.util.IntSummaryStatistics;



import com.CaicedoAna.libreria.model.*;
import com.CaicedoAna.libreria.repository.AutorRepository;
import com.CaicedoAna.libreria.repository.LibroRepository;
import com.CaicedoAna.libreria.service.ConsumoAPI;
import com.CaicedoAna.libreria.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {


    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner input = new Scanner(System.in);
    private final String direccion_URL = "https://gutendex.com/books/?page=1&search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    public String textoBusqueda;

    private LibroRepository repositoriolibro; // acceso CRUD
    private AutorRepository repositorioautor;

    public Principal(LibroRepository repositoriolibro, AutorRepository repositorioautor) {// acceso CRUD
        this.repositorioautor = repositorioautor;
        this.repositoriolibro = repositoriolibro;

    }

    public void mostrarMenu() {
        var opcion = -1;
        var menu = """
                           
                             MENU PRINCIPAL 
                --------------------------------------------
                1 - Buscar Libros por TÍtulo
                2 - Buscar Autor por Nombre
                3 - Listar Libros Registrados
                4 - Listar Autores Registrados
                5 - Listar Autores Vivos
                6 - Listar Libros por Idioma
                ----------------------------------------------
                0 -  SALIR DEL PROGRAMA 
                ----------------------------------------------
                Elija una opción:
                """;

        while (opcion != 0) {
            System.out.println(menu);
            try {
                opcion = Integer.valueOf(input.nextLine());
                switch (opcion) {
                    case 1:
                        BusquedaInicial();
                        break;
                    case 2:
                        buscarAutorPorNombre();
                        break;
                    case 3:
                        listarLibrosRegistrados();
                        break;
                    case 4:
                        listarAutoresRegistrados();
                        break;
                    case 5:
                        listarAutoresVivos();
                        break;
                    case 6:
                        listarLibrosPorIdioma();
                        break;
                    case 0:

                        System.out.println("Cerrando Aplicacion.... :)");
                        break;
                    default:
                        System.out.println("Opción no válida!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida: " + e.getMessage());

            }
        }
    }

    public void BusquedaInicial() {
        System.out.println("Escribe el titulo del libro que quieres buscar");
        var textoBusqueda = input.nextLine();
        String urlBusqueda = direccion_URL + textoBusqueda.replace(" ", "+");
        System.out.println("Buscando en la URL: " + urlBusqueda);
        var json = consumoAPI.obtenerDatos(urlBusqueda);

        Resultado resultados = conversor.obtenerDatos(json, Resultado.class);

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {


            Optional<Libros> datosLibro = resultados.resultados().stream()
                    .filter(libros -> libros.titulo().toLowerCase()
                            .contains(textoBusqueda.toLowerCase())).findFirst();

            if (datosLibro.isPresent()) {

                System.out.println("libro Encontrado");
                System.out.println(datosLibro.get().titulo());
                var nacimiento = datosLibro.get().autores().stream()
                        .map(autor -> autor.nacimiento().intValue()).findFirst();


                //  Opcion 1Datos directamente del dato encontrado
                System.out.println("\nAutor:" + datosLibro.get().autores().stream()
                        .map(autor -> autor.nombre()).limit(1).collect(Collectors.joining()) +
                        "\nNacimiento: " + datosLibro.get().autores().stream()
                        .map(autor -> autor.nacimiento().toString().replace("[", "").replace("[", "")).collect(Collectors.joining()) +
                        "\nDeceso: " + datosLibro.get().autores().stream()
                        .map(autor -> autor.deceso().toString().replace("[", "").replace("[", "")).collect(Collectors.joining()) +
                        " \nidiomas: " + datosLibro.get().idiomas().stream().collect(Collectors.joining()) +
                        " \ndescargas: " + datosLibro.get().descargas());

                // Opcion 2: Datos a traves del Array, las variables llegan con su tipo, puedo calcular la edad
                ArrayList<Autor> autor = datosLibro.get().autores();
                for (Autor autor1 : autor) {

                    var edad = autor1.nacimiento().intValue() - autor1.deceso().intValue();
                    System.out.println("Edad del Autor" + edad);
                }

                try {
                    List<LibroD> librosEncontrados = datosLibro.stream()
                            .map(libros -> new LibroD(libros)).collect(Collectors.toList());
                    AutorD autorLibro = datosLibro.stream()
                            .flatMap(libros -> libros.autores().stream()
                                    .map(autor2 -> new AutorD(autor2))).collect(Collectors.toList())
                            .stream().findFirst().get();
                    Optional<AutorD> buscarAutoresD = repositorioautor.findByNombreContainsIgnoreCase(datosLibro.get().autores().stream()
                            .map(autor2 -> autor2.nombre())
                            .collect(Collectors.joining()));
                    Optional<LibroD> buscarLibroD = repositorioautor.buscarLibroPorNombre(textoBusqueda);
                    if (buscarLibroD.isPresent()) {
                        System.out.println(" El libro ya esta en la base de datos");
                    } else {
                        AutorD autorsalvarBase;
                        if (buscarAutoresD.isPresent()) {
                            autorsalvarBase = buscarAutoresD.get();
                            System.out.println(" El autor ya esta en la base de datos");
                        } else {
                            autorsalvarBase = autorLibro;
                            repositorioautor.save(autorsalvarBase);
                        }
                        autorsalvarBase.setLibros(librosEncontrados);
                        repositorioautor.save(autorsalvarBase);


                    }


                } catch (Exception e) {
                    System.out.println("Warning! " + e.getMessage());
                }
            } else {
                System.out.println("Libro no encontrado!");
            }

        }
    }

    public void guardarLibroAutor(DatosLibroAutor infoLibroAutor ) {
        if (infoLibroAutor != null) {
            System.out.println("en el metodo"+infoLibroAutor.getAutor());
            repositorioautor.save(infoLibroAutor.getAutor());
            infoLibroAutor.getLibro().setAutor(infoLibroAutor.getAutor());
            repositoriolibro.save(infoLibroAutor.getLibro());
            System.out.println("en el metodo"+infoLibroAutor.getLibro());
        }
    }

    public void BusquedaInicialArray() {
        System.out.println("Escribe el titulo del libro que quieres buscar");
        var textoBusqueda = input.nextLine();
        // textoBusqueda = "A Room with a View";
        System.out.println(direccion_URL + textoBusqueda.replace(" ", "+"));
        var json = consumoAPI.obtenerDatos(direccion_URL + textoBusqueda.replace(" ", "+"));

        Resultado librosEncontrados = conversor.obtenerDatos(json, Resultado.class);

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {


            System.out.println(librosEncontrados);
            List<LibroD> librosParaBase = new ArrayList<>();
            librosParaBase = librosEncontrados.resultados().stream()
                    .map(LibroD::new)
                    .collect(Collectors.toList());
            // System.out.println(librosParaBase);
            for (LibroD librosD : librosParaBase) {
                System.out.println(librosD);
            }
            List<AutorD> autorParaBase = new ArrayList<>();
            autorParaBase = librosEncontrados.resultados().stream()
                    .flatMap(libros -> libros.autores().stream()
                            .map(autor -> new AutorD(autor)))
                    .collect(Collectors.toList());
            for (AutorD autorBD : autorParaBase) {
                //  System.out.println(autorBD);
            }

            List<DatosLibroAutor> listaLibroAutores = new ArrayList<>();


            for (int i = 0; i < librosParaBase.size(); i++) {
                DatosLibroAutor infoLibroAutor = new DatosLibroAutor(librosParaBase.get(i), autorParaBase.get(i));
                listaLibroAutores.add(infoLibroAutor);
            }


            for (DatosLibroAutor libroAutor : listaLibroAutores) {
                System.out.println( libroAutor.getAutor().getNombre());
                System.out.println( libroAutor.getLibro().getTitulo());
                Optional<AutorD> buscarAutoresD = repositorioautor.findByNombreContainsIgnoreCase(libroAutor.getAutor().getNombre());
                Optional<LibroD> buscarLibroD = repositoriolibro.findByTituloContainsIgnoreCase(libroAutor.getLibro().getTitulo());

                try {
                    if (buscarLibroD.isPresent()) {
                        System.out.println(" El libro ya esta en la base de datos");
                    } else {

                        if (buscarAutoresD.isPresent()) {
                            LibroD libronuevo;
                            libronuevo = libroAutor.getLibro();
                            libronuevo.setAutor(buscarAutoresD.get());
                            System.out.println(libronuevo);
                            repositoriolibro.save(libronuevo);
                            System.out.println(" El autor ya sera en la base de datos");
                        } else {
                            //  System.out.println("autor no presente");
                            AutorD autornuevo= libroAutor.getAutor();
                            LibroD libronuevo = libroAutor.getLibro();
                            DatosLibroAutor DatosLibroAutor = new DatosLibroAutor(libronuevo, autornuevo);
                            guardarLibroAutor(DatosLibroAutor);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Atencion! " + e.getMessage());
                }
//

            }
        } else {
            System.out.println("Libro no encontrado!");
        }
    }


    // metodo desahabilitado
    public void BusquedaLibros() {
//        textoBusqueda = "quijote";
        System.out.println(direccion_URL + textoBusqueda.replace(" ", "+"));
        var json = consumoAPI.obtenerDatos(direccion_URL + textoBusqueda.replace(" ", "+"));

        Resultado librosEncontrados = conversor.obtenerDatos(json, Resultado.class);
        System.out.println(librosEncontrados);
        List<LibroD> librosParaBase = new ArrayList<>();
        librosParaBase = librosEncontrados.resultados().stream()
                .map(LibroD::new)
                .collect(Collectors.toList());
        System.out.println(librosParaBase);
        for (LibroD librosBD : librosParaBase) {
            System.out.println(librosBD);
        }
        List<AutorD> autorParaBase = new ArrayList<>();
        autorParaBase = librosEncontrados.resultados().stream()
                .flatMap(libros -> libros.autores().stream()
                        .map(autor -> new AutorD(autor)))
                .collect(Collectors.toList());
        for (AutorD autorBD : autorParaBase) {
            System.out.println(autorBD);
        }


    }

    public void buscarAutorPorNombre() {
        System.out.println("Escribe el nombre del autor");
        var nombreAutor = input.nextLine();

        Optional<AutorD> autorBuscado = repositorioautor.findByNombreContainsIgnoreCase(nombreAutor);
        if (autorBuscado.isPresent()) {
            System.out.println(autorBuscado.get());
        }

    }

    public void listarLibrosRegistrados() {
        System.out.println("Lista de libros registrados");
        List<LibroD> libros = repositoriolibro.findAll();
        for (LibroD libro : libros) {
            System.out.println(libro);
        }

    }

    public void listarAutoresRegistrados() {
        System.out.println("Lista de autores registrados");
        List<AutorD> autores = repositorioautor.findAll();
        for (AutorD autor : autores) {
            System.out.println(autor);
        }

    }

    public void listarAutoresVivos() {
        System.out.println("Por favor escribe el año en cuestion");
        var anio = input.nextInt();
        List<AutorD> autores = repositorioautor.buscarAutoresVivos(anio);
        System.out.println(autores);
        for (AutorD autor :autores)
        {
            System.out.println("nombre:"+autor.getNombre());
            var edad=anio-autor.getNacimiento();
            System.out.println("tenia esta "+edad+" en ese año");


        }
    }

    public void listarLibrosPorIdioma() {
        List <String> idiomas= repositoriolibro.EncontrarlosIdimoas();
        System.out.println("lista de abrevitauras de idiomas existentes en la base");
        for ( String i :idiomas) {
            System.out.println(i);
        }

        System.out.println("Escribe la abreviatura del idioma ");
        var idioma = input.nextLine();
        List<LibroD> libros = repositorioautor.buscarPorIdima(idioma);
        System.out.println(libros);

    }

}
