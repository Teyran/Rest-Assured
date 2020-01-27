import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {
  @Test()
  public void testGet1() {
    given()
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .log().all()
            .body("page", notNullValue())
            .body("per_page", notNullValue())
            .body("total", notNullValue())
            .body("total_pages", notNullValue())
            .body("data.id", not(hasItem(nullValue())))
            .body("data.email", not(hasItem(nullValue())))
            .body("data.first_name", not(hasItem(nullValue())))
            .body("data.last_name", not(hasItem(nullValue())))
            .body("data.avatar", not(hasItem(nullValue())))
    ;
  }

  @Test()
  public void testPost1() {
    Map<String, String> data = new HashMap<>();
    data.put("name", "Ter");
    data.put("job", "Tester");
    Response response = given()
            .contentType("application/json")
            .body(data)
            .when().post("https://reqres.in/api/users")
            .then()
            .log().all()
            .statusCode(201)
            .extract()
            .response()
            ;
    JsonPath jsonPathEvaluator = response.jsonPath();
    Assert.assertEquals(jsonPathEvaluator.get("name").toString(),data.get("name"),"Name not equals");
    Assert.assertEquals(jsonPathEvaluator.get("job").toString(),data.get("job"),"Job not equals");
  }
}