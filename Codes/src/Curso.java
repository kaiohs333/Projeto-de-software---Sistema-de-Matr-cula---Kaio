import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Curso implements Serializable {
  private static int NEXT_ID = 0;

  private int id;
  private String nome;
  private int creditos;
  private Integer curriculoAtual;
  private Map<Integer, Curriculo> curriculos;
  static final long serialVersionUID = 413L; // Serialização

  public Curso(String nome, int creditos) {
    this.setNome(nome);
    this.setCreditos(creditos);
    this.curriculos = new HashMap<Integer, Curriculo>();
    this.id = ++Curso.NEXT_ID;
  }

  public void AtualizarCurriculo(Curriculo curriculo) {
    this.curriculoAtual = curriculo.getCodigo();
    this.curriculos.putIfAbsent(curriculoAtual, curriculo);
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getCreditos() {
    return creditos;
  }

  public void setCreditos(int creditos) {
    this.creditos = creditos;
  }

  public Curriculo getCurriculoAtual() {
    if (curriculoAtual == null) {
      return null;
    }

    return curriculos.get(curriculoAtual);
  }

  public void setCurriculoAtual(Integer curriculoAtual) {
    this.curriculoAtual = curriculoAtual;
  }

  @Override
  public String toString() {
    return "ID: " + id + " | Nome: " + nome + " | Creditos: " + creditos;
  }
}
