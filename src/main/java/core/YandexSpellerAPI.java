package core;

import beans.YandexSpellerAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;

import java.util.*;

import static core.YandexSpellerConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class YandexSpellerAPI {

    private HashMap<String, String> params = new HashMap<>();
    private List<String> texts = new ArrayList<>();

    private YandexSpellerAPI() {
    }

    public static List<List<YandexSpellerAnswer>> getYandexSpellerAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<List<YandexSpellerAnswer>>>() {
        }.getType());
    }

    public static class ApiBuilder {

        YandexSpellerAPI spellerApi;
        private ApiBuilder(YandexSpellerAPI gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder text(String... texts) {
            spellerApi.texts.addAll(Arrays.asList(texts));
            return this;
        }

        ApiBuilder text(List<String> texts) {
            spellerApi.texts.addAll(texts);
            return this;
        }

        public ApiBuilder options(String options) {
            spellerApi.params.put(PARAM_OPTIONS, options);
            return this;
        }

        public ApiBuilder language(Language language) {
            spellerApi.params.put(PARAM_LANG, language.langCode());
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParam(PARAM_TEXT, spellerApi.texts)
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get(YANDEX_SPELLER_API_URI).prettyPeek();
        }

    }

    public static ApiBuilder with() {
        YandexSpellerAPI api = new YandexSpellerAPI();
        return new ApiBuilder(api);
    }

    public static ResponseSpecification successResponse(Matcher<?> matcher) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectBody(matcher)
                .build();
    }

    public static ResponseSpecification responseWithMistakes(int status, String statusLine, String message) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .expectStatusLine(statusLine)
                .expectHeader("Connection", "keep-alive")
                .expectHeader("SpellerService", message)
                .expectResponseTime(lessThan(20000L))
                .build();
    }



}
