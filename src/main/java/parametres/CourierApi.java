package parametres;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierApi {
    
    private static final String courierEndpoint = "/api/v1/courier";
    private static final String loginEndpoint = "/api/v1/courier/login";

    public static Response CourierCreation(CourierCreation courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(courierEndpoint);
    }

    public static Response loginCourier(LoginCourier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(loginEndpoint);
    }

    public static void testCourierCreation (String login, String password, String firstName) {
        CourierCreation courier = new CourierCreation (login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post("/api/v1/courier");
    }

    public static void testCourierDelete(String login, String password) {
        LoginCourier loginCourier = new LoginCourier(login, password);
        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .body()
                .path("id");
        given()
                .delete("/api/v1/courier/{id}", id)
                .then().assertThat().statusCode(SC_OK);
    }
}