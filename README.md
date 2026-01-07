# PacmanGPS

[![CI/CT](https://github.com/NandoLozano/pacmanGPS/actions/workflows/ci-ct.yml/badge.svg)](https://github.com/NandoLozano/pacmanGPS/actions/workflows/ci-ct.yml)
![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)

Juego de Pac-Man desarrollado en Java utilizando Swing para la interfaz gráfica, con soporte para múltiples niveles de dificultad, habilidades especiales y pruebas unitarias completas.

## Características del Juego

- **Menú Principal**: Interfaz de inicio con opciones para jugar, seleccionar habilidades y salir
- **Niveles de Dificultad**: Tres niveles de dificultad (Fácil, Normal, Difícil) que afectan la velocidad de los fantasmas
- **Habilidades Especiales**: 
  - Tres vidas (modo clásico)
  - Impulso de velocidad para Pac-Man
  - Power-ups adicionales en el tablero
- **Laberinto**: Tablero con paredes, puntos coleccionables y power-ups
- **Movimiento**: Control de Pac-Man mediante teclas de flecha
- **Fantasmas**: Enemigos con movimiento automático e inteligencia adaptativa
- **Sistema de Puntuación**: Puntos por comer pellets y bonificaciones
- **Condiciones de Victoria/Derrota**: 
  - Victoria: Recolectar todos los puntos del nivel
  - Derrota: Ser atrapado por un fantasma
- **Interfaz Gráfica**: UI modular y responsive con Swing

## Estructura de Archivos

```
pacmanGPS/
├── .github/
│   └── workflows/
│       └── ci-ct.yml              # Workflow de CI/CT con GitHub Actions
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Game.java          # Clase principal: punto de entrada del juego
│   │       ├── MainMenu.java      # Menú principal del juego
│   │       ├── Board.java         # Lógica y renderizado del tablero
│   │       ├── Pacman.java        # Lógica y renderizado de Pac-Man
│   │       ├── Ghost.java         # Lógica y renderizado de los fantasmas
│   │       ├── Direction.java     # Enum para las direcciones de movimiento
│   │       ├── GameDifficulty.java      # Enum para niveles de dificultad
│   │       ├── GameAbility.java         # Enum para habilidades especiales
│   │       ├── DifficultySelection.java # Panel de selección de dificultad
│   │       └── AbilitySelection.java    # Panel de selección de habilidades
│   └── test/
│       └── java/
│           ├── GameTest.java            # Pruebas unitarias para Game
│           ├── MainMenuTest.java        # Pruebas unitarias para MainMenu
│           ├── BoardTest.java           # Pruebas unitarias para Board
│           ├── PacmanTest.java          # Pruebas unitarias para Pacman
│           ├── GhostTest.java           # Pruebas unitarias para Ghost
│           ├── DirectionTest.java       # Pruebas unitarias para Direction
│           ├── GameDifficultyTest.java  # Pruebas unitarias para GameDifficulty
│           ├── GameAbilityTest.java     # Pruebas unitarias para GameAbility
│           ├── DifficultySelectionTest.java # Pruebas para DifficultySelection
│           └── AbilitySelectionTest.java    # Pruebas para AbilitySelection
├── pom.xml                        # Configuración de Maven
├── .gitignore                     # Archivos ignorados por Git
└── README.md                      # Este archivo
```

## Requisitos

- **Java 17** o superior
- **Maven 3.8+** para gestión de dependencias y construcción
- (Opcional) IDE como IntelliJ IDEA, Eclipse, VSCode, etc.

## Compilación y Ejecución

### Con Maven

Desde la terminal, navega al directorio raíz del proyecto y ejecuta:

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar el juego
mvn exec:java -Dexec.mainClass="Game"
```

### Con IDE

Importa el proyecto Maven en tu IDE preferido y ejecuta la clase `Game.java` como aplicación Java.

## Controles

- **Flechas del teclado** para mover a Pac-Man:
  - ⬆️ Arriba
  - ⬇️ Abajo
  - ⬅️ Izquierda
  - ➡️ Derecha

## Testing y Cobertura

El proyecto cuenta con pruebas unitarias completas usando JUnit 5 y Mockito:

```bash
# Ejecutar todas las pruebas
mvn test

# Generar reporte de cobertura con JaCoCo
mvn jacoco:report

# Ver el reporte de cobertura
# El reporte HTML estará en: target/site/jacoco/index.html
```

El proyecto mantiene una cobertura de código del **100%** para asegurar la calidad del código.

## GitHub Actions

El repositorio incluye integración continua y pruebas continuas (CI/CT) mediante GitHub Actions:

### Workflow CI/CT (`ci-ct.yml`)

**Activación**: Se ejecuta automáticamente en cada `push` y `pull_request` a las ramas `main` y `develop`.

**Proceso**:
1. **Build**: Compila el proyecto con Maven (`mvn clean install -DskipTests`)
2. **Test**: Ejecuta todas las pruebas unitarias (`mvn test`)
3. **Coverage**: Genera reporte de cobertura con JaCoCo (`mvn jacoco:report`)
4. **Artifacts**: Publica reportes de cobertura y resultados de pruebas como artefactos
5. **Notificación**: Comenta en los PRs con resultados y actualiza los checks de GitHub
6. **Cleanup**: Limpia archivos temporales al finalizar

**Artefactos generados** (disponibles por 30 días):
- `jacoco-coverage-report`: Reporte de cobertura de código (HTML + jacoco.exec)
- `test-results`: Resultados de las pruebas unitarias (XML)

**Estado**: Los badges en la parte superior del README muestran el estado actual del build y la cobertura.

## Extensiones Futuras

- Mejorar la IA de los fantasmas con algoritmos de pathfinding
- Añadir más niveles y mapas personalizables
- Implementar efectos de sonido y música de fondo
- Añadir más power-ups y funcionalidades clásicas del juego
- Implementar sistema de puntuaciones altas (high scores)
- Agregar modo multijugador

## Autor

Proyecto desarrollado por [paberlo] y colaboradores.

---

¡Disfruta programando y jugando Pac-Man! 🎮👾
