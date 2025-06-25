package api.tests.users;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import api.requests.UsersPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Users")
public class DeleteUserTest extends UsersPage {

  @Test
  @Tag("API_Regression")
  @DisplayName("Delete user")
  @Severity(SeverityLevel.CRITICAL)
  void deleteUserSuccess() {
    assertThat(
        delete("/usuarios/", getUserId(), HttpStatus.SC_OK))
        .isEqualTo("Registro excluído com sucesso");
  }

  @Test
  @DisplayName("Try to delete an invalid user")
  @Severity(SeverityLevel.NORMAL)
  void deleteInvalidUser() {
    assertThat(delete("/usuarios/", "000000", HttpStatus.SC_OK))
        .isEqualTo("Nenhum registro excluído");
  }
}
