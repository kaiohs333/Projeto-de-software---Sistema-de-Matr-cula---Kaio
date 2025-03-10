import java.io.Serializable;
import java.util.List;

public class Curriculo implements Serializable {
  private static int NEXT_CODIGO = 0;

  private int codigo;
  private Curso curso;
  private List<Disciplina> disciplinas;
  static final long serialVersionUID = 1043L;

  public Curriculo(Curso curso, List<Disciplina> disciplinas) {
    this.curso = curso;
    this.disciplinas = disciplinas;
    this.codigo = ++Curriculo.NEXT_CODIGO;
  }

  public int getCodigo() {
    return codigo;
  }

  public Curso getCurso() {
    return curso;
  }

  public List<Disciplina> getDisciplinas() {
    return disciplinas;
  }

  public void setDisciplinas(List<Disciplina> disciplinas) {
    this.disciplinas = disciplinas;
  }

  @Override
  public String toString() {
    return "Curriculo > Codigo: " + this.getCodigo() + " | Curso > " + this.getCurso().toString();
  }
}
