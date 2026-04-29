# Softwareentwicklungsprojekt

Dieses Projekt implementiert ein Gantt-Diagramm zur Visualisierung von Planungsdaten (z. B. Fertigungsplan, Dienstplan, Bildfahrplan).

## Voraussetzungen

* Git
* Docker (empfohlen)

## Projekt klonen

```bash
git clone https://github.com/Jakoobly/Softwareenwicklungsprojekt.git
cd Softwareenwicklungsprojekt
```

## Projekt mit Docker ausführen

### 1. Docker-Image bauen

```bash
docker build -t sep-project .
```

### 2. Container starten

```bash
docker run --rm sep-project
```

Damit wird das Projekt im Container gebaut und getestet.

## Hinweise

* Die grafische Oberfläche (JavaFX) wird aktuell **nicht im Docker-Container angezeigt**.
* Docker dient hier zur einheitlichen Build- und Testumgebung.
* Für die GUI-Ausführung bitte das Projekt lokal in einer IDE (z. B. IntelliJ) starten.

## Technologien

* Java
* JavaFX
* Maven
* Docker

## Autoren

Jakob, Roque, Rafael, Robert, Annika
