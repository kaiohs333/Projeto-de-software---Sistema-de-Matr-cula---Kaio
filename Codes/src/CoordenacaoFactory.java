import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CoordenacaoFactory {
  private static void PrintMenuAluno() {
    System.out.println("======================================================");
    System.out.println("== 1 - Cadastrar dados do aluno                     ==");
    System.out.println("== 2 - Escolher curso do aluno                      ==");
    System.out.println("== 3 - Finalizar cadastro                           ==");
    System.out.println("== 0 - Voltar                                       ==");
    System.out.println("======================================================");
  }

  public static void CadastrarAluno(Scanner scanner) {
    Secretaria secretaria = Secretaria.getInstance();

    String nome = "";
    String login = "";
    String email = "";
    String senha = "";
    Curso curso = null;
    String instrucao;

    do {
      CoordenacaoFactory.PrintMenuAluno();
      instrucao = ScannerUtils.lerInstrucao(scanner);

      switch (instrucao) {
        case "1":
          nome = ScannerUtils.lerValor("Nome do aluno: ", scanner);
          login = ScannerUtils.lerValor("Login do aluno: ", scanner);
          email = ScannerUtils.lerValor("Email do aluno: ", scanner);
          senha = ScannerUtils.lerValor("Senha do aluno: ", scanner);
          break;
        case "2": {
          List<Curso> cursos = secretaria.ListarCursos();

          System.out.println("======================================================");
          cursos.stream().forEach(System.out::println);
          System.out.println("======================================================");

          String idCurso = ScannerUtils.lerValor("Digite o id do curso desejado: ", scanner,
              cursos.stream().map(c -> Integer.toString(c.getId())).toList());

          curso = secretaria.BuscarCurso(idCurso);
          break;
        }
        case "3": {
          Aluno aluno = new Aluno(nome, login, email, senha, curso);

          try {
            secretaria.MatricularAluno(aluno);
            instrucao = "0";
          } catch (Exception e) {
            System.out.println(e);
          }
        }
        default:
          break;
      }
    } while (!instrucao.equals("0"));
  }

  public static void CadastrarProfessor(Scanner scanner) {
    Secretaria secretaria = Secretaria.getInstance();

    String nome = ScannerUtils.lerValor("Nome do professor: ", scanner);
    String login = ScannerUtils.lerValor("Login do professor: ", scanner);
    String email = ScannerUtils.lerValor("Email do professor: ", scanner);
    String senha = ScannerUtils.lerValor("Senha do professor: ", scanner);

    Professor professor = new Professor(nome, login, email, senha);

    secretaria.AdicionarProfessor(professor);
  }

  public static void CadastrarCurso(Scanner scanner) {
    Secretaria secretaria = Secretaria.getInstance();

    String nome = ScannerUtils.lerValor("Nome do curso: ", scanner);

    int qtdeCreditos = -1;

    do {
      try {
        String qtdeCreditosStr = ScannerUtils.lerValor("Quantidade de créditos do curso: ", scanner);

        qtdeCreditos = Integer.parseInt(qtdeCreditosStr);
      } catch (Exception e) {
        System.out.println("O valor informado precisa ser um número");
      }
    } while (qtdeCreditos == -1);

    Curso curso = new Curso(nome, qtdeCreditos);

    secretaria.AdicionarCurso(curso);
  }

  private static void PrintMenuCadastrarDisciplina() {
    System.out.println("======================================================");
    System.out.println("== 1 - Definir curso                                ==");
    System.out.println("== 2 - Definir professor                            ==");
    System.out.println("== 3 - Definir dados da disciplina                  ==");
    System.out.println("== 4 - Cadastrar disciplina                         ==");
    System.out.println("== 0 - Voltar                                       ==");
    System.out.println("======================================================");
  }

  public static void CadastrarDisciplina(Scanner scanner) {
    Secretaria secretaria = Secretaria.getInstance();
    
    String nome = "";
    Curso curso = null;
    Professor professor = null;
    Double valor = -1D;
    TipoDisciplina tipo = null;

    String instrucao;

    do {
      CoordenacaoFactory.PrintMenuCadastrarDisciplina();
      instrucao = ScannerUtils.lerInstrucao(scanner);

      switch (instrucao) {
        case "1":
          List<Curso> cursos = secretaria.ListarCursos();

          System.out.println("======================================================");
          cursos.stream().forEach(System.out::println);
          System.out.println("======================================================");

          String idCurso = ScannerUtils.lerValor("Digite o id do curso desejado: ", scanner,
              cursos.stream().map(c -> Integer.toString(c.getId())).toList());

          curso = secretaria.BuscarCurso(idCurso);
          break;
        case "2": {
          List<Professor> professores = secretaria.ListarProfessores();

          System.out.println("======================================================");
          professores.stream().forEach(p -> System.out.println("N: " + (professores.indexOf(p) + 1) + " | " + p));
          System.out.println("======================================================");

          int idProfessor = -1;

          do {
            try {
              String idProfessorStr = ScannerUtils.lerValor("Digite o numero do professor desejado: ", scanner);

              int idEscolhido = Integer.parseInt(idProfessorStr);

              if (idEscolhido > 0 && idEscolhido < (professores.size() + 1)) {
                idProfessor = idEscolhido - 1;
              }
            } catch (Exception e) {
              System.out.println("Você precisa inserir um numero valido");
            }
          } while (idProfessor == -1);

          professor = professores.get(idProfessor);
          break;
        }    
        case "3": {
          nome = ScannerUtils.lerValor("Digite o nome da disciplina: ", scanner);

          String disciplinaObrigatoria = ScannerUtils.lerValor("A disciplina é obrigatória? (s/n)", scanner, List.of("s", "n"));
          tipo = disciplinaObrigatoria.equals("s") ? TipoDisciplina.OBRIGATORIA : TipoDisciplina.OPTATIVA;

          do {
            try {
              String valorStr = ScannerUtils.lerValor("Digite o valor da disciplina: ", scanner);

              Double valorEscolhido = Double.parseDouble(valorStr);

              if (valorEscolhido >= 0D) {
                valor = valorEscolhido;
              }
            } catch (Exception e) {
              System.out.println("O valor precisa ser um número válido");
            }
          } while (valor == -1D);

          break;
        }
        case "4": {
          if (nome.equals("")) {
            System.out.println("Você precisa definir um nome para a disciplina");
            break;
          }

          if (tipo == null) {
            System.out.println("Você precisa definir um tipo para a disciplina");
            break;
          }

          if (curso == null) {
            System.out.println("Você precisa definir um curso para a disciplina");
            break;
          }

          if (professor == null) {
            System.out.println("Você precisa definir um professor para a disciplina");
            break;
          }

          if (valor < 0D) {
            System.out.println("Você precisa definir um valor para a disciplina");
            break;
          }

          Disciplina disciplina = new Disciplina(nome, professor, tipo, curso, valor);

          secretaria.AdicionarDisciplina(disciplina);
          instrucao = "0";

          break;
        }
        default:
          break;
      }
    } while (!instrucao.equals("0"));
  }

  private static void PrintMenuGerarCurriculo() {
    System.out.println("======================================================");
    System.out.println("== 1 - Definir curso                                ==");
    System.out.println("== 2 - Definir disciplinas                          ==");
    System.out.println("== 3 - Salvar cadastro                              ==");
    System.out.println("== 0 - Voltar                                       ==");
    System.out.println("======================================================");
  }

  private static void PrintMenuDisciplinas() {
    System.out.println("======================================================");
    System.out.println("== 1 - Listar disciplinas                           ==");
    System.out.println("== 2 - Adicionar disciplina                         ==");
    System.out.println("== 3 - Remover disciplina                           ==");
    System.out.println("== 0 - Voltar                                       ==");
    System.out.println("======================================================");
  }

  public static void GerarCurriculo(Scanner scanner) {
    Secretaria secretaria = Secretaria.getInstance();

    Curso curso = null;
    Map<String, Disciplina> disciplinasAdicionadas = new HashMap<String, Disciplina>();
    String instrucao = "";

    do {
      ScannerUtils.LimparTela();
      CoordenacaoFactory.PrintMenuGerarCurriculo();
      instrucao = ScannerUtils.lerInstrucao(scanner);

      switch (instrucao) {
        case "1": {
          List<Curso> cursos = secretaria.ListarCursos();

          System.out.println("======================================================");
          cursos.stream().forEach(System.out::println);
          System.out.println("======================================================");

          String idCurso = ScannerUtils.lerValor("Digite o id do curso desejado: ", scanner,
              cursos.stream().map(c -> Integer.toString(c.getId())).toList());

          curso = secretaria.BuscarCurso(idCurso);
          break;
        }
        case "2": {
          String instrucaoDisciplinas;
          List<Disciplina> disciplinas = secretaria.ListarDisciplinas();

          do {
            ScannerUtils.LimparTela();
            CoordenacaoFactory.PrintMenuDisciplinas();
            instrucaoDisciplinas = ScannerUtils.lerInstrucao(scanner);

            switch (instrucaoDisciplinas) {
              case "1": {
                System.out.println("======================================================");
                disciplinas.stream().forEach(System.out::println);
                System.out.println("======================================================");
                ScannerUtils.Pausa(scanner);
                break;
              }
              case "2": {
                String disciplinaAdicionada = ScannerUtils.lerValor("Digite o código da disciplina a ser adicionada: ",
                    scanner, disciplinas.stream().map(di -> Integer.toString(di.getCodigo())).toList());
                Disciplina disciplinaInstance = disciplinas.stream()
                    .filter(d -> Integer.toString(d.getCodigo()).equals(disciplinaAdicionada)).findFirst().get();

                disciplinasAdicionadas.putIfAbsent(disciplinaAdicionada, disciplinaInstance);
                break;
              }
              case "3": {
                String disciplinaRemovida = ScannerUtils.lerValor("Digite o código da disciplina a ser removida: ",
                    scanner, disciplinas.stream().map(di -> Integer.toString(di.getCodigo())).toList());
                Disciplina disciplinaInstance = disciplinasAdicionadas.get(disciplinaRemovida);

                disciplinasAdicionadas.remove(disciplinaRemovida, disciplinaInstance);
                break;
              }
              default:
                break;
            }

          } while (!instrucaoDisciplinas.equals("0"));

          break;
        }
        case "3": {
          if (curso == null) {
            System.out.println("Você precisa definir o curso");
            break;
          }

          if (disciplinasAdicionadas.size() == 0) {
            System.out.println("Você precisa definir as disciplinas do curriculo");
            break;
          }

          Curriculo curriculo = new Curriculo(curso, List.copyOf(disciplinasAdicionadas.values()));

          Secretaria.getInstance().GerarCurriculo(curriculo, curso);
          instrucao = "0";

          break;
        }
        default:
          break;
      }
    } while (!instrucao.equals("0"));

  }
}
