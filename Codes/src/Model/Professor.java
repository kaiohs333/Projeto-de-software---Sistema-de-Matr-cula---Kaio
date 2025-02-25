package Model;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {
    private List<Disciplina> disciplinasMinistradas;

    public Professor() {
        this.disciplinasMinistradas = new ArrayList<>();
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        this.disciplinasMinistradas.add(disciplina);
    }

    public void removerDisciplina(Disciplina disciplina) {
        this.disciplinasMinistradas.remove(disciplina);
    }

    public List<Disciplina> getDisciplinasMinistradas() {
        return disciplinasMinistradas;
    }
}

