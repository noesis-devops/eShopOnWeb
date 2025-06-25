package api.tests.users;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import api.requestData.UserData;
import api.requests.UsersPage;
import commons.JsonData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Users")
public class GetUsersTest extends UsersPage {

  @Test
  @DisplayName("Get user by name")
  @Severity(SeverityLevel.CRITICAL)
  @Flaky
  void getUserByName() {
    UserData userData =
        getUsersByQueryParamRequests
            ("/usuarios",
                "nome",
                "Fulano da Silva",
                HttpStatus.SC_OK);

    assertThat(userData.getNome()).isEqualTo("Fulano da Silva");
    assertThat(userData.getEmail()).isEqualTo("fulano@qa.com");
  }

  @Test
  @DisplayName("Get user by id")
  @Severity(SeverityLevel.CRITICAL)
  void getUsersById() {
    UserData userData =
        getUsersByQueryParamRequests
            ("/usuarios",
                "_id",
                JsonData.getData("_id"),
                HttpStatus.SC_OK);

    assertThat(userData.get_id()).isEqualTo("0uxuPY0cbmQhpEz1");
    assertThat(userData.getEmail()).isEqualTo("fulano@qa.com");
  }

  @Test
  @DisplayName("Get user by email")
  @Severity(SeverityLevel.CRITICAL)
  void getUsersByEmail() {
    UserData userData =
        getUsersByQueryParamRequests
            ("/usuarios",
                "email",
                "fulano@qa.com",
                HttpStatus.SC_OK);

    assertThat(userData.getEmail()).isEqualTo("fulano@qa.com");
  }

  @Test
  @Tag("API_Regression")
  @DisplayName("Try get user by invalid id")
  @Severity(SeverityLevel.NORMAL)
  void getUsersByInvalidId() {
    UserData userData =
        getUsersByQueryParamRequests
            ("/usuarios",
                "_id",
                "000000000000000000000000",
                HttpStatus.SC_OK);

    assertThat(userData.getQuantidade()).isEqualTo(0);
    assertThat(userData.getUsuarios()).isEqualTo(null);
  }

  @Test
  @Description("Not implemented test")
  @Disabled("Not implemented")
  void getUsersByInvalidEmail() {
  }

  @Test
  @Disabled("Not implemented")
  void getUsersByInvalidName() {
  }

  @Test
  @Disabled("Not implemented")
  void getUsersByInvalidAdmin() {
  }
}
