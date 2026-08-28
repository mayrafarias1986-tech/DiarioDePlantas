Proyecto: Diario de Plantas (DiarioDePlantas)

Aplicacion movil desarrollada para Android en Kotlin y Jetpack Compose, disenada para llevar un registro detallado de plantas, su tipo, frecuencia de riego, geolocalizacion GPS y galeria fotografica.

Arquitectura y Patron de Diseno
La aplicacion sigue el patron MVVM (Model-View-ViewModel) recomendado por Google:
- UI (View): Pantallas construidas con Jetpack Compose que observan los estados.
- ViewModel: Gestiona la logica de negocio y expone los datos de forma reactiva (StateFlow).
- Repository: Centraliza el acceso a las fuentes de datos.
- Room Database: Capa de persistencia local para almacenar la informacion de las plantas de manera segura en el dispositivo.

Tecnologias y Librerias Utilizadas
- Lenguaje: Kotlin / Kotlin DSL (build.gradle.kts)
- Interfaz de Usuario: Jetpack Compose y Material 3
- Base de Datos: Room (SQLite local)
- Carga de Imagenes: Coil
- Ubicacion GPS: Google Play Services FusedLocationProviderClient
## Capturas de Pantalla de la Aplicación
<img width="493" height="833" alt="image" src="https://github.com/user-attachments/assets/de9039c2-0c14-46cf-a9aa-bc151ddad6f8" />
<img width="497" height="846" alt="image" src="https://github.com/user-attachments/assets/117f44e8-91da-4879-a046-d058504e1cb0" />
