import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario implements Serializable {
  private static final int MAX_DISCIPLINAS_OBG = 4;
  private static final int MAX_DISCIPLINAS_OPT = 2;
  private static int COUNT = 0;

  private List<Cobranca> cobrancas;
  private Integer matricula;
  private Curso curso;
  private int disciplinasObg = 0;
  private int disciplinasOpt = 0;
  private List<Disciplina> disciplinasMatriculadas;
  static final long serialVersionUID = 623L; // Serialização

  public Aluno(String nome, String login, String email, String senha, Curso curso) {
    super(nome, login, email, senha);
    this.matricula = ++Aluno.COUNT;
    this.setCurso(curso);
    this.disciplinasMatriculadas = new ArrayList<Disciplina>();
    this.cobrancas = new ArrayList<Cobranca>();
  }

  public List<Cobranca> ListarCobrancas() {
    return this.cobrancas.stream().filter(c -> c.getAtiva()).toList();
  }

  public void AdicionarCobranca(Cobranca cobranca) {
    this.cobrancas.add(cobranca);
  }

  public List<Disciplina> ListarDisciplinasMatriculadas() {
    return this.disciplinasMatriculadas;
  }

  public void AddDisciplina(Disciplina disciplina) {
    try {
      Boolean isDisciplinaObrigatoria = disciplina.getTipo() == TipoDisciplina.OBRIGATORIA;
      Boolean disciplinaMatriculada = this.disciplinasMatriculadas.contains(disciplina);

      if (disciplinaMatriculada) {
        throw new IllegalArgumentException("Disciplina já matriculada");
      }

      if (isDisciplinaObrigatoria) {
        this.AddDisciplina(disciplina, Aluno.MAX_DISCIPLINAS_OBG, this.disciplinasObg, "obrigatórias");
        this.disciplinasObg++;
      } else {
        this.AddDisciplina(disciplina, Aluno.MAX_DISCIPLINAS_OPT, this.disciplinasOpt, "optativas");
        this.disciplinasOpt++;
      }
    } catch (Exception e) {
      System.out.println(e + "\n");
    }
  }

  private void AddDisciplina(Disciplina disciplina, int maxPossivel, int curDisciplinasMatriculadas, String tipoDisciplina) {
    if (curDisciplinasMatriculadas < maxPossivel) {
      disciplina.Matricular(this);
      this.disciplinasMatriculadas.add(disciplina);
    } else {
      throw new IllegalStateException("Só é possivel se matricular em no máximo " + maxPossivel + " disciplinas " + tipoDisciplina);
    }
  }

  public void RemoverDisciplina(Disciplina disciplina) {
    try {
      Boolean isDisciplinaObrigatoria = disciplina.getTipo() == TipoDisciplina.OBRIGATORIA;
      Boolean disciplinaMatriculada = this.disciplinasMatriculadas.contains(disciplina);

      if (!disciplinaMatriculada) {
        throw new IllegalArgumentException("Disciplina não matriculada");
      }

      disciplina.CancelarMatricula(this);
      this.disciplinasMatriculadas.remove(disciplina);

      if (isDisciplinaObrigatoria) {
        this.disciplinasObg--;
      } else {
        this.disciplinasOpt--;
      }
    } catch (Exception e) {
      System.out.println(e + "\n");
    }
  }

  public Integer getMatricula() {
    return matricula;
  }

  public void setMatricula(Integer matricula) {
    this.matricula = matricula;
  }

  public Curso getCurso() {
    return curso;
  }

  public void setCurso(Curso curso) {
    this.curso = curso;
  }
}
