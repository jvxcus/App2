# Informe de Diseño

## 1. Arquitectura del Proyecto

El sistema se desarrolló siguiendo los principios de la Programación Orientada a Objetos (POO) y se organizó en los siguientes paquetes:

- `models`: contiene las clases de dominio como `Cultivo`, `Parcela`, `Actividad`, `ElementoAgricola` y `EstadoCultivo`. Estas clases encapsulan los datos y operaciones propias del negocio.
- `services`: incluye los gestores `GestorCultivos`, `GestorParcelas` y `GestorActividades`, encargados de orquestar la lógica de negocio.
- `utils`: contiene `CSVParser`, responsable de la conversión entre objetos Java y el archivo CSV.
- `ui`: contiene `App2` (punto de entrada) y `Menu`, que gestiona la interacción por consola.

### Relación entre clases

- `ElementoAgricola` es una clase abstracta que define `nombre`, `fechaSiembra` y `estado`. Es extendida por `Cultivo`.
- `Cultivo` contiene una lista de `Actividad`.
- `Parcela` contiene una lista de `Cultivo`.
- Se utilizan `ArrayList` para modelar composiciones, y `HashMap` temporalmente para generar parcelas desde cultivos.

## 2. Justificación de Diseño

- Se utilizó herencia con `ElementoAgricola` para factorizar atributos comunes de los elementos agrícolas.
- Las listas (`ArrayList`) se usaron por su eficiencia en operaciones secuenciales y su compatibilidad con recorridos.
- El parser se separó en una clase `utils/CSVParser` para respetar el principio de responsabilidad única.
- Se organizó el código en paquetes para favorecer la mantenibilidad, la navegación por IDE y la claridad del dominio.

## 3. Modificadores de Acceso y Encapsulamiento

- Todos los atributos de clases están declarados como `private`.
- Se exponen métodos `public` del tipo getter/setter para acceder a los datos.
- Se aplicó correctamente encapsulamiento: la modificación de estructuras internas (como `actividades`) se realiza solo mediante métodos públicos seguros.

## 4. Reflexiones Finales
- Durante el desarrollo de este proyecto logramos afianzar los conceptos fundamentales de programación orientada a objetos, aplicando principios como la herencia, encapsulamiento, y separación de responsabilidades. La estructura modular que definimos —con paquetes como models, services, utils y ui— nos permitió mantener un diseño claro, reutilizable y fácil de mantener.

Uno de los mayores desafíos que enfrentamos fue el manejo correcto del formato decimal en la escritura del archivo CSV. Descubrimos que nuestro sistema sobreescribía los valores numéricos usando coma decimal, lo que generaba problemas de compatibilidad. Esto nos llevó a investigar cómo controlar este comportamiento, y finalmente implementamos DecimalFormat con Locale.US, lo cual nos permitió tener control total sobre el formato de salida. Fue una experiencia muy formativa porque involucró tanto aspectos técnicos como de diseño.

También aprendimos mucho trabajando con estructuras de datos como ArrayList y HashMap, y vimos en la práctica el valor de utilizar clases abstractas para factorizar comportamiento común, como hicimos con ElementoAgricola.

Durante el proceso, utilizamos ChatGPT como herramienta de apoyo para resolver dudas puntuales, sugerencias de diseño, y revisión de errores difíciles de rastrear. Siempre validamos cada respuesta, adaptándola a nuestras necesidades y asegurándonos de comprender lo que estábamos haciendo. En ningún momento reemplazó nuestro trabajo, sino que lo complementó como una guía técnica.

En general, este proyecto nos ayudó a conectar teoría y práctica, a trabajar de forma más ordenada y profesional, y a enfrentar problemas reales de desarrollo de software con criterio técnico.

### 4.1 Desafíos

- Uno de los principales desafíos fue manejar el formato decimal y evitar que el archivo `CSV` se escribiera con coma decimal (`43,20`) en vez de punto (`43.20`).
- También se debió lidiar con entradas de datos inconsistentes, como fechas o estados mal escritos, lo que se resolvió con validaciones y normalizaciones.

### 4.2 Lectura y Escritura de CSV

- Se creó una clase `CSVParser` dedicada a interpretar y generar líneas de texto según el formato definido.
- Para asegurar el punto decimal, se usó `DecimalFormat` con `Locale.US`.
- Se evitó que las comas decimales causaran errores en el parser al reemplazarlas por puntos al leer.

### 4.3 Aprendizajes

- La importancia de separar responsabilidades: modelo vs lógica vs I/O.
- Buenas prácticas de encapsulamiento y modularidad.
- El valor de usar herramientas como Git y commits equilibrados para seguir el trabajo colaborativo.

### 4.4 Uso de IA

- Se utilizó ChatGPT para asistencia en la refactorización de clases, resolución de errores de parsing, y estructura del proyecto.
- Las respuestas se validaron manualmente mediante pruebas y revisiones de código.
- La IA fue usada como apoyo técnico, sin reemplazar la comprensión ni el desarrollo del equipo.

---

**Integrantes del grupo:**

- [Martín Aguilar] 
- [Santiago Leigh] 
- [Javiera Riquelme] 
