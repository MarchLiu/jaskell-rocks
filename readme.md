# Jaskell Rocks

Jaskell Rocks is Latest (today is 21) Java Edition of [Jaskell Core library](https://github.com/MarchLiu/jaskell-core).
It rewritten completed by new Java style. Support modern java language.

It includes a parsec combinators library , a SQL expressions library and a arithmetic expressions parser.


[![Maven Central](https://img.shields.io/maven-central/v/io.github.marchliu/jaskell-rocks.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.marchliu%22%20AND%20a:%22jaskell-rocks%22)

## INSTALLATION

### Maven

```xml
<dependency>
  <groupId>io.github.marchliu</groupId>
  <artifactId>jaskell-rocks</artifactId>
  <version>1.1.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.marchliu:jaskell-rocks:1.1.0'
```

### Gradle Kotlin

```
implementation("io.github.marchliu:jaskell-rocks:1.1.0")
```

### SBT

```sbtshell
libraryDependencies += "io.github.marchliu" % "jaskell-rocks" % "1.1.0"
```

### Apache Ivy

```xml
<dependency org="io.github.marchliu" name="jaskell-rocks" rev="1.1.0" />
```

### Groovy Grap

```groovy
@Grapes(
  @Grab(group='io.github.marchliu', module='jaskell-rocks', version='1.1.0')
)

```

### Leiningen

```clojure
[io.github.marchliu/jaskell-rocks "2.0.1"]
```

### Apache Bluildr

```
'io.github.marchliu:jaskell-rocks:jar:1.1.0'
```

### Maven Central Badge

```
[![Maven Central](https://img.shields.io/maven-central/v/io.github.marchliu/jaskell-rocks.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.marchliu%22%20AND%20a:%22jaskell-rocks%22)
```

### PURL

```
pkg:maven/io.github.marchliu/jaskell-rocks@1.1.0
```


## Update Log

### 1.0.0

Fork from jaskell java8

### 1.1.0

Add join map/flatmap (1 to 8) functions for try.
