import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AlunoFactory {
  private static void PrintMenuMatricular() {
    System.out.println("=================================================");
    System.out.println("== 1 - Listar disciplinas                      ==");
    System.out.println("== 2 - Escolher disciplina                     ==");
    System.out.println("== 3 - Matricular na disciplina escolhida      ==");
    System.out.println("== 0 - Voltar                                  ==");
    System.out.println("=================================================");
  }

  public static void Matricular(Scanner scanner, Usuario usuario) {
    Secretaria secretaria = Secretaria.getInstance();

    Aluno aluno = (Aluno) usuario;
    Disciplina disciplinaEscolhida = null;
    
    String instrucao = "";

    do {
      ScannerUtils.LimparTela();
      AlunoFactory.PrintMenuMatricular();
      instrucao = ScannerUtils.lerInstrucao(scanner);

      switch (instrucao) {
        case "1":
          secretaria.ListarDisciplinas().stream().filter(disciplina -> disciplina.getCurso().getId() == aluno.getCurso().getId())
              .forEach(System.out::println);
          ScannerUtils.Pausa(scanner);
          break;
        case "2": {
          do {
            String codigo = ScannerUtils.lerInstrucao("Digite o código da disciplina escolhida: ", scanner);

            Optional<Disciplina> disciplinaEncontrada = secretaria.ListarDisciplinas().stream()
                .filter(disciplina -> Integer.toString(disciplina.getCodigo()).equals(codigo)).findFirst();

            if (!disciplinaEncontrada.isEmpty() && disciplinaEncontrada.get().getCurso().getId() == aluno.getCurso().getId()) {
              disciplinaEscolhida = disciplinaEncontrada.get();
            } else {
              System.out.println(disciplinaEncontrada == null ? "Não existe uma disciplina com o código informado"
                  : "A disciplina precisa ser do curso do aluno.");
            }
          } while (disciplinaEscolhida == null);

          break;
        }
        case "3": {
          if (disciplinaEscolhida == null) {
            System.out.println("Você precisa escolher uma disciplina antes de poder se matricular");
            break;
          }

          secretaria.SolicitarMatricula(aluno, disciplinaEscolhida);
          ScannerUtils.Pausa(scanner);
          instrucao = "0";
          break;
        }
        default:
          break;
      }
    } while (!instrucao.equals("0"));
  }

  private static void PrintMenuCancelarMatricula() {
    System.out.println("====================================================");
    System.out.println("== 1 - Listar disciplinas                         ==");
    System.out.println("== 2 - Escolher disciplina                        ==");
    System.out.println("== 3 - Cancelar matricula na disciplina escolhida ==");
    System.out.println("== 0 - Voltar                                     ==");
    System.out.println("====================================================");
  }

  public static void CancelarMatricula(Scanner scanner, Usuario usuario) {
    Aluno aluno = (Aluno) usuario;
    Secretaria secretaria = Secretaria.getInstance();

    Disciplina disciplinaEscolhida = null;
    
    String instrucao = "";
    
    do {
      ScannerUtils.LimparTela();
      AlunoFactory.PrintMenuCancelarMatricula();
      instrucao = ScannerUtils.lerInstrucao(scanner);

      switch (instrucao) {
        case "1":
          aluno.ListarDisciplinasMatriculadas().stream().forEach(System.out::println);
          ScannerUtils.Pausa(scanner);
          break;
        case "2": {
          do {
            String codigo = ScannerUtils.lerInstrucao("Digite o código da disciplina escolhida: ", scanner);

            Optional<Disciplina> disciplinaEncontrada = aluno.ListarDisciplinasMatriculadas().stream()
                .filter(disciplina -> Integer.toString(disciplina.getCodigo()).equals(codigo)).findFirst();

            if (!disciplinaEncontrada.isEmpty()) {
              disciplinaEscolhida = disciplinaEncontrada.get();
            } else {
              System.out.println("Você não está matriculado na disciplina com o código informado");
            }
          } while (disciplinaEscolhida == null);

          break;
        }
        case "3": {
          if (disciplinaEscolhida == null) {
            System.out.println("Você precisa escolher uma disciplina");
            break;
          }

          secretaria.SolicitarCancelamentoMatricula(aluno, disciplinaEscolhida);
          instrucao = "0";
          break;
        }
        default:
          break;
      }
    } while (!instrucao.equals("0"));
  }

  public static void ConfirmarMatricula(Scanner scanner, Usuario usuario) {
    Aluno aluno = (Aluno) usuario;
    List<Disciplina> disciplinasMatriculadas = aluno.ListarDisciplinasMatriculadas();

    if (disciplinasMatriculadas == null) {
      System.out.println("Você precisa estar matriculado em pelo menos uma disciplina para confirmar a matricula");
    }

    String instrucao = ScannerUtils.lerValor("Você deseja confirmar sua matricula? (s/n)", scanner, List.of("s", "n"));

    if (instrucao.equals("s")) {
      Secretaria.getInstance().InscreverParaSemestre(aluno);
    }
  }

  public static void PagarCobranca(Scanner scanner, Usuario usuario) {
    Aluno aluno = (Aluno) usuario;
    List<Cobranca> cobrancas = aluno.ListarCobrancas();

    if (cobrancas != null) {
      System.out.println("===========================================");
      cobrancas.stream().forEach(c -> System.out.println("N: " + (cobrancas.indexOf(c) + 1) + " - " + c.toString()));
      System.out.println("===========================================");

      int cobrancaASerPaga = -1;

      do {
        try {
          String cobrancaASerPagaStr = ScannerUtils.lerValor("Digite o número da cobrança a ser paga: ", scanner);

          cobrancaASerPaga = Integer.parseInt(cobrancaASerPagaStr);
        } catch (Exception e) {
          System.out.println("O valor precisa ser um número válido na lista de cobranças pendentes");
        }
      } while (cobrancaASerPaga > (cobrancas.size() + 1) || cobrancaASerPaga <= 0);

      SistemaFinanceiro.getInstance().PagarCobranca(cobrancas.get(cobrancaASerPaga - 1));
    }
  }
}
