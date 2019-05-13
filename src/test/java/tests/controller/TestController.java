package tests.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.http.HttpStatus;
import tests.model.PhotoListResponse;

import static io.restassured.RestAssured.given;

public class TestController
{
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    protected static final String API_KEY = resourceBundle.getString("api.key");

    protected static final String API_BASE = "/mars-photos/api/v1";

    protected static final String BASE = String.format("%s%s", resourceBundle.getString("nasa.url"), API_BASE);

    protected static final String CURIOSITY_PHOTOS = "/rovers/curiosity/photos";


    protected RequestSpecification getRequestSpecification()
    {
        return new RequestSpecBuilder().
                setBaseUri(BASE).
                addHeader("Content-Type", "application/json").
                build();
    }


    public PhotoListResponse getMarsPhotosFromCuriosityRequest(Map<String, String> params)
    {
        return given(getRequestSpecification()).
                params(params).
                param("api_key", API_KEY).
                get(TestController.CURIOSITY_PHOTOS).
                then().
                statusCode(HttpStatus.SC_OK).extract().as(PhotoListResponse.class);
    }


}
