# Proyecto de Librería en Java

Este proyecto es una aplicación de consola escrita en Java que interactúa con una base de datos de libros y autores. Permite buscar, listar y guardar información sobre libros y autores, y consultar libros en línea desde una API externa. El proyecto utiliza Spring Data JPA para acceder a una base de datos y una API para obtener información adicional sobre libros.

## Funcionalidades del Menú Principal

La aplicación ofrece un menú con varias opciones para interactuar con los datos de libros y autores:

### 1. Buscar Libros por Título
Permite buscar libros por su título. Al ingresar un título, la aplicación realiza una consulta en una API externa para obtener información sobre el libro y mostrar detalles como el autor, fecha de nacimiento y muerte, idiomas y número de descargas.

### 2. Buscar Autor por Nombre
Permite buscar un autor por su nombre. Si el autor está registrado en la base de datos, se muestra la información correspondiente.

### 3. Listar Libros Registrados
Muestra una lista de todos los libros que están registrados en la base de datos.

### 4. Listar Autores Registrados
Muestra una lista de todos los autores que están registrados en la base de datos.

### 5. Listar Autores Vivos
Permite ingresar un año específico y muestra una lista de autores que estaban vivos en ese año, junto con su edad en ese momento.

### 6. Listar Libros por Idioma
Permite ver una lista de todos los idiomas registrados en la base de datos y luego seleccionar un idioma para ver los libros asociados a ese idioma.

### 0. Salir del Programa
Cierra la aplicación.

## Requisitos

- Java 11 o superior.
- Spring Boot y Spring Data JPA para el acceso a la base de datos.
- Dependencias de `ConsumoAPI` y `ConvierteDatos` para interactuar con la API externa de libros.

## Estructura del Proyecto

- **Principal.java**: Clase principal que contiene la lógica del menú y la interacción con el usuario.
- **LibroRepository**: Repositorio que maneja operaciones CRUD para la entidad `Libro`.
- **AutorRepository**: Repositorio que maneja operaciones CRUD para la entidad `Autor`.
- **ConsumoAPI**: Servicio que obtiene los datos de la API externa de libros.
- **ConvierteDatos**: Servicio que convierte los datos obtenidos de la API en objetos Java.

## Uso

1. Ejecuta el archivo `Principal.java` para iniciar la aplicación.
2. Selecciona una de las opciones del menú ingresando el número correspondiente.
3. Sigue las instrucciones en pantalla para realizar las búsquedas o listar la información.

## Contribuciones

Si deseas contribuir a este proyecto, puedes crear un fork del repositorio, hacer tus cambios y enviar un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
