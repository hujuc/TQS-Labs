package tqs.carservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.carservice.model.Car;
import tqs.carservice.repositories.CarRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CarRestControllerRestAssuredIT {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    int port;

    @Autowired
    private CarRepository carRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void whenPostCar_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla");

        given()
            .contentType(ContentType.JSON)
            .body(car)
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .body("maker", equalTo("Toyota"))
            .body("model", equalTo("Corolla"));
    }

    @Test
    void whenGetCars_thenReturnAllCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        carRepository.save(car1);
        carRepository.save(car2);

        given()
        .when()
            .get("/api/cars")
        .then()
            .statusCode(200)
            .body("size()", equalTo(2))
            .body("maker", hasItems("Toyota", "Honda"));
    }

    @Test
    void whenGetCarById_thenReturnCar() {
        Car car = new Car("Toyota", "Corolla");
        car = carRepository.save(car);

        given()
        .when()
            .get("/api/cars/" + car.getCarId())
        .then()
            .statusCode(200)
            .body("maker", equalTo("Toyota"))
            .body("model", equalTo("Corolla"));
    }
} 