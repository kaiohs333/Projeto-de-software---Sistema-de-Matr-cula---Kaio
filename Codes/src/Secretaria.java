import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Secretaria {
  private List<Professor> professores;
  private List<Disciplina> disciplinas;
  private Map<Integer, Curso> cursos;
  private Map<Integer, Aluno> alunos;
  private SistemaFinanceiro sistemaFinanceiro;
  private static Secretaria secretaria;

  private Secretaria() {
    this.professores = new ArrayList<Professor>();
    this.disciplinas = new ArrayList<Disciplina>();
    this.cursos = new HashMap<Integer, Curso>();
    this.alunos = new HashMap<Integer, Aluno>();
    this.sistemaFinanceiro = SistemaFinanceiro.getInstance();
  }

  public static Secretaria getInstance() {
    if (secretaria == null) {
      secretaria = new Secretaria();
    }

    return secretaria;
  }

  public static void InicializarDados(List<Object> professores, List<Object> alunos, List<Object> cursos, List<Object> disciplinas) {
    Secretaria secretaria = Secretaria.getInstance();

    if (professores.size() > 0) {
      secretaria.professores = professores.stream().map(p -> (Professor) p).collect(Collectors.toList());
    }

    if (disciplinas.size() > 0) {
      secretaria.disciplinas = disciplinas.stream().map(d -> (Disciplina) d).collect(Collectors.toList());

      Disciplina.AtualizarNextCodigo(secretaria.disciplinas.get(secretaria.disciplinas.size() - 1).getCodigo());
    }
    
    alunos.stream().forEach(a -> {
      secretaria.MatricularAluno((Aluno) a);
    });

    cursos.stream().forEach(c -> {
      secretaria.AdicionarCurso((Curso) c);
    });
  }

  public void GerarCurriculo(Curriculo curriculo, Curso curso) {
    curso.AtualizarCurriculo(curriculo);
  }

  public void SolicitarMatricula(Aluno aluno, Disciplina disciplina) {
    aluno.AddDisciplina(disciplina);
  }

  public void SolicitarCancelamentoMatricula(Aluno aluno, Disciplina disciplina) {
    aluno.RemoverDisciplina(disciplina);
  }

  public void MatricularAluno(Aluno aluno) throws IllegalArgumentException {
    Aluno alunoComMesmaMatricula = this.alunos.get(aluno.getMatricula());

    if (alunoComMesmaMatricula != null) {
      throw new IllegalArgumentException("Já existe um aluno com essa matricula");
    }

    this.alunos.putIfAbsent(aluno.getMatricula(), aluno);
  }

  public void AdicionarProfessor(Professor professor) {
    this.professores.add(professor);
  }

  public void AdicionarDisciplina(Disciplina disciplina) {
    this.disciplinas.add(disciplina);
    this.AtribuirDisciplina(disciplina, disciplina.getProfessor());
  }

  public void AtribuirDisciplina(Disciplina disciplina, Professor professor) {
    Boolean disciplinaEncontrada = professor.ListarDisciplinas().stream()
        .filter(d -> d.getCodigo() == disciplina.getCodigo()).findFirst().isPresent();

    if (!disciplinaEncontrada) {
      if (disciplina.getProfessor() != null) {
        disciplina.getProfessor().RemoverDisciplina(disciplina);
      }

      professor.AdicionarDisciplina(disciplina);
    }
  }

  public void AdicionarCurso(Curso curso) {
    this.cursos.putIfAbsent(curso.getId(), curso);
  }

  public void InscreverParaSemestre(Aluno aluno) {
    this.NotificarFinanceiro(aluno);
  }

  private void NotificarFinanceiro(Aluno aluno) {
    this.sistemaFinanceiro.GerarCobranca(aluno);
  }

  public Curso BuscarCurso(String idCurso) {
    if (this.cursos == null) {
      return null;
    }

    return this.cursos.get(Integer.parseInt(idCurso));
  }

  public List<Professor> ListarProfessores() {
    return this.professores;
  }

  public List<Aluno> ListarAlunos() {
    if (this.alunos == null) {
      return null;
    }

    return List.copyOf(this.alunos.values());
  }

  public List<Curso> ListarCursos() {
    if (this.cursos == null) {
      return null;
    }

    return List.copyOf(this.cursos.values());
  }

  public List<Disciplina> ListarDisciplinas() {
    return this.disciplinas;
  }
}
