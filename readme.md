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
implementation 'io.github.marchliu:jaskell-rocks:1.1.1'
```

### Gradle Kotlin

```
implementation("io.github.marchliu:jaskell-rocks:1.1.1")
```

### SBT

```sbtshell
libraryDependencies += "io.github.marchliu" % "jaskell-rocks" % "1.1.1"
```

### Apache Ivy

```xml
<dependency org="io.github.marchliu" name="jaskell-rocks" rev="1.1.1" />
```

### Groovy Grap

```groovy
@Grapes(
  @Grab(group='io.github.marchliu', module='jaskell-rocks', version='1.1.1')
)

```

### Leiningen

```clojure
[io.github.marchliu/jaskell-rocks "1.1.1"]
```

### Apache Bluildr

```
'io.github.marchliu:jaskell-rocks:jar:1.1.1'
```

### Maven Central Badge

```
[![Maven Central](https://img.shields.io/maven-central/v/io.github.marchliu/jaskell-rocks.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.marchliu%22%20AND%20a:%22jaskell-rocks%22)
```

### PURL

```
pkg:maven/io.github.marchliu/jaskell-rocks@1.1.1
```


## Update Log

### 1.0.0

Fork from jaskell java8

### 1.1.0

Add join map/flatmap (1 to 8) functions for try.

### 1.1.1

relax join map/flatMap type constraint

### 1.2.0

- add tryIt methods for function types
- make Try Class final
- functions cloud throw Exception in apply call

### 1.3.0

- replace throwable in method's signature to exception 
- add confirm method to re triable function
- add BiConsumer


### 1.3.1

now we can set rest in retry‘s on error handler

### 1.4.0

- add triable interface
- now retriable type implement triable 
- add all and any methods for try
- add async all and any methods, use virtual thread or custom executor

### 1.4.1

make try's join map methods return explicit type 

### 1.5.0

add tuple 2 to 8 and functor methods for them

### 1.6.0

- add curry methods for functionX
- add liftA for tupleN
- add apply for functionX
- uncurry for tupleN

### 1.6.1

- cow methods for tuple
- add item methods for tuple

### 1.6.2

- add tuple interface
  - head method
  - tail method
  - last method
  - butLast method
  - add static methods for tuple construct
  - size method
  - get by pos method
  - toList method
  - toMap method

### 1.6.3

 - fixed flatmap error in try type

### 1.7.0

 - add methods for triable interface make it as lazy try
   - joinMap
   - joinFlatMap
   - map
   - flatMap accept argument typed Function\<T, Try\<U\>\>
   - async methods
 - triable collect method return try object