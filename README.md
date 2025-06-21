"# T2_CASTRO" 
#Sistema de Alquiler de Películas

Proyecto para la gestión de alquileres y devoluciones de películas, desarrollado con **Spring Boot**, **Thymeleaf** y **MySQL**.

## Descripción

Este proyecto permite registrar clientes, películas, alquileres y devoluciones, gestionando el stock de películas y el historial de transacciones. Incluye validaciones, filtros y una interfaz moderna basada en Thymeleaf.

## Características

- Registro, edición y eliminación de clientes.
- Registro y gestión de películas.
- Registro de alquileres.
- Registro de devoluciones.
- Mensajes de éxito y error en la interfaz.
- Interfaz web amigable y moderna.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven
- Lombok

## Configuración del proyecto

 ### Conexión al proyecto
 
```bash
 1. Clonación del proyecto

git clone  https://github.com/PatriciaCastro24/T2_CASTRO
cd T2_CASTRO

 2. Conexion a base de datos

spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.url=jdbc:mysql://localhost:3306/BD_T2_CASTRO

mvn spring-boot:run

T2-BLOCKBUSTER/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/cibertec/blockbuster/
    │   │   ├── controller/
    │   │   ├── entity/
    │   │   └── repository/
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       └── templates/
    │           ├── clientes.html
    │           ├── devolucion.html
    │           ├── index.html
    │           ├── detalleAlquiler.html
    │           ├── peliculas.html
    │           └── nuevoAlquiler.html
    └── test/
        └── java/com/cibertec/blockbuster/
            └── BlockbusterApplicationTests.java


Autor
Lorena
