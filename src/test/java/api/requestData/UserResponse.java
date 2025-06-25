package api.requestData;

import lombok.Getter;

import java.util.List;

@Getter
public class UserResponse {
  private int quantidade;
  private List<UserData> usuarios;

  public List<UserData> getUsuarios() {
    return usuarios;
  }

  public int getQuantidade() {
    return quantidade;
  }



}