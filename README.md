<img width="300"  alt="Literalura logo" src="https://github.com/user-attachments/assets/208c19a4-765c-4f9f-b5f0-5ede05947029" />

<h1 align="center"> LiterAlura, tu bliblioteca personal!</h1>
<img src="https://img.shields.io/badge/Proyecto-Desafio%20Alura-green"/>


## :eyes: Descripción
LiterAlura es un proyecto que permite crear tu catalogo de libros y autores. Esta app hace uso de la api de  🔗 <a href="https://gutendex.com/">Gutendex.org</a> como proveedor de datos la cual a su vez de basa en el proyecto Gutenberg.org, 
en donde se cuenta con miles de libros en variados idioma para su libre descarga y lectura.

## :hammer: Funcionalidades del proyecto
Entre sus funciones se describen las siguientes capacidades que otorga esta app.
- `Diponibilidad`: Se basa en el uso de libros disponibles del <a href="https://www.gutenberg.org">Proyecto Gutenberg</a>
- `Busqueda en Linea`: Permite realizar busquedas en linea por titulo del libro.
- `Almacenamiento local`: Cada nuevo libro obtenido es almacenado de forma automatica en la base de datos local.
- `Nuevas busquedas`: Cada nueva busqueda verifica si el resultado ya existe en la base de datos, en caso contrario solicita los resultados a la api.
- `Tecnologias`: Java, SpringBoot, Maven, PostgreSQL, api Gutendex.

## :gear: Requisitos de entorno
- Java 17 o 23
- Spring Boot 3.2
- Gestor de base de datos PostgreSQL instalado.
- IDE IntelliJ IDEA (opcional, version Community)
- Base de datos "literalura" creada en postgreSQL.
- Configurar los parametros de entorno de acuerdo al archivo <b>application.properties</b> del proyecto java.

## :open_book: Modo de uso
 Al iniciar el proyecto Java se presentara el menu de usuario:

<img width="549" height="267" alt="Imagen de menu principal" src="https://github.com/user-attachments/assets/5ba15886-55f8-4c12-9103-33f44fd20042" />

Opcion 1: Permite hacer nuevas busquedas (si no existe en local se intentara la busqueda en la API)

<img width="598" height="275" alt="Imagen de opcion 1" src="https://github.com/user-attachments/assets/18c40f76-5923-4955-8b8d-35b0c485aea9" />

Opcion 2: Esta opcion permite ver los libros registrados y almacenados en Base de Datos.

<img width="577" height="455" alt="Imagen de opcion 2" src="https://github.com/user-attachments/assets/1cb290ca-21f2-4f78-9afc-72ace03d970d" />

Opcion 3: Esta opcion muestra los autores registrados.

<img width="517" height="316" alt="Imagen de opcion 3" src="https://github.com/user-attachments/assets/2bb6fd22-bf21-4910-b5c5-53f144f667d6" />

Opcion 4: Permite filtrar los autores vivos de un determinado año de los libros almacenados en la base de datos.

<img width="518" height="173" alt="Imagen de opcion 4" src="https://github.com/user-attachments/assets/a61a535b-0e40-4530-9599-f3d1ed5741ca" />

Opcion 5: Permite filtrar los libros almacenados por codigo de idioma.

-Filtro español (es)

<img width="717" height="138" alt="Imagen de opcion 5, español" src="https://github.com/user-attachments/assets/e47605be-39bc-4d64-b277-df325fbe8c1f" />

-Filtro ingles (en)

<img width="734" height="461" alt="Imagen de opcion 5, ingles" src="https://github.com/user-attachments/assets/b6636f24-a1b6-4dce-a17f-f1376ae5f3c9" />

Opcion 6: Permite revisar el detalle y resumen de un libro.

<img width="1540" height="484" alt="Imagen de opcion 6" src="https://github.com/user-attachments/assets/ea501495-e6c0-496c-a0c2-19b9a0388fe4" />

Opcion 0: Cierra la aplicacion.

**Cualquier otra opcion o valor fuera de rango sera indicado por la aplicacion**

