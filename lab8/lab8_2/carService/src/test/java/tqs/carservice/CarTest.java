package tqs.carservice;

import org.junit.jupiter.api.Test;
import tqs.carservice.model.Car;
import static org.assertj.core.api.Assertions.assertThat;

class CarTest {

    @Test
    void testEquals() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Toyota", "Corolla");
        Car car3 = new Car("Honda", "Civic");

        // Test equality with same values
        assertThat(car1.equals(car2)).isTrue();
        
        // Test equality with different values
        assertThat(car1.equals(car3)).isFalse();
        
        // Test equality with null
        assertThat(car1.equals(null)).isFalse();
        
        // Test equality with different type
        assertThat(car1.equals("not a car")).isFalse();
    }

    @Test
    void testHashCode() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Toyota", "Corolla");
        Car car3 = new Car("Honda", "Civic");

        // Same cars should have same hashcode
        assertThat(car1).hasSameHashCodeAs(car2);
        
        // Different cars should have different hashcodes
        assertThat(car1.hashCode()).isNotEqualTo(car3.hashCode());
    }

    @Test
    void testToString() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(1L);

        assertThat(car).hasToString("car{carId=1, maker='Toyota', model='Corolla'}");
    }

    @Test
    void testSetCarId() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(1L);
        assertThat(car.getCarId()).isEqualTo(1L);
    }
} 