package parametres;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderApi {

    private static final String orderEndpoint = "/api/v1/orders";

    public static Response makeOrder(MakeOrder order) {
        return given()
                .body(order)
                .post(orderEndpoint);
    }

    public static Response listOrders() {
        return given()
                .get(orderEndpoint);
    }
}