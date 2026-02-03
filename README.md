
# Ejercicio: PrimeFinder con Sincronización y Wait/Notify

## Objetivo
Modificar el programa PrimeFinder para pausar periódicamente todos los hilos trabajadores, mostrar estadísticas y reanudar la ejecución mediante entrada del usuario.

## Requisitos
- Pausar todos los hilos trabajadores cada `t` milisegundos
- Mostrar cantidad de números primos encontrados
- Esperar entrada del usuario (ENTER) para reanudar
- Usar `synchronized`, `wait()` y `notify()`/`notifyAll()` en un monitor compartido
- Evitar busy-waiting

## Conceptos Clave
- **Monitor compartido**: Objeto común para sincronización
- **wait()**: Libera el lock y pausa el hilo
- **notifyAll()**: Despierta todos los hilos en espera

## Implementación 

### Mecanismo de Sincronización
Se utiliza `pauseLock` como objeto monitor compartido para coordinar la pausa/reanudación de hilos:

- **En `PrimeFinderThread.run()`**: Cada iteración verifica el estado `paused`. Si es verdadero, el hilo espera llamando a `pauseLock.wait()`, liberando el lock.
- **`pauseThread()`**: Establece `paused = true` y llama `notifyAll()` para despertar hilos en espera.
- **`resumeThread()`**: Establece `paused = false` y llama `notifyAll()` nuevamente.s

### Flujo en Control
1. Inicia 3 hilos `PrimeFinderThread` que buscan primos
2. Cada 1 segundo pausa todos los hilos
3. Recopila estadísticas llamando a `getPrimesFound()`
4. Espera entrada del usuario
5. Reanuda la ejecución

### Ventajas
- Los hilos dormidos no consumen CPU
- Coordinación con `wait()`/`notifyAll()`
- `volatile boolean paused` asegura visibilidad de cambios



## Referencias
Oracle. *Synchronization* [Tutorial]. Java Documentation. Retrieved from https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
