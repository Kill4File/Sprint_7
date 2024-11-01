package actions.order;

import baseUrl.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static parametres.OrderApi.listOrders;

public class TestListOrders extends BaseTest {

    @Before
    public void setUp() {
        baseTestURL();
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void testListOrders() {
        Response response = listOrders();
        response.then().statusCode(SC_OK);
    }
}