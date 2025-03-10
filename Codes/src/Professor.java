import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario implements Serializable {
  private ArrayList<Disciplina> disciplinas;
  static final long serialVersionUID = 123L; // Serialização

  public Professor(String nome, String login, String email, String senha) {
    super(nome, login, email, senha);
    this.disciplinas = new ArrayList<Disciplina>();
  }

  public void AdicionarDisciplina(Disciplina disciplina) {
    this.disciplinas.add(disciplina);
  }

  public void RemoverDisciplina(Disciplina disciplina) {
    this.disciplinas.remove(disciplina);
  }

  public ArrayList<Disciplina> ListarDisciplinas() {
    return this.disciplinas;
  }

  public void ListarAlunos() {
    if (this.disciplinas == null || this.disciplinas.size() == 0) {
      System.out.println("Este professor não possui disciplinas!");
    } else {
      this.disciplinas.stream().forEach(disciplina -> {
        this.ListarAlunos(disciplina);
      }); // Ou filtrar pelas disciplinas que tem esse usuario como professor
    }
  }

  public void ListarAlunos(Disciplina disciplina) {
    List<Aluno> alunos = disciplina.ListarAlunos();

    System.out.println("=======================================================");
    System.out.println("Disciplina: " + disciplina.getNome());
    System.out.println("=======================================================");
    alunos.stream().forEach(System.out::println);

    System.out.println("=======================================================");
  }
}
