package tqs.carservice.services;

import org.springframework.stereotype.Service;
import tqs.carservice.model.Car;
import tqs.carservice.repositories.CarRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {
    private final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findById(id);
    }

}
