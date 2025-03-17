# Questions

## a) Identify a couple of examples that use AssertJ expressive methods chaining.

Some examples found on the tests are:

- A_EmployeeRepositoryTest
  - ``` assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName()); ```

- B_EmployeeService_UnitTest
  - ```assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());```

- E_EmployeeRestControllerTemplateIT
  - ```assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");```

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

#### C_EmployeeController_WithMockServiceTest.java:

In ``C_EmployeeController_WithMockServiceTest``, the ``EmployeeService`` is mocked to simulate the behavior of the service layer. This allows the ``EmployeeRestController`` controller to be tested in isolation without relying on the actual service implementation.

```java
@MockBean
    private EmployeeService service;


    @Test
    void whenPostEmployee_thenCreateEmployee( ) throws Exception {
        Employee alex = new Employee("alex", "alex@deti.com");

        when( service.save(Mockito.any()) ).thenReturn( alex);

        mvc.perform(
                post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(alex)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("alex")));

        verify(service, times(1)).save(Mockito.any());

    }
```

- This test verifies that the `getAllEmployees` method in the controller correctly returns a list of employees.

- The service's `getAllEmployees` method is mocked to return a list of employees, so the test does not need to interact with the actual service or database.

## c) What is the difference between standard @Mock and @MockBean?

The @Mock and @MockBean annotations are both used for creating mock objects in testing. Let's see the differences between them:
- @Mock is used to create a mock object in unit tests. Apart from making the code more readable, @Mock makes it easier to find the problem mock in case of a failure, as the name of the field appears in the failure message.
  - Use: When you want to mock dependencies for a class under test without involving Spring.
- We can use the @MockBean to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context. If no bean of the same type is defined, a new one will be added.
  - Use: When you need to mock a bean that is part of the Spring context (e.g., a service or repository used by a controller).

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The file application-integrationtest.properties is a configuration file used in Spring Boot applications to define specific properties for integration testing. It allows you to customize the behavior of your application during integration tests, such as configuring database connections, server ports, or other environment-specific settings.

It may be used in different conditions, such as:
- When running integration tests (e.g., using @SpringBootTest), Spring Boot automatically loads this file if it is placed in the src/test/resources directory.
- When the file is loaded while the integrationtest profile is active.
- When it is used while connecting to a dedicated test database (e.g., a local MySQL instance or a Docker container) instead of the production or development database.

Excerpt from the provided application-integrationtest.properties file:
```
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo
```
- `spring.datasource.url`: Specifies the URL of the MySQL database running on port 33060.
- `spring.jpa.hibernate.ddl-auto=create-drop`: Configures Hibernate to create and drop the database schema automatically for each test run.
- `spring.datasource.username and spring.datasource.password`: Provide credentials for accessing the database.

## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

| Feature  | Strategy C (`C_EmployeeController_WithMockServiceTest`)  | Strategy D (`D_EmployeeRestControllerIT`)  | Strategy E (`E_EmployeeRestControllerTemplateIT`)  |
|---|---|---|---|
| Type of Test  | Unit Test (controller layer)  | Integration Test (controller + repository)  | Integration Test (full application)  |
| Tools  | `@WebMvcTest`, `MockMvc`, `@MockBean`  | `@SpringBootTest`, `MockMvc`, `@AutoConfigureTestDatabase`  | `@SpringBootTest`, `TestRestTemplate`, `@AutoConfigureTestDatabase`  |
| Scope  | Controller layer only  | Controller + Repository layers  | Full application (HTTP server, controller, service, repository)  |
| Database | No database (mocked service)  | In-memory H2 database  | In-memory H2 database |
| Use Case | Test HTTP layer (request/response) | Test integration between controller and repository | End-to-end testing of the API |
| Speed | Fast (no Spring context or database) | Moderate (Spring context + in-memory database) | Slower (full application + HTTP server)

- **Strategy C**: Use for unit testing the controller layer in isolation. Fast and focused on HTTP behavior.
- **Strategy D**: Use for integration testing the controller and repository layers. Verifies that the API works with real components.
- **Strategy E**: Use for end-to-end testing the full application. Simulates real client-server interactions and production-like behavior.