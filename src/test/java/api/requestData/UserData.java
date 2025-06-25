package api.requestData;

import java.util.List;

import lombok.Getter;

@Getter
public class UserData {

    private String nome;
    private String email;
    private String password;
    private String administrador;
    private String _id;

    private int quantidade;
    private List<UserData> usuarios;

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getAdministrador() {
        return administrador;
    }

    public String get_id() {
        return _id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<UserData> getUsuarios() {
        return usuarios;
    }
}