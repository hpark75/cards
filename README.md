# Tarjetas de Credito

## Prerrequisitos

1. Crear certificados para la generacion de token JWT y firma:

```
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

Con estos comandos obtenemos los archivos: private.pem y public.pem que vamos a necesitar para el token JWT, vamos a colocarlos en la carpeta src/main/resources/certs/

## Pasos

Para correr los tests:
./gradlew clean test --info

Para levantar la aplicación:
./gradlew clean bootRun --info

La aplicacion por consola es ConsoleApplication, se puede correr del IntelliJ una vez levantado el backend.

