package api.tests.users;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import api.requests.UsersPage;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Feature("Users")
public class PutUserTest extends UsersPage {

  @Test
  @DisplayName("Update user")
  @Severity(SeverityLevel.CRITICAL)
  void putUser() {
    assertThat(put("/usuarios/" + getUserId(), randomUser(), HttpStatus.SC_OK))
        .isEqualTo("Registro alterado com sucesso");
  }

  @Test
  @DisplayName("Try to update a user with invalid data")
  @Severity(SeverityLevel.CRITICAL)
  void putInvalidUser() {

    APIResponse response = request.put("/usuarios/" + getUserId(),
        RequestOptions.create().setData(invalidUser()));

    assertThat(response.status()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    assertThat(response.text()).contains(
        "nome não pode ficar em branco",
        "email não pode ficar em branco",
        "password não pode ficar em branco",
        "administrador deve ser 'true' ou 'false");
  }
}
