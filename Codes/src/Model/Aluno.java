package Model;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private String matricula;
    private List<Disciplina> disciplinasMatriculadas;

    public Aluno() {
        this.disciplinasMatriculadas = new ArrayList<>();
    }

    public void matricular(Disciplina disciplina) {
        this.disciplinasMatriculadas.add(disciplina);
    }

    public void cancelarMatricula(Disciplina disciplina) {
        this.disciplinasMatriculadas.remove(disciplina);
    }

    public List<Disciplina> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}


