# Template jdbc con Hikari
. Hikari

. Flyway (para crear versionado de bases de datos):
    
    . En lugar de tener un file grande con todo mi SQL , puedo versionar los cambios.
    . Ver de repetar el nombre de las carpetas para que funcione.
    . Las tablas se crean solas cuando arranca la app (pero 1ero hay que crear la bd a mano).


```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```


. Jdbc: Como me conecto a la DB usando JDBC, y Jdbc Template me permite simplificar 
el uso de JDBC, errores de conexion, me deja proveer sql y extraer los resultados.

. HIKARI: Supuestamente es el mas rapido de los jdbc handler


Este es el Wrapper JDBC
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>
```

Probar curl con lista de objetos de Actores
```json
POST http://localhost:8080/api/v1/movies/add
Content-Type: application/json

{
  "id": "10",
  "name": "peli nueva7",
  "releaseDate": "2021-09-21",
  "actors":[{"id":"2","name":"matrix2", "movieId":  "10"}]
}
```

