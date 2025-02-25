package Model;
import java.time.LocalDate;

public class Matricula {
    private Long id;
    private LocalDate dataMatricula;
    private Aluno aluno;
    private Disciplina disciplina;

    public void confirmarMatricula() {
        
    }

    public void cancelarMatricula() {
        
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
}

