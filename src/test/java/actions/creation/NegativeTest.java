package actions.creation;

import baseUrl.BaseTest;
import parametres.*;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class NegativeTest extends BaseTest {
    private String login;
    private String password;
    private String firstName;
    private int code;
    private String message;

    public NegativeTest(String login, String password, String firstName, int code, String message) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.code = code;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"cinderella", "1234", "Zoya", 409, "Этот логин уже используется. Попробуйте другой."},
                {"cinderella", "", "Zoya", 400, "Недостаточно данных для создания учетной записи"},
                {"", "1234", "Zoya", 400, "Недостаточно данных для создания учетной записи"},
                {"", "", "Zoya", 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Before
    public void setUp() {
        baseTestURL();
        CourierApi.CourierCreation(new CourierCreation("cinderella", "1234", "Zoya"));
    }

    @Step("Регистрация с неполными/повторяющимися данными")
    public Response signIn() {
        CourierCreation courier = new CourierCreation(login, password, firstName);
        Response response = CourierApi.CourierCreation(courier);
        return response;
    }

    @Test
    @DisplayName("Создание курьера - негативный кейс")
    public void createNegativeTest() {
        Response response = signIn();
        response
                .then().assertThat().statusCode(code).body("message", equalTo(message));

    }

    @After
    public void deleteCourier() {

        CourierApi.testCourierDelete("cinderella", "1234");
    }
}
