# Lab 1 - Unit Testing (with JUnit 5)

## Solving the exercise guide

1.1) 
h)

Em ambos os casos, o coverage da totalidade dos testes resultou em 100% do ficheiro TqsStack.java, tanto no feito manualmente, quanto no gerado por AI.
Isto deve-se ao facto de que esta é uma implementação comum, [...]. O nome do ficheiro gerado por IA é "\_TqsStackTest.java\_"

1.2)
c) O report do Jacoco permite visualizar em maior detalhe os Coverage Reports. Permite, em especial, perceber que, neste momento, na classe BoundedSetOfNaturals, são cobertos 60% dos métodos e 55% das linhas. 

d) "What kind of unit test are worth writing for proper validation of BoundedSetOfNaturals?"
Testar a função de adição, do construtor através de Array, do tamanho e da intereseção com diferentes inputs.
A função de hashcode, função de equals e getters e setters são gerados automaticamente, pelo que não vale a pena criar testes unitários.


## What is JUnit?

JUnit is a popular open-source testing framework for Java applications. It provides a simple and easy-to-use API for writing unit tests. JUnit allows developers to write test cases that are executed automatically by a test runner.

JUnit provides a set of annotations that can be used to mark test methods and configure the behavior of the test runner. JUnit also provides a set of assertions that can be used to verify that the code being tested behaves as expected.

### JUnit 5 Annotations

| Annotation | Description |
| --- | --- |
| @Test | Marks a method as a test method. |
| @BeforeAll | Marks a method as a method that should be executed before all test methods. |
| @AfterAll | Marks a method as a method that should be executed after all test methods. |
| @BeforeEach | Marks a method as a method that should be executed before each test method. |
| @AfterEach | Marks a method as a method that should be executed after each test method. |
| @Disabled | Marks a test class or test method as disabled. |
| @DisplayName | Specifies a custom display name for a test class or test method. |

## JaCoCo

JaCoCo is a free Java library for code coverage. It is used to track how many lines, branches, and the cyclomatic complexity of the code are executed during automated tests.

### How to use JaCoCo with Maven

1. In the `pom.xml` file:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>${jacoco-maven-plugin.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Running the test with JUnit will automatically trigger the JaCoCo agent. It will generate a coverage report in binary format within the target directory, specifically in target/jacoco.exec. Of course, we can’t interpret this output directly, but other tools and plugins, like SonarQube, can. The good news is that we can use the jacoco:report goal to generate readable code coverage reports in various formats, such as HTML, CSV, and XML.

2. Create a report

```bash
$ mvn clean test jacoco:report
```

This should generate an HTML report under target/site/jacoco/index.html.
