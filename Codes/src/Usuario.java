import java.io.Serializable;

public class Usuario implements Serializable {
  private String nome;
  private String login;
  private String email;
  private String senha;
  static final long serialVersionUID = 663L; // Serialização

  public Usuario(String nome, String login, String email, String senha) {
    this.nome = nome;
    this.login = login;
    this.email = email;
    this.senha = senha;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Override
  public String toString() {
    return "Nome: " + this.getNome() + " | Email: " + this.getEmail();
  }
}