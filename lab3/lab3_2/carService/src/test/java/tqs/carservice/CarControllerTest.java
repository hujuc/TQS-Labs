package tqs.carservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tqs.carservice.controllers.CarController;
import tqs.carservice.model.Car;
import tqs.carservice.services.CarManagerService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.Optional;

import tqs.carservice.JsonUtils;

class CarControllerTest {

    private MockMvc mvc;

    @Mock
    private CarManagerService carManagerService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void whenGetCars_thenReturnCars() throws Exception {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Peugeot", "206");
        Car car3 = new Car("Citroen", "C3");

        when(carManagerService.getAllCars()).thenReturn(Arrays.asList(car1, car2, car3));

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is("Toyota")))
                .andExpect(jsonPath("$[1].maker", is("Peugeot")))
                .andExpect(jsonPath("$[2].maker", is("Citroen")));
    }

    @Test
    void whenGetCar_thenReturnCar() throws Exception {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")));
    }

    @Test
    void whenCreateCar_thenReturnCar() throws Exception {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.save(any(Car.class))).thenReturn(car);

        mvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(car)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")));

        verify(carManagerService, times(1)).save(any(Car.class));
    }
}
