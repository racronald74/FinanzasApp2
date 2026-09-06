# FinanzasApp2

## Descripción

FinanzasApp2 es una aplicación móvil desarrollada para Android que permite gestionar de manera sencilla
los ingresos y gastos personales. La aplicación cuenta con una interfaz organizada que facilita el registro, 
consulta y seguimiento de los movimientos financieros.

El proyecto fue desarrollado como parte de la evidencia GA8-220501096-AA2-EV02, aplicando los requerimientos 
definidos para el desarrollo de módulos móviles bajo la plataforma Android.

## Requisitos

Para abrir y ejecutar el proyecto se requiere:

- Android Studio.
- Android SDK.
- JDK compatible con la versión de Android Studio utilizada.
- Un dispositivo Android físico o un emulador configurado.
- Sistema operativo Windows, Linux o macOS.

Para instalar la APK directamente en un dispositivo Android:

- Dispositivo con Android compatible.
- Permitir la instalación de aplicaciones desde fuentes desconocidas cuando sea necesario.

## Tecnologías utilizadas

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Material Design.
- Android SDK.
- Git y GitHub.
- Arquitectura organizada por capas y paquetes.
- ViewModel para la gestión de datos de la aplicación.

## Funcionalidades

La aplicación cuenta con los siguientes módulos y funcionalidades:

### Módulo de ingresos

- Registro de nuevos ingresos.
- Visualización del total de ingresos.
- Visualización del saldo disponible.
- Historial de ingresos registrados.
- Consulta de información de cada ingreso.
- Eliminación de registros.
- Edición de registros.

### Módulo de gastos

- Registro de nuevos gastos.
- Visualización del historial de gastos.
- Consulta de información de cada gasto.
- Eliminación de registros.
- Edición de registros.

### Navegación

- Navegación entre las pantallas de ingresos y gastos.
- Barra de navegación inferior.
- Pantalla de carga personalizada.
- Interfaz adaptada para dispositivos Android.

## Almacenamiento

La aplicación utiliza una estructura de datos organizada mediante modelos, repositorios y componentes 
de almacenamiento local.

Los datos registrados por la aplicación se manejan localmente, permitiendo trabajar con los movimientos 
financieros sin requerir conexión permanente a un servidor externo.

## Instrucciones para clonar el repositorio

1. Instalar Git en el computador.

2. Abrir una terminal o Git Bash.

3. Ejecutar:

// ```bash
git clone https://github.com/racronald74/FinanzasApp2.git //

4. Ingresar a la carpeta del proyecto:

cd FinanzasApp2

5.	Abrir la carpeta del proyecto desde Android Studio.

6.	Esperar a que Android Studio sincronice Gradle y descargue las dependencias necesarias.

7.	Seleccionar un dispositivo físico o crear un emulador Android.

8.	Ejecutar el proyecto desde Android Studio.

## Instrucciones para cargar la APK

La APK funcional se encuentra ubicada directamente en la carpeta raíz del repositorio:

/FinanzasApp2

Para instalarla en un dispositivo Android:

1.	Copiar el archivo app-debug.apk al dispositivo Android.

2.	Abrir el archivo APK desde el dispositivo.

3.	Si Android solicita autorización, permitir la instalación desde esta fuente.

4.	Seleccionar la opción de instalar.

5.	Una vez finalizada la instalación, abrir FinanzasApp2.

Generación de la APK

Para generar nuevamente la APK desde Android Studio:

1.	Abrir el proyecto FinanzasApp2.

2.	Seleccionar:

Build > Build Bundle(s) / APK(s) > Build APK(s)

3.	Esperar a que finalice la compilación.

4.	Android Studio mostrará la ubicación del archivo generado.

La APK de depuración se encontrará normalmente en:

app/build/outputs/apk/debug/app-debug.apk

