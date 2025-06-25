package api.requests;

import static org.assertj.core.api.Assertions.assertThat;

import commons.BaseTests.BaseAPI;
import api.requestData.ResponseData;
import api.requestData.UserData;
import api.requestData.UserResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import commons.JsonData;
import io.qameta.allure.Step;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class UsersPage extends BaseAPI {

  private static final Gson gson = new Gson();

  public Map<String, String> userBody(String nome, String email, String password,
      String administrador) {
    Map<String, String> data = new HashMap<>();
    data.put("nome", nome);
    data.put("email", email);
    data.put("password", password);
    data.put("administrador", administrador);
    return data;
  }

  @Step("Gerar usuário aleatório")
  public Map<String, String> randomUser() {
    return userBody(generator.fullName(), generator.email(), "12345678", "true");
  }

  @Step("Gerar usuário inválido")
  public Map<String, String> invalidUser() {
    return userBody("", "", "", "");
  }

  @Step("Obter ID do usuário")
  public String getUserId() {
    APIResponse response = request.post("/usuarios", RequestOptions.create().setData(randomUser()));

    if (response.status() == HttpStatus.SC_CREATED) {
      JsonObject jsonObject =
          JsonParser.parseString(response.text()).getAsJsonObject();

      if (jsonObject.has("_id")) {
        return jsonObject.get("_id").getAsString();
      }
    }
    throw new RuntimeException("Erro ao obter o ID do usuário.");
  }

  @Step("Realizar requisição POST na rota {rota}")
  public String post(String rota, Map body, Integer statusCode) {
    APIResponse apiResponse = request.post(rota, RequestOptions.create().setData(body));

    ResponseData responseData =
        gson.fromJson(apiResponse.text(), ResponseData.class);

    assertThat(apiResponse.status()).isEqualTo(statusCode);
    return responseData.getMessage();
  }

  @Step("Realizar requisição PUT na rota {rota}")
  public String put(String rota, Map body, Integer statusCode) {
    APIResponse apiResponse = request.put(rota,
        RequestOptions.create().setData(body)
            .setHeader("accept", "application/json")
            .setIgnoreHTTPSErrors(true));

    ResponseData responseData =
        gson.fromJson(apiResponse.text(), ResponseData.class);

    assertThat(apiResponse.status()).isEqualTo(statusCode);
    return responseData.getMessage();
  }

  @Step("Realizar requisição DELETE na rota {rota}")
  public String delete(String rota, String id, Integer statusCode) {
    APIResponse apiResponse =
        request.delete(rota + id, RequestOptions.create()
            .setIgnoreHTTPSErrors(true)
            .setHeader("accept", "application/json"));

    ResponseData responseData =
        gson.fromJson(apiResponse.text(), ResponseData.class);

    assertThat(apiResponse.status()).isEqualTo(statusCode);
    return responseData.getMessage();
  }

  @Step("Realizar requisição GET na rota {rota}")
  public UserData getUsersByQueryParamRequests(String rota, String key, String value,
      Integer statusCode) {
    APIResponse apiResponse =
        request.get(rota, RequestOptions.create()
            .setQueryParam(key, value)
            .setIgnoreHTTPSErrors(true)
            .setHeader("accept", "application/json"));

    UserResponse userResponse = gson.fromJson(apiResponse.text(), UserResponse.class);

    assertThat(apiResponse.status()).isEqualTo(statusCode);

    if (userResponse.getUsuarios().isEmpty()) {
      return new UserData();
    }
    return userResponse.getUsuarios().get(0);
  }

  public void createDefaultUser() {
    Map<String, String> data = new HashMap<>();
    data.put("nome", JsonData.getData("nome"));
    data.put("email", JsonData.getData("email"));
    data.put("password", JsonData.getData("password"));
    data.put("administrador", JsonData.getData("administrador"));

    try {
      post("/usuarios", data, HttpStatus.SC_CREATED);
    } catch (Exception e) {
      System.out.println("[INFO] Usuário default já cadastrado.");
    }
  }
}