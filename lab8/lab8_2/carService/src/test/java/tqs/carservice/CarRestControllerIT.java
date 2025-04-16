package tqs.carservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.carservice.model.Car;
import tqs.carservice.repositories.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenPostcar_thenCreatecar() {
        Car car = new Car("Toyota", "Corolla");
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", car, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getMaker()).isEqualTo("Toyota");
    }

    @Test
    void whenGetcars_thenReturnAllcars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        carRepository.save(car1);
        carRepository.save(car2);

        ResponseEntity<Car[]> response = restTemplate.getForEntity("/api/cars", Car[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2).extracting(Car::getMaker).contains("Toyota", "Honda");
    }
}