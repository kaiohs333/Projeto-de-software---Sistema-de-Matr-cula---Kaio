import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    // Nomes dos arquivos binários
    static String arqProfessores = "professores.bin";
    static String arqAlunos = "alunos.bin";
    static String arqCursos = "cursos.bin";
    static String arqDisciplinas = "disciplinas.bin";

    // #region utilidades

    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Menu
     * 
     * @param teclado Scanner de leitura
     * @return Opção do usuário (int)
     */

    private static int menu(Scanner teclado) {
        // limparTela();
        System.out.println("\033[1;32mSistema de Matrículas");
        System.out.println("=================================================");
        System.out.println("1 - Realizar Login");
        System.out.println("0 - Sair");
        int opcao = 0;
        try {
            opcao = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException ex) {
            teclado.nextLine();
            System.out.println("\033[1;31mSomente opções numéricas!");
            opcao = -1;
        }
        return opcao;
    }

    private static void menuAluno(Scanner teclado, Usuario usuario) {
        int opcao = 0;

        do {
            limparTela();
            System.out.println("=========================================");
            System.out.println("== 1 - Listar disciplinas matriculadas ==");
            System.out.println("== 2 - Fazer matricula em matéria      ==");
            System.out.println("== 3 - Cancelar matricula em matéria   ==");
            System.out.println("== 4 - Confirmar matricula             ==");
            System.out.println("== 5 - Listar cobrancas                ==");
            System.out.println("== 6 - Pagar cobranca                  ==");
            System.out.println("== 0 - Sair do sistema                 ==");
            System.out.println("=========================================");
    
            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case 1:
                        ((Aluno) usuario).ListarDisciplinasMatriculadas().stream().forEach(System.out::println);
                        ScannerUtils.Pausa(teclado);
                        break;
                    case 2:
                        AlunoFactory.Matricular(teclado, usuario);
                        break;
                    case 3:
                        AlunoFactory.CancelarMatricula(teclado, usuario);
                        break;
                    case 4:
                        AlunoFactory.ConfirmarMatricula(teclado, usuario);
                        break;
                    case 5:
                        ((Aluno) usuario).ListarCobrancas().stream().forEach(System.out::println);
                        ScannerUtils.Pausa(teclado);
                        break;
                    case 6:
                        AlunoFactory.PagarCobranca(teclado, usuario);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\033[1;31mOpção inválida!");
                        break;
                }
            } catch (InputMismatchException ex) {
                teclado.nextLine();
                System.out.println("\033[1;31mSomente opções numéricas.");
            }
        } while (opcao != 0);
    }

    private static void menuProfessor(Scanner teclado, Usuario usuario) {
        int opcao = 0;

        do {
            limparTela();
            System.out.println("========================================");
            System.out.println("== 1 - Listar alunos                  ==");
            System.out.println("== 0 - Sair do sistema                ==");
            System.out.println("========================================");
    
            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case 1:
                        ProfessorFactory.ListarAlunos(teclado, usuario);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\033[1;31mOpção inválida!");
                        break;
                }
            } catch (InputMismatchException ex) {
                teclado.nextLine();
                System.out.println("\033[1;31mSomente opções numéricas.");
            }            
        } while (opcao != 0);

    }

    private static void menuSecretaria(Scanner teclado, Coordenacao secretaria) {
        int opcao = 0;

        do {
            limparTela();
            System.out.println("========================================");
            System.out.println("== 1 - Cadastrar aluno                ==");
            System.out.println("== 2 - Cadastrar professor            ==");
            System.out.println("== 3 - Cadastrar curso                ==");
            System.out.println("== 4 - Cadastrar disciplina           ==");
            System.out.println("== 5 - Gerar curriculo                ==");
            System.out.println("== 0 - Sair do sistema                ==");
            System.out.println("========================================");

            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case 1:
                        CoordenacaoFactory.CadastrarAluno(teclado);
                        break;
                    case 2:
                        CoordenacaoFactory.CadastrarProfessor(teclado);
                        break;
                    case 3:
                        CoordenacaoFactory.CadastrarCurso(teclado);
                        break;
                    case 4:
                        CoordenacaoFactory.CadastrarDisciplina(teclado);
                        break;
                    case 5:
                        CoordenacaoFactory.GerarCurriculo(teclado);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\033[1;31mOpção inválida!");
                        break;
                }
            } catch (InputMismatchException ex) {
                teclado.nextLine();
                System.out.println("\033[1;31mSomente opções numéricas.");
            }
        } while (opcao != 0);
    }

    /**
     * Autenticação e direcionamento do usuário
     * 
     * @param teclado
     * @param usuario
     */
    private static void realizarLogin(int opcao, Scanner teclado, Coordenacao coordenacao) {
        Usuario usuarioEncontrado = AuthFactory.Autenticar(teclado, coordenacao);

        if (usuarioEncontrado != null) {
            String tipoUsuario = usuarioEncontrado.getClass().getName();
            System.out.println(tipoUsuario);

            switch (tipoUsuario) {
                case "Aluno":
                    App.menuAluno(teclado, usuarioEncontrado);
                    break;
                case "Professor":
                    App.menuProfessor(teclado, usuarioEncontrado);
                    break;
                case "Coordenacao":
                    App.menuSecretaria(teclado, coordenacao);
                    break;
                default:
                    break;
            }
        }
    }

    // #endregion

    /**
     * Salva os objetos em formato serializado (Object)
     * 
     * @param lista Lista com os objetos
     * @param arq   Nome do arquivo a ser gerado
     */
    public static void salvarBinario(List<Object> lista, String arq) {
        ObjectOutputStream saida = null;
        try {
            saida = new ObjectOutputStream(new FileOutputStream(arq));
            saida.writeObject(lista);
            saida.close();
        } catch (FileNotFoundException fe) {
            System.out.println(
                    "\033[1;31mArquivo não encontrado, ou permissão negada. Tente novamente com outro arquivo");
        } catch (IOException ex) {
            System.out.println("\033[1;31mProblemas na operação de E/S. Contacte o suporte");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Carrega os objetos de um arquivo serializado (Object)
     * 
     * @param arq Nome do arquivo de dados
     * @return Uma lista genérica com a classe do arquivo passado como parâmetro.
     *         A lista pode estar vazia ou nula em caso de exceções.
     */
    public static List<Object> carregarBinario(String arq) {
        List<Object> lista = null;

        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arq));
            lista = (List<Object>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException fe) {
            lista = new ArrayList<Object>();
        } catch (ClassNotFoundException ce) {
            System.out.println("\033[1;31mProblema na conversão dos dados: classe inválida. Contacte o suporte.");
        } catch (IOException ie) {
            System.out.println("\033[1;31mProblemas na operação de E/S. Contacte o suporte");
            System.out.println(ie.getMessage());
        }

        return lista;
    }

    public static void main(String[] args) throws Exception {
        /**
         * Criação da classe Secretaria e alimentação das classes/carregamento dos
         * arquivos binários caso existam.
         */
        Coordenacao coordenacao = new Coordenacao("secretaria", "secretaria", "secretaria@gmail.com", "123");

        Secretaria.InicializarDados(App.carregarBinario(arqProfessores), App.carregarBinario(arqAlunos), App.carregarBinario(arqCursos), App.carregarBinario(arqDisciplinas));

        /**
         * Menu
         */
        Scanner teclado = new Scanner(System.in);
        int opcao;

        while (true) {
            opcao = menu(teclado);
            if (opcao == 0) {
                limparTela();
                break;
            }

            switch (opcao) {
                case 1:
                    realizarLogin(opcao, teclado, coordenacao);
                    break;
                default:
                    break;
            }

            ScannerUtils.Pausa(teclado);
            limparTela();
        }

        /**
         * Após o fim da aplicação salva os dados de usuários e disciplinas
         *
         */
        Secretaria secretaria = Secretaria.getInstance();

        App.salvarBinario(Arrays.asList(secretaria.ListarProfessores().toArray()), arqProfessores);
        App.salvarBinario(Arrays.asList(secretaria.ListarAlunos().toArray()), arqAlunos);
        App.salvarBinario(Arrays.asList(secretaria.ListarDisciplinas().toArray()), arqDisciplinas);
        App.salvarBinario(Arrays.asList(secretaria.ListarCursos().toArray()), arqCursos);
    }
}
