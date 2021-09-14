package APICalls;

import constant.Endpoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static constant.Endpoints.PAGE;
import static constant.Endpoints.NAME;
import static constant.Endpoints.JOB;


public class RestServices {


    @Before
    public void setup() {
        RestAssured.baseURI = Endpoints.BASE_URL;
    }

    @Test
    public void getUsersList() {
        Response response = RestAssured
                .given()
                .header("Content-Type", ContentType.JSON)
                .request()
                .queryParam(PAGE,2)
                .get(Endpoints.GETUSERLIST)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();

        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusCode());
        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void updateUser() {

        JSONObject requestParams = new JSONObject();
        requestParams.put(NAME, "Ashish Tikarye");
        requestParams.put(JOB, "Automation Engineer");

        Response response = RestAssured
                .given()
                .header("Content-Type", ContentType.JSON)
                .request().body(requestParams.toString())
                .post(Endpoints.UPDATEUSER)
                .then().statusCode(HttpStatus.SC_CREATED)
                .extract().response();
        Assert.assertEquals("Ashish Tikarye",response.jsonPath().get("name"));

    }

    @Test
    public void updateJob() {

        JSONObject requestParams = new JSONObject();
        requestParams.put(NAME, "Ashish Tikarye");
        requestParams.put(JOB, "Automation Tester");
        Response response = RestAssured
                .given()
                .header("Content-Type", ContentType.JSON)
                .request().body(requestParams.toString())
                .put(Endpoints.UPDATEUSER)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();

        Assert.assertEquals("Automation Tester",response.jsonPath().get("job"));
    }

    @Test
    public void deleteUser() {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", ContentType.JSON)
                .request()
                .get(Endpoints.DELETEUSER)
                .then().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().response();

    }
}
