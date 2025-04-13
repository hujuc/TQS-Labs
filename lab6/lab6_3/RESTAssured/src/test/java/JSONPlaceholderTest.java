
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JSONPlaceholderTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void testTodosEndpointIsAvailable() {
        given()
                .when()
                .get(BASE_URL + "/todos")
                .then()
                .statusCode(200);
    }

    @Test
    void testTodoNumberFourHasExpectedTitle() {
        given()
                .when()
                .get(BASE_URL + "/todos/4")
                .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    void testTodosListContainsExpectedIds() {
        given()
                .when()
                .get(BASE_URL + "/todos")
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }

    @Test
    void testTodosListResponseTime() {
        given()
                .when()
                .get(BASE_URL + "/todos")
                .then()
                .statusCode(200)
                .time(lessThan(2000L)); // Resposta em menos de 2 segundos
    }
}