package tqs.carservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tqs.carservice.model.Car;
import tqs.carservice.repositories.CarRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import tqs.carservice.services.CarManagerService;

class CarManagerServiceTest {

    private CarRepository carRepository = Mockito.mock(CarRepository.class);
    private CarManagerService carManagerService = new CarManagerService(carRepository);

    @Test
    void whenGetAllcars_thenReturnAllcars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        List<Car> allcars = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(allcars);

        List<Car> found = carManagerService.getAllCars();
        assertThat(found).hasSize(2).extracting(Car::getMaker).contains("Toyota", "Honda");
    }

    @Test
    void whenSavecar_thenReturncar() {
        Car car = new Car("Toyota", "Corolla");
        when(carRepository.save(Mockito.any())).thenReturn(car);

        Car savedcar = carManagerService.save(car);
        assertThat(savedcar.getMaker()).isEqualTo("Toyota");
    }

    @Test
    void whenGetcarDetails_thenReturncar() {
        Car car = new Car("Toyota", "Corolla");
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> found = carManagerService.getCarDetails(1L);
        assertThat(found).isPresent().hasValueSatisfying(c -> c.getMaker().equals("Toyota"));
    }
}