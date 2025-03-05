# Questions

## a) Identify a couple of examples that use AssertJ expressive methods chaining.

Some examples found on the tests are:

```
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```
> This is found in the Test Class: "A_EmployeeRepositoryTest", on line 75

```
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
```
> This is found in the Test Class: "B_EmployeeService_UnitTest", on line 112

```
assertThat(found).extracting(Employee::getName).containsOnly("bob");
```
> This is found in the Test Class: "D_EmployeeRestControllerIT", on line 58

```
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```
> This is found in the Test Class: "E_EmployeeRestControllerTemplateIT", on line 67

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

An example of this is found in the Test Class: "B_EmployeeService_UnitTest". In this class unit tests are achived by mocking the repository using Mockito:

```java
@Mock( lenient = true)
private EmployeeRepository employeeRepository;

@InjectMocks
private EmployeeServiceImpl employeeService;

@BeforeEach
public void setUp() {

    //these expectations provide an alternative to the use of the repository
    Employee john = new Employee("john", "john@deti.com");
    john.setId(111L);

    Employee bob = new Employee("bob", "bob@deti.com");
    Employee alex = new Employee("alex", "alex@deti.com");

    List<Employee> allEmployees = Arrays.asList(john, bob, alex);

    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
    Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
    Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
    Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
    Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
}
```

## c) What is the difference between standard @Mock and @MockBean?

The annotation @Mock is used to create a mock object within a test class, while the annotation @MockBean is used when we want to mock or replace Spring beans in the application context. The @MockBean annotation is used to add mock objects to the Spring application context, which make it useful when we want to replace a bean with a mock in the application context.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

This file is used to configure the application properties for integration tests. It is used when we want to run integration tests, and it configures the application properties for these tests. It overrides the application properties defined in the application.properties file.

## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C is a unit test while D and E are integration tests. The approach used in C mocks all the service implementations and the access to the API.
D mocks the web environment and the API, encapsulating all the application beans. E uses all the real components, accessing the API through a direct request instead of a mock.