# AREP-TALLER04


Para este taller los estudiantes deberán construir un servidor Web (tipo Apache) en Java. El servidor debe ser capaz de entregar páginas html e imágenes tipo PNG. Igualmente el servidor debe proveer un framework IoC para la construcción de aplicaciones web a partir de POJOS. Usando el servidor se debe construir una aplicación Web de ejemplo y desplegarlo en Heroku. El servidor debe atender múltiples solicitudes no concurrentes.

Para este taller desarrolle un prototipo mínimo que demuestre capcidades reflexivas de JAVA y permita por lo menos cargar un bean (POJO) y derivar una aplicación Web a partir de él. Debe entregar su trabajo al final del laboratorio.

## **Sugerencia**

1. Para su primera versión cargue el POJO desde la línea de comandos , de manera similar al framework de TEST. Es decir pásela como parámetro cuando invoke el framework. Ejemplo de invocación:
```
java -cp target/classes co.edu.escuelaing.reflexionlab.MicroSpringBoot co.edu.escuelaing.reflexionlab.FirstWebService
```

2. Atienda la anotación @ResuestMapping publicando el servicio en la URI indicada, limítelo a tipos de retorno String,  ejemplo:
```java
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}
```
3. En su versión final el framework debe explorar el directorio raiz (o classpath) buscando classes con una anotación que indique que son componentes, por ejemplo @Component, y cargar todos los que tengan dicha anotación. Así no tendrá que especificarlos siempre en la línea de comandos.

## **Prerrequisitos**

-   [Git](https://git-scm.com/downloads) - Sistema de control de versiones
-   [Maven](https://maven.apache.org/download.cgi) - Gestor de dependencias
-   [Java 8](https://www.java.com/download/ie_manual.jsp) - Entorno de desarrollo
-   [Intellij Idea](https://www.jetbrains.com/es-es/idea/download/) (Opcional)


## **Instrucciones de ejecución local**

0. Desde cmd clonar el repositorio

```git
git clone https://github.com/Rincon10/AREP-TALLER04.git
```


1. Ubicarse en la carpeta AREP-TALLER04 y borraremos todas las dependencias y modulos que puedan exisitir de los binarios del proyecto.
```maven
mvn clean
```

2. Realizamos la compilación y empaquetamiento del proyecto
```maven
mvn package -U
```

3. Ejecutamos el proyecto
```maven
mvn exec:java -Dexec.mainClass="edu.escuelaing.arep.RunMyServer"
```

<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/running.jpg" />




4. Generando la documentación del proyecto
```mvn
mvn javadoc:javadoc
```
La documentación se generara en la ruta
```
target/site/apidocs/index.html
```

<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/javaDoc.jpg" />

<br />


## **Ejecutando pruebas**
Para la ejecución de pruebas

```mvn
mvn test
```

## **Carga de componentes de manera dinamica**

Para que nuestro framework casero detectara que componentes cargar, decidimos colocar la anotación **@component**, de tal manera que se buscaran todas las clases que tengan esa etiqueta.

<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/searchingComponents.jpg" />

## **Carga de página por defecto**

```
http://localhost:35000/
```
<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/defaultPage.jpg" />

## **Carga de página del controllador hello**

```
http://localhost:35000/hello
```
<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/hello.jpg" />

## **Carga de página 404**

```
http://localhost:35000/dsds
```
<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/404.jpg" />

## **Carga de página Status**

```
http://localhost:35000/Services/status
```
<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/status.jpg" />

## **Carga de página Date**

```
http://localhost:35000/Services/date
```
<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/date.jpg" />

## **Carga de imagen**

```
http://localhost:35000/public/image
```

<img src="https://github.com/Rincon10/AREP-TALLER04/blob/master/resources/img/img.jpg" />

## **Construido con**
  -   [Maven](https://maven.apache.org/download.cgi) - Gestor de dependencias

## **Autor**

-   [Iván Camilo](https://github.com/Rincon10).