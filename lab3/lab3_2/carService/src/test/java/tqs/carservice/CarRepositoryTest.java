package tqs.carservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.carservice.model.Car;

import static org.assertj.core.api.Assertions.assertThat;
import tqs.carservice.repositories.CarRepository;
import java.util.List;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindBycarId_thenReturncar() {
        Car car = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(car);

        Car found = carRepository.findByCarId(car.getCarId());

        assertThat(found.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    void whenFindAll_thenReturnAllcars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        entityManager.persist(car1);
        entityManager.persistAndFlush(car2);

        List<Car> allcars = carRepository.findAll();
        assertThat(allcars).hasSize(2).extracting(Car::getMaker).containsOnly(car1.getMaker(), car2.getMaker());
    }
}


