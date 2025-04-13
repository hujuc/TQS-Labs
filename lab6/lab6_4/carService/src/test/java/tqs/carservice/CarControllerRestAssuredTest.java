package tqs.carservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.carservice.controllers.CarController;
import tqs.carservice.model.Car;
import tqs.carservice.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetAllCars_thenReturnJsonArray() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Peugeot", "206");
        Car car3 = new Car("Citroen", "C3");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(carManagerService.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasSize(3))
                .body("[0].maker", is("Toyota"))
                .body("[1].maker", is("Peugeot"))
                .body("[2].maker", is("Citroen"));

        verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    void whenGetCarById_thenReturnCar() {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars/1")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("maker", is("Toyota"))
                .body("model", is("Corolla"));

        verify(carManagerService, times(1)).getCarDetails(1L);
    }

    @Test
    void whenGetInvalidCarId_thenReturn404() {
        when(carManagerService.getCarDetails(99L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars/99")
                .then()
                .statusCode(404);

        verify(carManagerService, times(1)).getCarDetails(99L);
    }

    @Test
    void whenCreateCar_thenReturnSavedCar() {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.save(any(Car.class))).thenReturn(car);

        RestAssuredMockMvc
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(car)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("maker", is("Toyota"))
                .body("model", is("Corolla"));

        verify(carManagerService, times(1)).save(any(Car.class));
    }
}