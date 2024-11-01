package actions.creation;

import parametres.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class PositiveTest extends baseUrl.BaseTest {

    private String login, password, firstName;

    public PositiveTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"summer1", "1234", "Hans"},
                {"summer2", "1234", "Senya"}
        };
    }

    @Before
    public void setUp() {
        baseTestURL();
    }

    @Step("Регистрация")
    public Response signIn() {
        CourierCreation courier = new CourierCreation(login, password, firstName);
        Response response = CourierApi.CourierCreation(courier);
        return response;
    }

    @Test
    @DisplayName("Создание курьера")
    public void creationPositiveTest() {
        Response response = signIn();
        response
                .then().assertThat().statusCode(SC_CREATED).body("ok", equalTo(true));
    }

    @After
    public void deleteCourier() {
        CourierApi.testCourierDelete(login, password);
    }
}
