# testmeep

3 carpetas 

- jhipster-registry: Eureka server. Spring Cloud
- backendtestgate: gateway del microservicio
- backendtest: microservicio


## Instalación

1 - Clonar el proyecto en una carpeta
2 - Arrancar jhipster-registry (./mvnw)
3 - Arrancar los proyectos de microservicio (./mvnw)
4 - Acceder a servicio gateway para probar (admin/admin)
5 - Acceder al menú entities/Position -> la propiedad result es el JSON actualizado



## Solución

->Consulta desde gateway <-> cache <-> database (h2 en dev) <-> microservicio <- servicio Meep

- JHipster. Creado el entorno con JHipster para generar rápidamente una arquitectura preconfigurada con Spring Boot, JUnit, Spring Data, Spring Cloud y Eureka
- El servicio se conecta por Scheduler cada 30 segundos al servicio
- Compara el resultado y guarda en BBDD
- Para servir el resultado, se realiza por BBDD con cacheo mediante HazelCast y seguridad JWT
- Gateway: uso de Angular para mostrar los resultados del servicio
- Almacena cada registro con una enumeración (NUEVO, MODIFICADO, BORRADO, NINGÚN CAMBIO).
- Configurada un timeout y readtimeout sobre el servicio.

- Tecnologías: 
	· JWT: Seguridad del servicio por token (admin/admin)
	· HazelCast. Por escalabilidad. Guardamos el contenido en BBDD en el microservicio, después, los datos se servirán desde memoria con caché sincronizada.
	· JHipster: para generar el entorno rápidamente y ejecutar los servicios.


## Posibles mejoras:
- Para servir, el cacheo en memoria se hace con HazelCast, la configuración está por defecto. Podría sustituirse por Kafka.
- Monitorización de los servicios externos. Si el servicio externo cae por mucho tiempo, los datos podrían resultar falseados, no hay monitorización.