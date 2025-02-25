package Model;
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private Long id;
    private String nome;
    private Integer creditos;
    private Curso curso;
    private List<Aluno> alunosMatriculados;

    public Disciplina() {
        this.alunosMatriculados = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        this.alunosMatriculados.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        this.alunosMatriculados.remove(aluno);
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}


