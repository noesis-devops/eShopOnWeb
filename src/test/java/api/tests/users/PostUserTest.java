package api.tests.users;

import static org.assertj.core.api.Assertions.assertThat;

import api.requests.UsersPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Users")
public class PostUserTest extends UsersPage {

  @Test
  @DisplayName("Create a new user")
  @Severity(SeverityLevel.CRITICAL)
  void postUser() {
    assertThat(
        post("/usuarios", randomUser(), HttpStatus.SC_CREATED))
        .isEqualTo("Cadastro realizado com sucesso");
  }

  @Test
  @DisplayName("Try to create a user with a duplicated email")
  @Severity(SeverityLevel.NORMAL)
  void postDuplicatedEmail() {
    Map body = randomUser();
    post("/usuarios", body, HttpStatus.SC_CREATED);

    assertThat(
        post("/usuarios", body, HttpStatus.SC_BAD_REQUEST))
        .isEqualTo("Este email já está sendo usado");
  }
}
