package com.example.tests;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.entities.Message;
import com.example.entities.WeatherInfo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Tests {
    private long testStartTime;
    private static final String SUT_URL = System.getProperty("sut.url", "http://localhost:8080/sut");
    private static final String QAWS_URL = System.getProperty("qaws.url", "http://localhost:8080/qaws");

    @BeforeMethod
    public void setUp() {
        open(SUT_URL);
        RestAssured.baseURI = QAWS_URL;
        testStartTime = System.currentTimeMillis();
    }

    @Test
    public void testFileCreation() {
        final String fileContent = "Some great file content!";
        $("#text-to-save").setValue(fileContent);
        $("#btn-save-file").click();
        $("#save-confirm").waitUntil(value("Saved!"), 5000);
        given().contentType(ContentType.JSON).body("{\"path\": \"/../sut/sut-file.txt\"}").when().post("/file/read")
                .then().body(is("Text: " + fileContent));
    }

    @Test
    public void testWeather() {
        String city = "Kyiv";
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemp(293.0);
        weatherInfo.setDescription("the weather is fine!");
        given().contentType(ContentType.JSON).body(weatherInfo).when().post("/weather/put?city={city}", city).then()
                .statusCode(200);
        $("#weather-city").setValue(city);
        $("#btn-get-weather").click();
        $("#weather-result-temp").waitUntil(not(empty), 5000);
        $("#weather-result-descr").shouldHave(value("the weather is fine!"));
    }

    @Test
    public void testMessageSending() {
        Message message = new Message();
        message.setSender("Dart");
        message.setText("Some textqweqweqweqwe");
        $("#msg-sender").setValue(message.getSender());
        $("#msg-text").setValue(message.getText());
        $("#btn-msg-send").click();
        $("#msg-result").waitUntil(value("OK"), 5000);
        Response response = RestAssured.get("/messages/read/{fromTime}", testStartTime);
        List<Message> messages = Arrays.asList(response.getBody().as(Message[].class));
        Assertions.assertThat(messages).usingFieldByFieldElementComparator().contains(message);
    }

    @AfterMethod
    public void tearDown() {

    }
}
